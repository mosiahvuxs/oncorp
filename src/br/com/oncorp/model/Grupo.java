package br.com.oncorp.model;

import java.io.Serializable;
import java.util.List;

import br.com.topsys.util.TSUtil;

@SuppressWarnings("serial")
public class Grupo implements Serializable {

	private Long id;

	private String descricao;

	private List<Permissao> permissoes;
	
	public Grupo() {
		
	}

	public Grupo(Long id) {
		this.id = id;
	}
	
	public Long getId() {
		return TSUtil.tratarLong(id);
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public List<Permissao> getPermissoes() {
		return permissoes;
	}

	public void setPermissoes(List<Permissao> permissoes) {
		this.permissoes = permissoes;
	}

}
