package com.uniovi.types;

import java.io.Serializable;

public class OrdenTrabajoKey implements Serializable{
	
	private static final long serialVersionUID = 1L;

	Long almacenero;
	Long pedido;
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((almacenero == null) ? 0 : almacenero.hashCode());
		result = prime * result + ((pedido == null) ? 0 : pedido.hashCode());
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
		OrdenTrabajoKey other = (OrdenTrabajoKey) obj;
		if (almacenero == null) {
			if (other.almacenero != null)
				return false;
		} else if (!almacenero.equals(other.almacenero))
			return false;
		if (pedido == null) {
			if (other.pedido != null)
				return false;
		} else if (!pedido.equals(other.pedido))
			return false;
		return true;
	}
	
	

}

