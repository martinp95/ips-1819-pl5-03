package com.uniovi.entities;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class OrdenTrabajo {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	private User almacenero;
	
	@OneToMany(mappedBy = "ordenTrabajo", cascade = CascadeType.ALL)
	private Set<PedidosOrdenTrabajo> pedidoOrdenesTrabajo = new HashSet<PedidosOrdenTrabajo>();	
	
	private Date fecha;
	private boolean incidencia;
	
	@OneToOne
	private Paquete paquete;

	
	public OrdenTrabajo() {
	}
	
	public OrdenTrabajo(User user) {
		this.almacenero = user;
		this.setFecha(new Date());
		this.incidencia = true;
	}

	public Long getId() {
		return id;
	}

	public User getAlmacenero() {
		return almacenero;
	}

	public void setAlmacenero(User almacenero) {
		this.almacenero = almacenero;
	}

	Set<PedidosOrdenTrabajo> _getPedidoOrdenesTrabajo() {
		return pedidoOrdenesTrabajo;
	}
	
	public Set<PedidosOrdenTrabajo> getPedidoOrdenesTrabajo() {
		return new HashSet<PedidosOrdenTrabajo>(pedidoOrdenesTrabajo);
	}

	public void addPedidoOrdenTrabajo(PedidosOrdenTrabajo pedidosOrdenesTrabajo) {
		this.pedidoOrdenesTrabajo.add(pedidosOrdenesTrabajo);
	}
	
	public Paquete getPaquete() {
		return paquete;
	}

	public void setPaquete(Paquete paquete) {
		this.paquete = paquete;
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
		OrdenTrabajo other = (OrdenTrabajo) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public boolean isIncidencia() {
		return incidencia;
	}

	public void setIncidencia(boolean incidencia) {
		this.incidencia = incidencia;
	}
	
	

}
