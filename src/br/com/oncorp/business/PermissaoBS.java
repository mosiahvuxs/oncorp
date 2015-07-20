package br.com.oncorp.business;

import java.util.List;

import javax.ejb.Stateless;

import br.com.oncorp.dao.CrudDAO;
import br.com.oncorp.dao.PermissaoDAO;
import br.com.oncorp.model.Grupo;
import br.com.oncorp.model.Permissao;
import br.com.topsys.exception.TSApplicationException;

@Stateless
public class PermissaoBS extends CrudBS<Permissao> {

	PermissaoDAO permissaoDAO = new PermissaoDAO();

	@Override
	protected CrudDAO<Permissao> getCrudDAO() {

		return this.permissaoDAO;
	}

	public List<Permissao> pesquisar(Grupo model) {

		return this.permissaoDAO.pesquisar(model);
	}
	
	public void excluirPorGrupo(Grupo model) throws TSApplicationException{
		
		this.permissaoDAO.excluirPorGrupo(model);
	}

}
