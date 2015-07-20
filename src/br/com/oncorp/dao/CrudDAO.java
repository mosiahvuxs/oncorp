package br.com.oncorp.dao;

import java.util.List;

import br.com.topsys.exception.TSApplicationException;

public interface CrudDAO<T> {

	public T obter(T crudModel);

	public List<T> pesquisar(T crudModel);

	public T inserir(T crudModel) throws TSApplicationException;

	public T alterar(T crudModel) throws TSApplicationException;

	public void excluir(T crudModel) throws TSApplicationException;

}
