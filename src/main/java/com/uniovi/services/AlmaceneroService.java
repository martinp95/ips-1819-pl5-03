package com.uniovi.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uniovi.entities.OrdenTrabajo;
import com.uniovi.entities.Pedido;
import com.uniovi.entities.User;
import com.uniovi.repositories.OrdenTrabajoRepository;
import com.uniovi.repositories.PedidosRepository;
import com.uniovi.repositories.UsersRepository;

@Service
public class AlmaceneroService {

	@Autowired
	PedidosRepository pedidosRepository;	
	
	@Autowired
	UsersRepository usersRepository;
	
	@Autowired
	OrdenTrabajoRepository ordenTrabajoRepository;


	public List<Pedido> findNoAsignadosOrderByFecha() {
		return pedidosRepository.findNoAsignadosOrderByFecha();
	}

	
	public void addPedido(Long idPedido, User almacenero) {
		Pedido pedido = pedidosRepository.findById(idPedido);
		OrdenTrabajo ordenTrabajo = new OrdenTrabajo(pedido,almacenero);
		ordenTrabajoRepository.save(ordenTrabajo);
		pedido.setAsignado(true);
		pedido.addOrdenTrabajo(ordenTrabajo);		
		pedidosRepository.save(pedido);
		almacenero.addOrdenTrabajo(ordenTrabajo);
		usersRepository.save(almacenero);		
	}


	public void addPedido(Pedido pedido) {
		pedidosRepository.save(pedido);
		
	}
}
