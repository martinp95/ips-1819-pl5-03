package com.uniovi.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uniovi.entities.PedidoAlmacen;
import com.uniovi.entities.Producto;
import com.uniovi.repositories.PedidoAlmacenRepository;

@Service
public class PedidoAlmacenService {

	@Autowired
	private PedidoAlmacenRepository pedidoAlmacenRepository;

	public void crearPedido(Producto producto, int cantidad) {
		pedidoAlmacenRepository.save(new PedidoAlmacen(producto, cantidad));
	}

	public List<PedidoAlmacen> findAll() {
		return pedidoAlmacenRepository.findAll();
	}

	public PedidoAlmacen findByID(Long id) {
		return pedidoAlmacenRepository.findOne(id);
	}

	public void remove(PedidoAlmacen pa) {
		pedidoAlmacenRepository.delete(pa);
	}

	public void update(PedidoAlmacen pedidoAlmacen) {
		pedidoAlmacenRepository.save(pedidoAlmacen);
	}

	public PedidoAlmacen findByProducto(Producto producto) {
		return pedidoAlmacenRepository.findPedidoAlmacenByProducto(producto);
	}
}
