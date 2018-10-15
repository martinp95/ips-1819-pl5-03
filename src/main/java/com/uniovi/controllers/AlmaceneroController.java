package com.uniovi.controllers;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.uniovi.entities.OrdenTrabajo;
import com.uniovi.entities.User;
import com.uniovi.services.AlmaceneroService;
import com.uniovi.services.UsersService;

@Controller
public class AlmaceneroController {
	
	@Autowired
	private UsersService usersService;
	@Autowired
	private AlmaceneroService almaceneroService;

	
	@RequestMapping("/almacenero/asignar/{id}")
	public String addPedido(@PathVariable Long id, Principal principal) {
		String email = principal.getName();
		User almacenero = usersService.getUserByEmail(email);
		almaceneroService.asignarPedido(id, almacenero);
		return "almacenero/listPedidosNoAsignados :: tableListPedidos";		
		
	}
	
	@RequestMapping("/ordenesTrabajo")
	public String getListado(Model model, @RequestParam(value = "", required = false) Principal principal) {
		String email = principal.getName();
		User almacenero = usersService.getUserByEmail(email);
		List<OrdenTrabajo> ordenesTrabajo = almaceneroService.findOrdenTrabajoByUser(almacenero);		
		model.addAttribute("ordenTrabajoList", ordenesTrabajo);
		return "almacenero/listOrdenesTrabajo";
	}

}
