package com.uniovi.entities;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Producto {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private String description;
	private double precio;
	private int stock;
	private int pasillo;
	private String posicion;
	private int numEstanteria;
	private int numFila;
	private String codigo;

	@OneToMany(mappedBy = "producto", cascade = CascadeType.ALL)
	private Set<ProductosCarrito> productosCarrito = new HashSet<ProductosCarrito>();

	@OneToMany(mappedBy = "producto", cascade = CascadeType.ALL)
	private Set<ProductosPedido> productosPedido = new HashSet<ProductosPedido>();

	public Producto() {
	}

	public Producto(String name, String description, double precio, int stock, int pasillo, String posicion,
			int numEstanteria, int numFila) {
		super();
		this.name = name;
		this.description = description;
		this.precio = precio;
		this.stock = stock;
		this.pasillo = pasillo;
		this.posicion = posicion;
		this.numEstanteria = numEstanteria;
		this.numFila = numFila;
	}

	public String getName() {
		return name;
	}

	public void setNombre(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getPrecio() {
		return precio;
	}

	public void setPrecio(double precio) {
		this.precio = precio;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

	public int getPasillo() {
		return pasillo;
	}

	public void setPasillo(int pasillo) {
		this.pasillo = pasillo;
	}

	public String getPosicion() {
		return posicion;
	}

	public void setPosicion(String posicion) {
		this.posicion = posicion;
	}

	public int getNumEstanteria() {
		return numEstanteria;
	}

	public void setNumEstanteria(int numEstanteria) {
		this.numEstanteria = numEstanteria;
	}

	public int getNumFila() {
		return numFila;
	}

	public void setNumFila(int numFila) {
		this.numFila = numFila;
	}

	public Long getId() {
		return id;
	}

	Set<ProductosCarrito> _getProductosCarrito() {
		return productosCarrito;
	}

	public Set<ProductosCarrito> getProductosCarrito() {
		return new HashSet<ProductosCarrito>(productosCarrito);
	}

	Set<ProductosPedido> _getProductosPedido() {
		return productosPedido;
	}

	public Set<ProductosPedido> getProductosPedido() {
		return new HashSet<ProductosPedido>(productosPedido);
	}	

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
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
		Producto other = (Producto) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Producto [id=" + id + ", name=" + name + ", description=" + description + ", precio=" + precio
				+ ", stock=" + stock + ", pasillo=" + pasillo + ", posicion=" + posicion + ", numEstanteria="
				+ numEstanteria + ", numFila=" + numFila + "]";
	}
	
	

}
