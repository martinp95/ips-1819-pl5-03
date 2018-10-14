package com.uniovi.controllers;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.uniovi.entities.Pedido;
import com.uniovi.entities.User;
import com.uniovi.services.PedidosService;
<<<<<<< HEAD
=======
import com.uniovi.services.ProductosCarritoService;
>>>>>>> branch 'master' of https://martinp95@bitbucket.org/martinp95/ips-1819-pl5-03.git
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
<<<<<<< HEAD
=======
	@Autowired
	private ProductosCarritoService productosCarritoService;
>>>>>>> branch 'master' of https://martinp95@bitbucket.org/martinp95/ips-1819-pl5-03.git

	@RequestMapping(value = "/pedido/add")
	public String addProductoCarrito(Model model, Pageable pageable, Principal principal) {
		String email = principal.getName();
		User user = usersService.getUserByEmail(email);

		if (user.getProductosCarrito().size() > 0) {
			Pedido pedido = new Pedido(user, user.getProductosCarrito().size());
			pedidosService.addPedido(pedido);
			productosPedidoService.addProductosPedido(pedido, user.getProductosCarrito());
<<<<<<< HEAD
			// borrar el carrito
=======
			productosCarritoService.deleteCarrito(user.getProductosCarrito());
>>>>>>> branch 'master' of https://martinp95@bitbucket.org/martinp95/ips-1819-pl5-03.git
		} else {
			// devolver algun error o mandarlo a la vista directamente.
		}

		return "home";
	}

//	@RequestMapping("/pedidos")
//	public String getListado(Model model, @RequestParam(value = "", required = false) Principal principal) {
//
//		List<PedidoPablo> pedidos = new ArrayList<PedidoPablo>();
//		pedidos = almaceneroService.findNoAsignadosOrderByFecha();
//
//		model.addAttribute("pedidosList", pedidos);
//		return "almacenero/listPedidosNoAsignados";
//	}
//
//	/*
//	 * 
//	 * 
//	 * 
//	 * Esto deberia de ir en un controlador destinado para las accciones del
//	 * almacenero.
//	 * 
//	 * 
//	 */
//	@RequestMapping("/almacenero/add/{id}")
//	public String addPedido(@PathVariable Long id, Principal principal) {
//		String email = principal.getName();
//		User almacenero = usersService.getUserByEmail(email);
//		almaceneroService.addPedido(id, almacenero);
//		return "redirect:/almacenero/listPedidosNoAsignados";
//	}

}
