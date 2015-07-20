package br.com.oncorp.business;

import javax.ejb.Stateless;

import br.com.oncorp.dao.CrudDAO;
import br.com.oncorp.dao.GrupoDAO;
import br.com.oncorp.model.Grupo;

@Stateless
public class GrupoBS extends CrudBS<Grupo>{

	GrupoDAO grupoDAO = new GrupoDAO();
	
	@Override
	protected CrudDAO<Grupo> getCrudDAO() {

		return grupoDAO;
	}
	
}
