package br.com.oncorp.business;

import javax.ejb.Stateless;

import br.com.oncorp.dao.CrudDAO;
import br.com.oncorp.dao.UsuarioDAO;
import br.com.oncorp.model.Usuario;

@Stateless
public class UsuarioBS extends CrudBS<Usuario> {

	UsuarioDAO usuarioDAO = new UsuarioDAO();

	@Override
	protected CrudDAO<Usuario> getCrudDAO() {

		return this.usuarioDAO;
	}

	public Usuario obterPorLoginSenha(Usuario model) {

		return this.usuarioDAO.obterPorLoginSenha(model);
	}

}
