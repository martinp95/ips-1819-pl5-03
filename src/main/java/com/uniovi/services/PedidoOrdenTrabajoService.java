package com.uniovi.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uniovi.entities.PedidosOrdenTrabajo;
import com.uniovi.repositories.PedidoOrdenTrabajoRepository;

@Service
public class PedidoOrdenTrabajoService {

	@Autowired
	private PedidoOrdenTrabajoRepository pedidoOrdenTrabajoRepository;

	public void addPedidoOrdenTrabajo(PedidosOrdenTrabajo pedidoOrdenTrabajo) {
		pedidoOrdenTrabajoRepository.save(pedidoOrdenTrabajo);
	}

}
