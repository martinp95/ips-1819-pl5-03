package com.uniovi.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uniovi.entities.OrdenTrabajo;
import com.uniovi.entities.Pedido;
import com.uniovi.entities.User;
import com.uniovi.repositories.OrdenTrabajoRepository;

@Service
public class AlmaceneroService {
	
	@Autowired
	private PedidosService pedidosService;

	@Autowired
	private OrdenTrabajoRepository ordenTrabajoRepository;

	public void asignarPedidos(User almacenero, String... idPedido) {
		for (String id : idPedido) {
			Pedido pedido = pedidosService.findById(Long.parseLong(id));
			OrdenTrabajo otPersist = new OrdenTrabajo(pedido, almacenero);
			ordenTrabajoRepository.save(otPersist);
		}
	}

	public List<OrdenTrabajo> findOrdenTrabajoByUser(User almacenero) {

		return ordenTrabajoRepository.findByAlmacenero(almacenero);
	}
}
