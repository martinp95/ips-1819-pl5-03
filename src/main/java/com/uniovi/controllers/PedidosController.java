package com.uniovi.controllers;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.uniovi.entities.Pedido;
import com.uniovi.entities.User;
import com.uniovi.services.PedidosService;
import com.uniovi.services.ProductosCarritoService;
import com.uniovi.services.ProductosPedidoService;
import com.uniovi.services.UsersService;

@Controller
public class PedidosController {
	@Autowired
	private UsersService usersService;
	@Autowired
	private PedidosService pedidosService;
	@Autowired
	private ProductosPedidoService productosPedidoService;
	@Autowired
	private ProductosCarritoService productosCarritoService;

	@RequestMapping(value = "/pedido/add")
	public String addProductoCarrito(Model model, Pageable pageable, Principal principal) {
		String email = principal.getName();
		User user = usersService.getUserByEmail(email);

		if (user.getProductosCarrito().size() > 0) {
			Pedido pedido = new Pedido(user, user.getProductosCarrito().size());
			pedidosService.addPedido(pedido);
			productosPedidoService.addProductosPedido(pedido, user.getProductosCarrito());
			productosCarritoService.deleteCarrito(user);
		} else {
			return "redirect:/carrito";
		}
		return "redirect:/productos";
	}

	@RequestMapping("/pedidos")
	public String getListado(Model model, @RequestParam(value = "", required = false) Principal principal) {

		List<Pedido> pedidos = new ArrayList<Pedido>();
		pedidos = pedidosService.findNoAsignadosOrderByFecha();

		model.addAttribute("pedidosList", pedidos);
		return "almacenero/listPedidosNoAsignados";
	}
	

}
