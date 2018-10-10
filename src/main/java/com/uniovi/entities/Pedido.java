package com.uniovi.entities;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Pedido {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Date fecha;
	private int size;
	private boolean asignado;
	
	@OneToMany(mappedBy="pedido", cascade = CascadeType.ALL)
	private Set<OrdenTrabajo> ordenesTrabajo = new HashSet<OrdenTrabajo>();
	
	@OneToMany(mappedBy="pedido", cascade = CascadeType.ALL)
	private Set<Producto> productosPedido = new HashSet<Producto>();	
	
	
	public Pedido() {
		super();
		this.fecha = new Date();
		this.size = 0;
		this.asignado = false;
	}


	public Date getFecha() {
		return fecha;
	}


	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}


	public int getSize() {
		return size;
	}


	public void setSize(int tamaño) {
		this.size = tamaño;
	}


	public Long getId() {
		return id;
	}

	public boolean isAsignado() {
		return asignado;
	}

	public void setAsignado(boolean asignado) {
		this.asignado = asignado;
	}

	public Set<OrdenTrabajo> getOrdenesTrabajo() {
		return ordenesTrabajo;
	}

	public void addOrdenTrabajo(OrdenTrabajo ordenesTrabajo) {
		this.ordenesTrabajo.add(ordenesTrabajo);
	}

	public Set<Producto> getProductosPedido() {
		return productosPedido;
	}

	public void addProducto(Producto producto) {
		this.productosPedido.add(producto);
		this.size ++;
	}
	
	
	
	
	

}
