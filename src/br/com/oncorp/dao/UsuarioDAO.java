package br.com.oncorp.dao;

import java.util.List;

import br.com.oncorp.model.Usuario;
import br.com.topsys.database.TSDataBaseBrokerIf;
import br.com.topsys.database.factory.TSDataBaseBrokerFactory;
import br.com.topsys.exception.TSApplicationException;
import br.com.topsys.util.TSUtil;

public class UsuarioDAO implements CrudDAO<Usuario> {

	public Usuario obter(Usuario model) {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setPropertySQL("usuariodao.obter", model.getId());

		return (Usuario) broker.getObjectBean(Usuario.class, "id", "email", "flagAtivo", "login", "nome", "senha", "grupo.id");
	}

	public Usuario obterPorLoginSenha(Usuario model) {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setPropertySQL("usuariodao.obterPorLoginSenha", model.getLogin(), model.getSenhaCriptografada());

		return (Usuario) broker.getObjectBean(Usuario.class, "id", "email", "flagAtivo", "login", "nome", "senha", "grupo.id");
	}

	public void excluir(Usuario model) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setPropertySQL("usuariodao.excluir", model.getId());

		broker.execute();

	}

	public Usuario alterar(Usuario model) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setPropertySQL("usuariodao.alterar", model.getEmail(), model.getFlagAtivo(),

		model.getLogin(), model.getNome(), model.getSenha(), model.getGrupo().getId(), model.getId());

		broker.execute();

		return model;

	}

	public Usuario inserir(Usuario model) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		model.setId(broker.getSequenceNextValue("usuarios_id_seq"));

		broker.setPropertySQL("usuariodao.inserir", model.getId(), model.getEmail(), model.getFlagAtivo(),

		model.getLogin(), model.getNome(), model.getSenha(), model.getGrupo().getId());

		broker.execute();

		return model;

	}

	@SuppressWarnings("unchecked")
	public List<Usuario> pesquisar(Usuario model) {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		StringBuilder sql = new StringBuilder();

		sql.append("SELECT U.ID, U.EMAIL, U.FLAG_ATIVO, U.LOGIN, U.NOME, U.SENHA, U.GRUPO_ID, G.DESCRICAO FROM USUARIOS U, GRUPOS G WHERE U.GRUPO_ID = G.ID");

		if (!TSUtil.isEmpty(model.getLogin())) {

			sql.append(" AND SEM_ACENTOS(U.LOGIN) ILIKE ?");
		}

		if (!TSUtil.isEmpty(model.getEmail())) {

			sql.append(" AND SEM_ACENTOS(U.EMAIL) ILIKE ?");
		}

		if (!TSUtil.isEmpty(model.getNome())) {

			sql.append(" AND SEM_ACENTOS(U.NOME) ILIKE ?");
		}

		if (!TSUtil.isEmpty(model.getGrupo()) && !TSUtil.isEmpty(model.getGrupo().getId())) {

			sql.append(" AND U.GRUPO_ID = ?");
		}

		sql.append(" AND U.FLAG_ATIVO = ?");

		sql.append(" ORDER BY U.NOME");

		broker.setSQL(sql.toString());

		if (!TSUtil.isEmpty(model.getLogin())) {

			broker.set("%" + model.getLogin() + "%");

		}

		if (!TSUtil.isEmpty(model.getEmail())) {

			broker.set("%" + model.getEmail() + "%");

		}

		if (!TSUtil.isEmpty(model.getNome())) {

			broker.set("%" + model.getNome() + "%");

		}

		if (!TSUtil.isEmpty(model.getGrupo()) && !TSUtil.isEmpty(model.getGrupo().getId())) {

			broker.set(model.getGrupo().getId());

		}

		broker.set(model.getFlagAtivo());

		return broker.getCollectionBean(Usuario.class, "id", "email", "flagAtivo", "login", "nome", "senha", "grupo.id", "grupo.descricao");
	}

}
