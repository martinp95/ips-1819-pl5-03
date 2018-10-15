package com.uniovi.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.uniovi.types.OrdenTrabajoKey;

@Entity
@IdClass(OrdenTrabajoKey.class)
public class OrdenTrabajo {
	
	@Id
	@ManyToOne
	@JoinColumn(name = "PEDIDO_ID")
	private Pedido pedido;
	
	
	@Id
	@ManyToOne
	@JoinColumn(name = "ALMACENERO_ID")
	private User almacenero;
	
	OrdenTrabajo(){}

	public OrdenTrabajo(Pedido pedido, User almacenero) {
		super();
		this.pedido = pedido;
		this.almacenero = almacenero;
	}

	public Pedido getPedido() {
		return pedido;
	}

	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}

	public User getAlmacenero() {
		return almacenero;
	}

	public void setAlmacenero(User almacenero) {
		this.almacenero = almacenero;
	}
	
	

}
