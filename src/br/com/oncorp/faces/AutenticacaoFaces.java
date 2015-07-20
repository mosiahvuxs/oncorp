package br.com.oncorp.faces;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.oncorp.business.MenuBS;
import br.com.oncorp.business.PermissaoBS;
import br.com.oncorp.business.UsuarioBS;
import br.com.oncorp.model.Grupo;
import br.com.oncorp.model.Menu;
import br.com.oncorp.model.Permissao;
import br.com.oncorp.model.Usuario;
import br.com.oncorp.util.Constantes;
import br.com.topsys.util.TSUtil;
import br.com.topsys.web.faces.TSMainFaces;
import br.com.topsys.web.util.TSFacesUtil;

@SuppressWarnings("serial")
@SessionScoped
@ManagedBean(name = "autenticacaoFaces")
public class AutenticacaoFaces extends TSMainFaces {
	
	@EJB
	private UsuarioBS usuarioBS;

	@EJB
	private MenuBS menuBS;

	@EJB
	private PermissaoBS permissaoBS;
	
	private Usuario usuario;
	private List<Menu> menus;
	private List<Permissao> permissoes;
	private Permissao permissaoSelecionada;
	private String nomeTela, tela;
	private Integer tabAtiva;	

	public AutenticacaoFaces() {

		this.setNomeTela("Área de Trabalho");
	}

	@PostConstruct
	public void init() {

		this.tabAtiva = 0;
		this.usuario = new Usuario();
		this.permissaoSelecionada = new Permissao();
		this.menus = new ArrayList<Menu>();

	}

	public String login() {

		this.usuario = this.usuarioBS.obterPorLoginSenha(this.usuario);

		if (TSUtil.isEmpty(this.usuario)) {

			this.clearFields();

			super.addErrorMessage("Login/Senha sem credencial para acesso.");

			return null;
		}

		this.carregarMenu(this.usuario.getGrupo());

		TSFacesUtil.addObjectInSession(Constantes.USUARIO_CONECTADO, this.usuario);

		return "entrar";
	}

	private void carregarMenu(Grupo grupo) {

		this.menus = this.menuBS.pesquisarCabecalhos(grupo);

		this.permissoes = this.permissaoBS.pesquisar(grupo);

	}

	public String redirecionar() {

		if (!TSUtil.isEmpty(this.permissaoSelecionada.getMenu().getManagedBeanReset())) {
			TSFacesUtil.removeManagedBeanInSession(this.permissaoSelecionada.getMenu().getManagedBeanReset());
		}

		this.setTela(this.permissaoSelecionada.getMenu().getUrl());
		this.setNomeTela("Área de Trabalho > " + permissaoSelecionada.getMenu().getMenuPai().getDescricao() + " > " + permissaoSelecionada.getMenu().getDescricao());
		this.setTabAtiva(Integer.valueOf(this.menus.indexOf(this.permissaoSelecionada.getMenu().getMenuPai())));

		return SUCESSO;
	}

	public String logout() {

		TSFacesUtil.removeObjectInSession(Constantes.USUARIO_CONECTADO);

		TSFacesUtil.getRequest().getSession().invalidate();

		return "sair";
	}

	public String getNomeTela() {
		return nomeTela;
	}

	public void setNomeTela(String nomeTela) {
		this.nomeTela = nomeTela;
	}

	public String getTela() {
		return tela;
	}

	public void setTela(String tela) {
		this.tela = tela;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public List<Menu> getMenus() {
		return menus;
	}

	public void setMenus(List<Menu> menus) {
		this.menus = menus;
	}

	public List<Permissao> getPermissoes() {
		return permissoes;
	}

	public void setPermissoes(List<Permissao> permissoes) {
		this.permissoes = permissoes;
	}

	public Permissao getPermissaoSelecionada() {
		return permissaoSelecionada;
	}

	public void setPermissaoSelecionada(Permissao permissaoSelecionada) {
		this.permissaoSelecionada = permissaoSelecionada;
	}

	public Integer getTabAtiva() {
		return tabAtiva;
	}

	public void setTabAtiva(Integer tabAtiva) {
		this.tabAtiva = tabAtiva;
	}

}
