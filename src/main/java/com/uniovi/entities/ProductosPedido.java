package com.uniovi.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.uniovi.types.ProductosPedidoKey;

@Entity
@IdClass(ProductosPedidoKey.class)
public class ProductosPedido {

	@Id
	@ManyToOne
	@JoinColumn(name = "PEDIDO_ID")
	private Pedido pedido;

	@Id
	@ManyToOne
	@JoinColumn(name = "PRODUCTO_ID")
	private Producto producto;

	private int cantidad;
	private int cantidadPorRecoger;
	private double precioUnidad;
	private String incidencia;
	private boolean tieneIncidencia;

	@ManyToOne
	private Paquete paquete;

	public ProductosPedido() {
	}

	public ProductosPedido(Pedido pedido, Producto producto, int cantidad, double precioUnidad) {
		this.pedido = pedido;
		this.producto = producto;
		this.setCantidad(cantidad);
		this.setCantidadPorRecoger(cantidad);
		this.setPrecioUnidad(precioUnidad);
		this.incidencia = "";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((pedido == null) ? 0 : pedido.hashCode());
		result = prime * result + ((producto == null) ? 0 : producto.hashCode());
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
		ProductosPedido other = (ProductosPedido) obj;
		if (pedido == null) {
			if (other.pedido != null)
				return false;
		} else if (!pedido.equals(other.pedido))
			return false;
		if (producto == null) {
			if (other.producto != null)
				return false;
		} else if (!producto.equals(other.producto))
			return false;
		return true;
	}

	public Pedido getPedido() {
		return pedido;
	}

	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}

	public Producto getProducto() {
		return producto;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}

	public double getPrecioUnidad() {
		return precioUnidad;
	}

	public void setPrecioUnidad(double precioUnidad) {
		this.precioUnidad = precioUnidad;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	public int getCantidadPorRecoger() {
		return cantidadPorRecoger;
	}

	public void setCantidadPorRecoger(int cantidadPorRecoger) {
		this.cantidadPorRecoger = cantidadPorRecoger;
	}

	public String getIncidencia() {
		return incidencia;
	}

	public void setIncidencia(String incidencia) {
		this.incidencia = incidencia;
	}

	public Paquete getPaquete() {
		return paquete;
	}

	public void setPaquete(Paquete paquete) {
		this.paquete = paquete;
	}

	public boolean isTieneIncidencia() {
		return tieneIncidencia;
	}

	public void setTieneIncidencia(boolean tieneIncidencia) {
		this.tieneIncidencia = tieneIncidencia;
	}
}
