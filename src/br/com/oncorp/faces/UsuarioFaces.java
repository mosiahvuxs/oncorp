package br.com.oncorp.faces;

import java.util.Collections;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;

import br.com.oncorp.business.GrupoBS;
import br.com.oncorp.business.UsuarioBS;
import br.com.oncorp.model.Grupo;
import br.com.oncorp.model.Usuario;
import br.com.oncorp.util.UsuarioUtil;
import br.com.oncorp.util.Utilitarios;
import br.com.topsys.constant.TSConstant;
import br.com.topsys.exception.TSApplicationException;
import br.com.topsys.util.TSCryptoUtil;
import br.com.topsys.util.TSUtil;
import br.com.topsys.web.faces.TSMainFaces;
import br.com.topsys.web.util.TSFacesUtil;

@SuppressWarnings("serial")
@ViewScoped
@ManagedBean(name = "usuarioFaces")
public class UsuarioFaces extends TSMainFaces {

	@EJB
	private UsuarioBS usuarioBS;
	
	@EJB
	private GrupoBS grupoBS;

	private Usuario crudModel;

	private Usuario crudPesquisaModel;

	private List<SelectItem> grupos;

	private List<Usuario> grid;

	private Integer tabIndex;

	private boolean flagAlterar;

	private String senha;

	public UsuarioFaces() {

		this.init();
	}

	public void init() {

		this.limpar();
		this.limparPesquisa();
		this.initCombo();

	}

	public String limpar() {

		this.crudModel = new Usuario();
		this.crudModel.setGrupo(new Grupo());
		this.crudModel.setFlagAtivo(Boolean.TRUE);

		this.tabIndex = 0;
		this.flagAlterar = false;

		return null;
	}

	public String limparPesquisa() {

		this.grid = Collections.emptyList();
		this.crudPesquisaModel = new Usuario();
		this.crudPesquisaModel.setGrupo(new Grupo());
		this.crudPesquisaModel.setFlagAtivo(Boolean.TRUE);

		return null;
	}

	private void initCombo() {

		this.grupos = super.initCombo(this.grupoBS.pesquisar(new Grupo()), "id", "descricao");
	}

	private boolean validaSenhas() {

		if (!this.crudModel.getSenha().equals(this.crudModel.getConfirmaSenha())) {

			super.addErrorMessage("Senhas não conferem.");

			return false;
		}

		return true;
	}

	@Override
	protected String insert() throws TSApplicationException {

		super.setClearFields(false);

		super.setDefaultMessage(false);

		if (!this.validaSenhas()) {

			return null;

		}

		this.crudModel.setSenha(TSCryptoUtil.gerarHash(this.crudModel.getSenha(), TSConstant.CRIPTOGRAFIA_MD5));

		this.usuarioBS.inserir(this.getCrudModel());

		this.limpar();

		super.setDefaultMessage(true);

		return null;
	}

	@Override
	protected String find() {

		this.tabIndex = 1;

		this.grid = this.usuarioBS.pesquisar(this.crudPesquisaModel);

		TSFacesUtil.gerarResultadoLista(this.grid);

		return null;
	}

	@Override
	protected String detail() {

		this.crudModel = this.usuarioBS.obter(this.crudModel);

		this.senha = this.crudModel.getSenha();

		this.tabIndex = 0;

		this.flagAlterar = true;

		return null;

	}

	@Override
	protected String update() throws TSApplicationException {

		super.setClearFields(false);

		super.setDefaultMessage(false);

		if (TSUtil.isEmpty(Utilitarios.tratarString(this.getCrudModel().getSenha()))) {

			this.getCrudModel().setSenha(this.senha);

		} else {

			this.getCrudModel().setSenha(UsuarioUtil.getSenhaCriptografada(this.getCrudModel().getSenha()));

		}

		this.usuarioBS.alterar(this.crudModel);

		this.limpar();

		super.setDefaultMessage(true);

		return null;
	}

	@Override
	protected String delete() throws TSApplicationException {

		this.usuarioBS.excluir(this.crudModel);

		this.grid = this.usuarioBS.pesquisar(this.crudPesquisaModel);

		this.tabIndex = 1;

		return null;

	}

	public Usuario getCrudModel() {
		return crudModel;
	}

	public void setCrudModel(Usuario crudModel) {
		this.crudModel = crudModel;
	}

	public Usuario getCrudPesquisaModel() {
		return crudPesquisaModel;
	}

	public void setCrudPesquisaModel(Usuario crudPesquisaModel) {
		this.crudPesquisaModel = crudPesquisaModel;
	}

	public List<SelectItem> getGrupos() {
		return grupos;
	}

	public void setGrupos(List<SelectItem> grupos) {
		this.grupos = grupos;
	}

	public List<Usuario> getGrid() {
		return grid;
	}

	public void setGrid(List<Usuario> grid) {
		this.grid = grid;
	}

	public Integer getTabIndex() {
		return tabIndex;
	}

	public void setTabIndex(Integer tabIndex) {
		this.tabIndex = tabIndex;
	}

	public boolean isFlagAlterar() {
		return flagAlterar;
	}

	public void setFlagAlterar(boolean flagAlterar) {
		this.flagAlterar = flagAlterar;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

}
