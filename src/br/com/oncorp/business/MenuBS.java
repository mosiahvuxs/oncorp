package br.com.oncorp.business;

import java.util.List;

import javax.ejb.Stateless;

import br.com.oncorp.dao.CrudDAO;
import br.com.oncorp.dao.MenuDAO;
import br.com.oncorp.model.Grupo;
import br.com.oncorp.model.Menu;

@Stateless
public class MenuBS extends CrudBS<Menu>{
	
	MenuDAO menuDAO = new MenuDAO();
	
	@Override
	protected CrudDAO<Menu> getCrudDAO() {

		return this.menuDAO;
	}
	
	public List<Menu> pesquisarCabecalhos(Grupo model) {
		
		return this.menuDAO.pesquisarCabecalhos(model); 
	}
	
	public List<Menu> pesquisarExecutaveis(){
		
		return this.menuDAO.pesquisarExecutaveis();
	}
	
}
