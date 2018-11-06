package com.uniovi.services;

import java.util.List;

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

	public List<Pedido> findNoAsignadosOrderByFecha() {
		List<Pedido> pedidosNoAsignados = pedidosRepository.findPedidosNoAsignadosOrderByFecha();
		return pedidosNoAsignados;
	}
	
	public Pedido findById(Long id) {
		return pedidosRepository.findById(id);
	}

	public List<Pedido> findNoPagadosOrderByFecha() {
		List<Pedido> pedidosNoPagados = pedidosRepository.findPedidosNoPagadosOrderByFecha();
		return pedidosNoPagados;
	}

	public void pagarPedido(String pedidoID) {
		Pedido pedido = pedidosRepository.findById(Long.parseLong(pedidoID));
		pedido.setPagado(true);
		pedidosRepository.save(pedido);
		
	}

	public Pedido findPrimerosProductosPedido(long id, int NUM_MAX_PEDIDO) {
		//return null;
		return pedidosRepository.findPrimerosProductosPedido(id, NUM_MAX_PEDIDO);
	}

}
