package com.uniovi.controllers;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.uniovi.entities.OrdenTrabajo;
import com.uniovi.entities.PedidosOrdenTrabajo;
import com.uniovi.entities.Producto;
import com.uniovi.entities.User;
import com.uniovi.services.AlmaceneroService;
import com.uniovi.services.OrdenTrabajoService;
import com.uniovi.services.PedidoOrdenTrabajoService;
import com.uniovi.services.PedidosService;
import com.uniovi.services.ProductosService;
import com.uniovi.services.UsersService;

@Controller
public class AlmaceneroController {

	@Autowired
	private UsersService usersService;
	@Autowired
	private AlmaceneroService almaceneroService;
	@Autowired
	private PedidosService pedidoService;
	@Autowired
	private OrdenTrabajoService ordenTrabajoService;
	@Autowired
	private PedidoOrdenTrabajoService pedidoOrdenTrabajoService;
	@Autowired
	private ProductosService productosService;

	@RequestMapping(value = "/almacenero/asignar", method = RequestMethod.POST)
	public String addPedido(Principal principal, Model model,
			@RequestParam(value = "pedidoID", required = false) String pedidoID) {

		if (pedidoID != null) {
			String email = principal.getName();
			User almacenero = usersService.getUserByEmail(email);
			OrdenTrabajo ordenTrabajo = new OrdenTrabajo(almacenero);
			ordenTrabajoService.addOrdenTrabajo(ordenTrabajo);
			PedidosOrdenTrabajo pedidoOrdenTrabajo = new PedidosOrdenTrabajo(
					pedidoService.findById(Long.parseLong(pedidoID)), ordenTrabajo);
			pedidoOrdenTrabajoService.addPedidoOrdenTrabajo(pedidoOrdenTrabajo);
		}
		return "redirect:/ordenesTrabajo";

	}

	@RequestMapping("/ordenesTrabajo")
	public String getListado(Model model, Principal principal) {
		String email = principal.getName();
		User almacenero = usersService.getUserByEmail(email);
		List<OrdenTrabajo> ordenesTrabajo = almaceneroService.findOrdenTrabajoByUser(almacenero);
		model.addAttribute("ordenTrabajoList", ordenesTrabajo);
		return "almacenero/listOrdenesTrabajo";
	}

	@RequestMapping("/ordenesTrabajo/productos")
	public String getProductosOrdenTrabajo(Principal principal, Model model,
			@RequestParam(value = "otID", required = false) String otID) {
		if (otID != null) {
			OrdenTrabajo ordenTrabajo = ordenTrabajoService.findById(Long.parseLong(otID));
			List<Producto> productos = productosService.findProductosByOt(ordenTrabajo);
			model.addAttribute("productosList", productos);
		}
		return "almacenero/listProductosOT";
	}


	@RequestMapping("/ordenTrabajo/marcarRecogido")
	public String marcarProductosOrdenTrabajoRecogido(Principal principal, Model model,
			@RequestParam(value = "codigoProducto", required = false) String codigoProducto) {
		return "almacenero/listProductosOT";
	}
	
	@RequestMapping("/ordenesTrabajo/empaquetar/productos/")
	public String getProductosEmpaquetarOrdenTrabajo(Principal principal, Model model,
			@RequestParam(value = "otID", required = false) String otID) {
		if (otID != null) {
			OrdenTrabajo ordenTrabajo = ordenTrabajoService.findById(Long.parseLong(otID));
			List<Producto> productos = productosService.findProductosByOt(ordenTrabajo);
			model.addAttribute("productosList", productos);
		}
		return "almacenero/listProductosEmpaquetar";
	}
	
	@RequestMapping("/ordenTrabajo/empaquetar")
	public String empaquetarOrdenTrabajo(Principal principal, Model model,
			@RequestParam(value = "codigoProducto", required = false) String codigoProducto) {
		if(codigoProducto!=null) {
			Producto producto = productosService.findByCodigo(codigoProducto);
			if(producto!=null) {
				productosService.empaquetarProducto(producto);//empaquetar producto o producto pedido?
				
			}
		}
		return "almacenero/listProductosEmpaquetar";
	}
}
