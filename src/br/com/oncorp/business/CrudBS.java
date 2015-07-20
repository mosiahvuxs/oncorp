package br.com.oncorp.business;

import java.io.Serializable;
import java.util.List;

import br.com.oncorp.dao.CrudDAO;
import br.com.topsys.exception.TSApplicationException;

public abstract class CrudBS<T extends Serializable> {

	public CrudBS() {

	}

	protected abstract CrudDAO<T> getCrudDAO();

	public List<T> pesquisar(T crudModel) {
		return this.getCrudDAO().pesquisar(crudModel);
	}

	public T obter(T crudModel) {
		return this.getCrudDAO().obter(crudModel);
	}

	public T inserir(T crudModel) throws TSApplicationException {
		return this.getCrudDAO().inserir(crudModel);
	}

	public T alterar(T crudModel) throws TSApplicationException {
		return this.getCrudDAO().alterar(crudModel);
	}

	public void excluir(T crudModel) throws TSApplicationException {
		getCrudDAO().excluir(crudModel);
	}

}
