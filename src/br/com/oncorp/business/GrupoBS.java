package br.com.oncorp.business;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import br.com.oncorp.dao.CrudDAO;
import br.com.oncorp.dao.GrupoDAO;
import br.com.oncorp.model.Grupo;
import br.com.oncorp.model.Permissao;
import br.com.topsys.exception.TSApplicationException;

@Stateless
public class GrupoBS extends CrudBS<Grupo> {

	@EJB
	private PermissaoBS permissaoBS;

	GrupoDAO grupoDAO = new GrupoDAO();

	@Override
	protected CrudDAO<Grupo> getCrudDAO() {

		return grupoDAO;
	}

	@Override
	public Grupo inserir(Grupo model) throws TSApplicationException {

		model = this.getCrudDAO().inserir(model);

		for (Permissao permissao : model.getPermissoes()) {

			this.permissaoBS.inserir(permissao);

		}

		return model;
	}

	@Override
	public Grupo alterar(Grupo model) throws TSApplicationException {

		model = this.getCrudDAO().alterar(model);

		this.permissaoBS.excluirPorGrupo(model);

		for (Permissao permissao : model.getPermissoes()) {

			this.permissaoBS.inserir(permissao);

		}

		return model;
	}

}
