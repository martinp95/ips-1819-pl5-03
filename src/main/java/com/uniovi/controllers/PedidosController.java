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
import com.uniovi.entities.ProductosCarrito;
import com.uniovi.entities.User;
import com.uniovi.services.PedidosService;
import com.uniovi.services.ProductosCarritoService;
import com.uniovi.services.ProductosPedidoService;
import com.uniovi.services.ProductosService;
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
	@Autowired
	private ProductosService productosService;

	@RequestMapping(value = "/pedido/add")
	public String addPedido(Model model, Pageable pageable, Principal principal) {
		String email = principal.getName();
		User user = usersService.getUserByEmail(email);
		if (user.getProductosCarrito().size() > 0) {
			int size = 0;
			for (ProductosCarrito productoCarro : user.getProductosCarrito()) {
				size += productoCarro.getCantidad();
			}
			model.addAttribute("productosList", user.getProductosCarrito());
			Pedido pedido = new Pedido(user, size);
			pedido.setPagado(true);
			pedidosService.addPedido(pedido);
			productosPedidoService.addProductosPedido(pedido, user.getProductosCarrito());
			productosService.descontarStock(user.getProductosCarrito());
			// meter el total en el pedido.
			pedidosService.setTotalPedido(pedido);
			productosCarritoService.deleteCarrito(user);
		} else {
			return "redirect:/carrito";
		}
		return "carrito/pedidoEmpresa";
	}

	@RequestMapping("/pedidos")
	public String getListado(Model model, @RequestParam(value = "", required = false) Principal principal) {

		List<Pedido> pedidos = new ArrayList<Pedido>();
		pedidos = pedidosService.findNoAsignadosOrderByFecha();

		model.addAttribute("pedidosList", pedidos);
		return "almacenero/listPedidosNoAsignados";
	}

	@RequestMapping("/pedido/metodosPago")
	public String getMetodosPago(Model model, Principal principal) {
		String email = principal.getName();
		User user = usersService.getUserByEmail(email);
		if (user.getProductosCarrito().size() > 0) {
			return "carrito/metodosPago";
		} else
			return "redirect:/productos";
	}

	@RequestMapping("/pedido/pagar")
	public String getPagar(Model model, Principal principal,
			@RequestParam(value = "metodoPago", required = false) String metodoPago) {
		if (metodoPago.equals("Tarjeta"))
			return "carrito/tarjeta";
		else {
			String email = principal.getName();
			User user = usersService.getUserByEmail(email);
			if (user.getProductosCarrito().size() > 0) {
				int size = 0;
				for (ProductosCarrito productoCarro : user.getProductosCarrito()) {
					size += productoCarro.getCantidad();
				}
				Pedido pedido = new Pedido(user, size);
				if (metodoPago.equals("Contrareembolso"))
					pedido.setPagado(true);
				pedidosService.addPedido(pedido);
				productosPedidoService.addProductosPedido(pedido, user.getProductosCarrito());
				productosService.descontarStock(user.getProductosCarrito());
				productosCarritoService.deleteCarrito(user);
			} else {
				return "redirect:/carrito";
			}
		
		if (metodoPago.equals("Transferencia"))
			return "carrito/transferencia";
		else
			return "redirect:/productos";
		}

	}

	@RequestMapping("/pedidos/noPagados")
	public String getPedidosNoPagados(Model model, @RequestParam(value = "", required = false) Principal principal) {
		List<Pedido> pedidos = new ArrayList<Pedido>();
		pedidos = pedidosService.findNoPagadosOrderByFecha();
		model.addAttribute("pedidosList", pedidos);
		return "carrito/pagarTransferencias";
	}

	@RequestMapping("/pedido/pagar/transferencia")
	public String pagarPedidoTransferencia(Principal principal, Model model,
			@RequestParam(value = "pedidoID", required = false) String pedidoID) {
		if (pedidoID != null) {
			pedidosService.pagarPedido(pedidoID);
		}
		return "redirect:/pedidos/noPagados";
	}

	@RequestMapping("/pedido/tarjeta")
	public String getSimulacionTarjeta(Model model, Principal principal) {
		String email = principal.getName();
		User user = usersService.getUserByEmail(email);
		if (user.getProductosCarrito().size() > 0) {
			int size = 0;
			for (ProductosCarrito productoCarro : user.getProductosCarrito()) {
				size += productoCarro.getCantidad();
			}
			Pedido pedido = new Pedido(user, size);
			pedido.setPagado(true);
			pedidosService.addPedido(pedido);
			productosPedidoService.addProductosPedido(pedido, user.getProductosCarrito());
			productosService.descontarStock(user.getProductosCarrito());
			productosCarritoService.deleteCarrito(user);
		}
		return "redirect:/productos";
	}

	@RequestMapping("/pedido/transferencia")
	public String getSimulacionTransferencia(Model model,
			@RequestParam(value = "", required = false) Principal principal) {
		return "redirect:/productos";
	}

}