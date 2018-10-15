package com.uniovi.services;

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
	private PedidosRepository pedidosRepository;
	
	@Autowired
	private OrdenTrabajoRepository ordenTrabajoRepository;
	
	@Autowired
	private UsersRepository userRepository;

	public void asignarPedido(Long idPedido, User almacenero) {
		Pedido pedidoPersist = pedidosRepository.findById(idPedido);
		OrdenTrabajo otPersist = new OrdenTrabajo(pedidoPersist,almacenero);
		pedidoPersist.addOrdenTrabajo(otPersist);
		almacenero.addOrdenTrabajo(otPersist);		
		pedidosRepository.save(pedidoPersist);
		ordenTrabajoRepository.save(otPersist);
		userRepository.save(almacenero);
	}

	
}
