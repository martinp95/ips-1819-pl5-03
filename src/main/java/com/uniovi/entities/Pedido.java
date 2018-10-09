package com.uniovi.entities;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Pedido {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Date fecha;
	private int size;
	private boolean asignado;
	
	@ManyToOne
	private Almacenero almacenero;
	
	Pedido(){}
	
	public Pedido(Date fecha, int size) {
		super();
		this.fecha = fecha;
		this.size = size;
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

	public Almacenero getAlmacenero() {
		return almacenero;
	}

	public void setAlmacenero(Almacenero almacenero) {
		this.almacenero = almacenero;
	}

	public boolean isAsignado() {
		return asignado;
	}

	public void setAsignado(boolean asignado) {
		this.asignado = asignado;
	}
	
	
	

}
