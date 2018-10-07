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
	@JoinColumn(name = "USER_ID", referencedColumnName = "ID")
	private User user;

	@Id
	@ManyToOne
	@JoinColumn(name = "PRODUCTO_ID", referencedColumnName = "ID")
	private Producto producto;

	private int cantidad;

	public ProductosCarrito() {
	}

	public ProductosCarrito(User user, Producto producto, int cantidad) {
		_setUser(user);
		_setProducto(producto);

		user._getProductosCarrito().add(this);
		producto._getProductosCarrito().add(this);
		
		this.setCantidad(cantidad);
	}

	private void _setProducto(Producto producto) {
		this.producto = producto;
	}

	private void _setUser(User user) {
		this.user = user;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

}