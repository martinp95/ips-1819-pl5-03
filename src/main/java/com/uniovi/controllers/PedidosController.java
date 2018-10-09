package com.uniovi.controllers;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.uniovi.entities.Almacenero;
import com.uniovi.entities.Pedido;
import com.uniovi.services.AlmaceneroService;

@Controller
public class PedidosController {
	
	@Autowired
	AlmaceneroService almaceneroService;
	
	@RequestMapping("/pedidos")
	public String getListado(Model model, @RequestParam(value = "", required = false) Principal principal) {

		List<Pedido> pedidos = new ArrayList<Pedido>();		
		pedidos = almaceneroService.findNoAsignadosOrderByFecha();
		
		model.addAttribute("pedidosList", pedidos);		
		return "almacenero/listPedidosNoAsignados";
	}
	
	@RequestMapping("/almacenero/add/{id}")
	public String addPedido(@PathVariable Long id,Principal principal) {
		String email = principal.getName();
		Almacenero almacenero = almaceneroService.getAlmaceneroByEmail(email);
		almaceneroService.addPedido(id,almacenero);
		return "redirect:/almacenero/listPedidosNoAsignados";
	}

}
