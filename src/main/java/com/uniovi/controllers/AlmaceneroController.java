package com.uniovi.controllers;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.uniovi.entities.OrdenTrabajo;
import com.uniovi.entities.Pedido;
import com.uniovi.entities.User;
import com.uniovi.services.AlmaceneroService;
import com.uniovi.services.PedidosService;
import com.uniovi.services.UsersService;

@Controller
public class AlmaceneroController {
	
	@Autowired
	private UsersService usersService;
	@Autowired
	private AlmaceneroService almaceneroService;
	@Autowired
	private PedidosService pedidosService;

	
	@RequestMapping("/almacenero/asignar/{id}")
	public String addPedido(@PathVariable Long id, Principal principal) {
		String email = principal.getName();
		User almacenero = usersService.getUserByEmail(email);
		Pedido pedido = pedidosService.findById(id);
		almaceneroService.asignarPedido(pedido, almacenero);
		return "almacenero/listPedidosNoAsignados :: tableListPedidos";		
		
	}
	
	@RequestMapping("/ordenesTrabajo")
	public String getListado(Model model, Principal principal) {
		String email = principal.getName();
		User almacenero = usersService.getUserByEmail(email);
		List<OrdenTrabajo> ordenesTrabajo = almaceneroService.findOrdenTrabajoByUser(almacenero);		
		model.addAttribute("ordenTrabajoList", ordenesTrabajo);
		return "almacenero/listOrdenesTrabajo";
	}

}
