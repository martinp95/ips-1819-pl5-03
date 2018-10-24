package com.uniovi.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.uniovi.types.PedidosOrdenTrabajoKey;

@Entity
@IdClass(PedidosOrdenTrabajoKey.class)
public class PedidosOrdenTrabajo {

	@Id
	@ManyToOne
	@JoinColumn(name = "PEDIDO_ID")
	private Pedido pedido;

	@Id
	@ManyToOne
	@JoinColumn(name = "ORDENTRABAJO_ID")
	private OrdenTrabajo ordenTrabajo;

	public PedidosOrdenTrabajo() {
	}

	public PedidosOrdenTrabajo(Pedido pedido, OrdenTrabajo ordenTrabajo) {
		this.pedido = pedido;
		this.ordenTrabajo = ordenTrabajo;
	}

	public Pedido getPedido() {
		return pedido;
	}

	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}

	public OrdenTrabajo getOrdenTrabajo() {
		return ordenTrabajo;
	}

	public void setOrdenTrabajo(OrdenTrabajo ordenTrabajo) {
		this.ordenTrabajo = ordenTrabajo;
	}
	
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
		PedidosOrdenTrabajo other = (PedidosOrdenTrabajo) obj;
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
