package com.uniovi.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uniovi.entities.Pedido;
import com.uniovi.repositories.PedidosRepository;

@Service
public class PedidosService {
	
	@Autowired
	private PedidosRepository pedidosRepository;

	public void addPedido(Pedido pedido) {
		pedidosRepository.save(pedido);
	}

}
