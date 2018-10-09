package com.uniovi.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uniovi.entities.Almacenero;
import com.uniovi.entities.Pedido;
import com.uniovi.repositories.AlmaceneroRepository;
import com.uniovi.repositories.PedidosRepository;

@Service
public class AlmaceneroService {

	@Autowired
	PedidosRepository pedidosRepository;
	
	@Autowired
	AlmaceneroRepository almaceneroRepository;

	public List<Pedido> findNoAsignadosOrderByFecha() {
		return pedidosRepository.findNoAsignadosOrderByFecha();
	}

	public Almacenero getAlmaceneroByEmail(String email) {
		return almaceneroRepository.findByEmail(email);
	}

	public void addPedido(Long id, Almacenero almacenero) {
		//
		
	}
}
