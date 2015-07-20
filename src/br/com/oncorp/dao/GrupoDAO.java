package br.com.oncorp.dao;

import java.util.List;

import br.com.oncorp.model.Grupo;
import br.com.oncorp.model.Permissao;
import br.com.topsys.database.TSDataBaseBrokerIf;
import br.com.topsys.database.factory.TSDataBaseBrokerFactory;
import br.com.topsys.exception.TSApplicationException;
import br.com.topsys.util.TSUtil;

public class GrupoDAO implements CrudDAO<Grupo>{

	@SuppressWarnings("unchecked")
	public List<Grupo> pesquisar(Grupo model) {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		StringBuilder sql = new StringBuilder();

		sql.append("SELECT ID, DESCRICAO FROM GRUPOS WHERE 1 = 1");

		if (!TSUtil.isEmpty(model.getDescricao())) {

			sql.append(" AND SEM_ACENTOS(DESCRICAO) ILIKE ?");
		}

		sql.append(" ORDER BY DESCRICAO");

		broker.setSQL(sql.toString());

		if (!TSUtil.isEmpty(model.getDescricao())) {

			broker.set("%" + model.getDescricao() + "%");

		}

		return broker.getCollectionBean(Grupo.class, "id", "descricao");
	}

	public Grupo obter(Grupo model) {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setPropertySQL("grupodao.obter", model.getId());

		return (Grupo) broker.getObjectBean(Grupo.class, "id", "descricao");
	}

	public void excluir(Grupo model) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.setPropertySQL("grupodao.excluir", model.getId());

		broker.execute();

	}

	public Grupo inserir(Grupo model) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.beginTransaction();

		model.setId(broker.getSequenceNextValue("grupos_id_seq"));

		broker.setPropertySQL("grupodao.inserir", model.getId(), model.getDescricao());

		broker.execute();

		if (!TSUtil.isEmpty(model.getPermissoes())) {

			for (Permissao item : model.getPermissoes()) {

				item.setGrupo(model);

				new PermissaoDAO().inserir(item, broker);
			}
		}

		broker.endTransaction();

		return model;

	}

	public Grupo alterar(Grupo model) throws TSApplicationException {

		TSDataBaseBrokerIf broker = TSDataBaseBrokerFactory.getDataBaseBrokerIf();

		broker.beginTransaction();

		broker.setPropertySQL("grupodao.alterar", model.getDescricao(), model.getId());

		broker.execute();

		PermissaoDAO permissaoDAO = new PermissaoDAO();

		permissaoDAO.excluirPorGrupo(model, broker);

		if (!TSUtil.isEmpty(model.getPermissoes())) {

			for (Permissao item : model.getPermissoes()) {

				item.setGrupo(model);

				new PermissaoDAO().inserir(item, broker);
			}
		}

		broker.endTransaction();

		return model;

	}

}
