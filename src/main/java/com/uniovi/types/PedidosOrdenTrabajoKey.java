package com.uniovi.types;

import java.io.Serializable;

public class PedidosOrdenTrabajoKey implements Serializable {

	private static final long serialVersionUID = 1L;

	Long pedido;
	Long ordenTrabajo;

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((ordenTrabajo == null) ? 0 : ordenTrabajo.hashCode());
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
		PedidosOrdenTrabajoKey other = (PedidosOrdenTrabajoKey) obj;
		if (ordenTrabajo == null) {
			if (other.ordenTrabajo != null)
				return false;
		} else if (!ordenTrabajo.equals(other.ordenTrabajo))
			return false;
		if (pedido == null) {
			if (other.pedido != null)
				return false;
		} else if (!pedido.equals(other.pedido))
			return false;
		return true;
	}
}
