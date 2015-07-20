package br.com.oncorp.faces;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;

import br.com.oncorp.business.GrupoBS;
import br.com.oncorp.business.MenuBS;
import br.com.oncorp.business.PermissaoBS;
import br.com.oncorp.dao.PermissaoDAO;
import br.com.oncorp.model.Grupo;
import br.com.oncorp.model.Menu;
import br.com.oncorp.model.Permissao;
import br.com.topsys.exception.TSApplicationException;
import br.com.topsys.util.TSUtil;
import br.com.topsys.web.faces.TSMainFaces;
import br.com.topsys.web.util.TSFacesUtil;

@SuppressWarnings("serial")
@ViewScoped
@ManagedBean(name = "grupoFaces")
public class GrupoFaces extends TSMainFaces {

	@EJB
	private GrupoBS grupoBS;

	@EJB
	private PermissaoBS permissaoBS;

	@EJB
	private MenuBS menuBS;

	private Grupo crudModel;

	private Grupo crudPesquisaModel;

	private List<SelectItem> comboMenus;

	private Permissao permissaoSelecionada;

	private List<Grupo> grid;

	private Integer tabIndex;

	private boolean flagAlterar;

	@PostConstruct
	public void init() {

		this.limpar();
		this.limparPesquisa();
		this.initCombo();
	}

	private void initCombo() {

		this.comboMenus = super.initCombo(this.menuBS.pesquisarExecutaveis(), "id", "descricao");
	}

	@Override
	protected void clearFields() {

		this.tabIndex = 0;

	}

	public String limpar() {

		this.flagAlterar = false;

		this.crudModel = new Grupo();

		this.clearFields();

		return null;

	}

	public String limparPesquisa() {

		this.grid = Collections.emptyList();

		this.setGrid(new ArrayList<Grupo>());

		this.setCrudPesquisaModel(new Grupo());

		return null;
	}

	public String addPermissao() {

		Permissao permissao = new Permissao();

		permissao.setGrupo(getCrudModel());
		permissao.setMenu(new Menu());
		permissao.setFlagInserir(Boolean.TRUE);
		permissao.setFlagAlterar(Boolean.TRUE);
		permissao.setFlagExcluir(Boolean.FALSE);

		if (TSUtil.isEmpty(getCrudModel().getPermissoes())) {
			
			this.crudModel.setPermissoes(new ArrayList<Permissao>());
		}

		if (!this.crudModel.getPermissoes().contains(permissao)) {

			this.crudModel.getPermissoes().add(permissao);

		} else {

			super.addErrorMessage("Essa permissão já foi adicionada");
		}

		return null;
	}

	public String delPermissao() {
		
		this.crudModel.getPermissoes().remove(this.permissaoSelecionada);

		return null;
	}

	@Override
	protected String insert() throws TSApplicationException {

		super.setClearFields(false);

		super.setDefaultMessage(false);

		this.grupoBS.inserir(this.crudModel);

		this.limpar();

		super.setDefaultMessage(true);

		return null;
	}

	@Override
	protected String find() {

		this.tabIndex = 1;

		this.grid = this.grupoBS.pesquisar(this.crudPesquisaModel);

		TSFacesUtil.gerarResultadoLista(this.grid);

		return null;
	}

	@Override
	protected String detail() {

		this.crudModel = this.grupoBS.obter(this.crudModel);

		this.crudModel.setPermissoes(new PermissaoDAO().pesquisar(this.crudModel));

		this.tabIndex = 0;

		this.flagAlterar = true;

		return null;
	}

	@Override
	protected String update() throws TSApplicationException {

		super.setClearFields(false);

		super.setDefaultMessage(false);

		this.grupoBS.alterar(this.crudModel);

		this.limpar();

		super.setDefaultMessage(true);

		return null;
	}

	public List<SelectItem> getComboMenus() {
		return comboMenus;
	}

	public void setComboMenus(List<SelectItem> comboMenus) {
		this.comboMenus = comboMenus;
	}

	public Permissao getPermissaoSelecionada() {
		return permissaoSelecionada;
	}

	public void setPermissaoSelecionada(Permissao permissaoSelecionada) {
		this.permissaoSelecionada = permissaoSelecionada;
	}

	public Grupo getCrudModel() {
		return crudModel;
	}

	public void setCrudModel(Grupo crudModel) {
		this.crudModel = crudModel;
	}

	public Grupo getCrudPesquisaModel() {
		return crudPesquisaModel;
	}

	public void setCrudPesquisaModel(Grupo crudPesquisaModel) {
		this.crudPesquisaModel = crudPesquisaModel;
	}

	public List<Grupo> getGrid() {
		return grid;
	}

	public void setGrid(List<Grupo> grid) {
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

}
