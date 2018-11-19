package com.uniovi.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uniovi.entities.PedidoAlmacen;
import com.uniovi.entities.Producto;
import com.uniovi.repositories.PedidoAlmacenRepository;

@Service
public class PedidoAlmacenService {
	
	@Autowired
	private PedidoAlmacenRepository pedidoAlmacenRepository;

	public void crearPedido(Producto producto,int cantidad) {
		pedidoAlmacenRepository.save(new PedidoAlmacen(producto,cantidad));
	}
	
	

}
