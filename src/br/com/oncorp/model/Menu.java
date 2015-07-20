package br.com.oncorp.model;

import java.io.Serializable;
import java.util.List;

import br.com.topsys.util.TSUtil;

@SuppressWarnings("serial")

public final class Menu implements Serializable {

	private Long id;

	private String descricao;

	private String url;
	
	private String managedBeanReset;
	
	private Boolean flagAtivo;

	private Integer ordem;
	
	private Menu menuPai;
	
	private List<Menu> menus;

	public Menu() {
	}
	
	public Menu(Boolean flagAtivo) {
		this.flagAtivo = flagAtivo;
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

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Boolean getFlagAtivo() {
		return flagAtivo;
	}

	public void setFlagAtivo(Boolean flagAtivo) {
		this.flagAtivo = flagAtivo;
	}

	public Integer getOrdem() {
		return ordem;
	}

	public void setOrdem(Integer ordem) {
		this.ordem = ordem;
	}

	public List<Menu> getMenus() {
		return menus;
	}

	public void setMenus(List<Menu> menus) {
		this.menus = menus;
	}

	public String getManagedBeanReset() {
		return managedBeanReset;
	}

	public void setManagedBeanReset(String managedBeanReset) {
		this.managedBeanReset = managedBeanReset;
	}

	public Menu getMenuPai() {
		return menuPai;
	}

	public void setMenuPai(Menu menuPai) {
		this.menuPai = menuPai;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Menu other = (Menu) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}
