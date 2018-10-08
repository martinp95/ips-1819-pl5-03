package com.uniovi.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.uniovi.types.ProductosCarritoKey;

@Entity
@IdClass(ProductosCarritoKey.class)
public class ProductosCarrito {

	@Id
	@ManyToOne
	@JoinColumn(name = "USER_ID")
	private User user;

	@Id
	@ManyToOne
	@JoinColumn(name = "PRODUCTO_ID")
	private Producto producto;

	private int cantidad;

	public ProductosCarrito() {
	}

	public ProductosCarrito(User user, Producto producto, int cantidad) {
		this.user = user;
		this.producto = producto;		
		this.setCantidad(cantidad);
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

}