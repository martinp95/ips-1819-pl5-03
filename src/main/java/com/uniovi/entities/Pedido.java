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

@Entity
public class Pedido {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Date fecha;
	private int size;
	private double total;
	private String tipoPago;

	@ManyToOne
	private User user;

	@OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL)
	private Set<ProductosPedido> productosPedido = new HashSet<ProductosPedido>();	
	
	@OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL)
	private Set<PedidosOrdenTrabajo> pedidoOrdenesTrabajo = new HashSet<PedidosOrdenTrabajo>();	
	
	private boolean pagado;


	public Pedido() {
	}

	@SuppressWarnings("deprecation")
	public Pedido(User user, int size) {
		this.user = user;
		Date dia = new Date();
		this.setFecha(new Date(dia.getYear(), dia.getMonth(), dia.getDate()));
		this.setSize(size);
		this.pagado = false;
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

	public void setSize(int size) {
		this.size = size;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	Set<ProductosPedido> _getProductosPedido() {
		return productosPedido;
	}

	public Set<ProductosPedido> getProductosPedido() {
		return new HashSet<ProductosPedido>(productosPedido);
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

	public Long getId() {
		return id;
	}
	
	public boolean isPagado() {
		return pagado;
	}

	public void setPagado(boolean pagado) {
		this.pagado = pagado;
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
		Pedido other = (Pedido) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Pedido [id=" + id + ", fecha=" + fecha + ", size=" + size + ", user=" + user + "]";
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public String getTipoPago() {
		return tipoPago;
	}

	public void setTipoPago(String tipoPago) {
		this.tipoPago = tipoPago;
	}

}
