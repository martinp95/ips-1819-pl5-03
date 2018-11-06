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
import com.uniovi.entities.Pedido;
import com.uniovi.entities.PedidosOrdenTrabajo;
import com.uniovi.entities.ProductosPedido;
import com.uniovi.entities.User;
import com.uniovi.services.AlmaceneroService;
import com.uniovi.services.OrdenTrabajoService;
import com.uniovi.services.PaqueteService;
import com.uniovi.services.PedidoOrdenTrabajoService;
import com.uniovi.services.PedidosService;
import com.uniovi.services.ProductosPedidoService;
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
	@Autowired
	private ProductosPedidoService productosPedidoService;
	@Autowired
	private PaqueteService paqueteService;

	private final int NUM_MIN_PEDIDO = 5;
	private final int NUM_MAX_PEDIDO = 5;

	@RequestMapping(value = "/almacenero/asignar", method = RequestMethod.POST)
	public String addPedido(Principal principal, Model model,
			@RequestParam(value = "pedidoID", required = false) String pedidoID) {
		if (pedidoID != null) {
			String email = principal.getName();
			User almacenero = usersService.getUserByEmail(email);
			OrdenTrabajo ordenTrabajo  = new OrdenTrabajo(almacenero);
			ordenTrabajoService.addOrdenTrabajo(ordenTrabajo);
			PedidosOrdenTrabajo pedidoOrdenTrabajo;

			// tamaño pedido
			int tam = pedidoService.findById(Long.parseLong(pedidoID)).getSize();

			// if tamaño menor que NUM_MIN_PEDIDO
			if (tam < NUM_MIN_PEDIDO) {
				// añade pedido a la orden de trabajo
				pedidoOrdenTrabajo = new PedidosOrdenTrabajo(pedidoService.findById(Long.parseLong(pedidoID)),
						ordenTrabajo);
				pedidoOrdenTrabajoService.addPedidoOrdenTrabajo(pedidoOrdenTrabajo);
				// saca la lista del resto de pedidos
				List<Pedido> pedidosNoAsignados = pedidoService.findNoAsignadosOrderByFecha();
				// los va asignando si caben a la orden de trabajo existente
				for (Pedido p : pedidosNoAsignados) {
					if (p.getSize() + tam <= NUM_MIN_PEDIDO) {
						pedidoOrdenTrabajo = new PedidosOrdenTrabajo(p, ordenTrabajo);
						pedidoOrdenTrabajoService.addPedidoOrdenTrabajo(pedidoOrdenTrabajo);
						tam += p.getSize();
					}
				}
				return "redirect:/ordenesTrabajo";
			}

			// if tamaño mayor que NUM_MAX_PEDIDO
			if (tam > NUM_MAX_PEDIDO) {
				// variable auixliar con el tamaño para controlar los productos que faltan
				int tamaux = tam;
				// creo la variable porque solo interviene 1 pedido aqui
				Pedido pFinal = pedidoService.findById(Long.parseLong(pedidoID));
				// saco los 5 primeros productos del pedido y los añado
				//pedidoService.findPrimerosProductosPedido(Long.parseLong(pedidoID), NUM_MAX_PEDIDO);
				pedidoOrdenTrabajo = new PedidosOrdenTrabajo(pFinal, ordenTrabajo);
				pedidoOrdenTrabajoService.addPedidoOrdenTrabajo(pedidoOrdenTrabajo);
				tamaux -= NUM_MAX_PEDIDO;
				do {
					// creo la OT nueva pero esta vez sin almacenero asignado
					ordenTrabajo = new OrdenTrabajo(null);
					ordenTrabajoService.addOrdenTrabajo(ordenTrabajo);
					// busco la siguiente remesa de productos
					//p1 = ... ;
					// añadir pedido con productos que no esten en OT a otra OT
					pedidoOrdenTrabajo = new PedidosOrdenTrabajo(pFinal, ordenTrabajo);
					pedidoOrdenTrabajoService.addPedidoOrdenTrabajo(pedidoOrdenTrabajo);
					tamaux -= NUM_MAX_PEDIDO;
				}
				while (tamaux > 0);
				return "redirect:/ordenesTrabajo";
			}

			// para pedidos de 5 productos (funcionaria como en el sprint 1)
			pedidoOrdenTrabajo = new PedidosOrdenTrabajo(pedidoService.findById(Long.parseLong(pedidoID)),
					ordenTrabajo);
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
			List<Object> productos = productosService.findProductosByOt(ordenTrabajo);
			model.addAttribute("productosList", productos);
			model.addAttribute("otID", otID);
		}
		return "almacenero/listProductosOT";
	}

	@RequestMapping(value = "/ordenTrabajo/marcarRecogido", method = RequestMethod.POST)
	public String marcarProductosOrdenTrabajoRecogido(Principal principal, Model model,
			@RequestParam(value = "codigoProducto", required = false) String codigoProducto,
			@RequestParam(value = "otID", required = false) String otID,
			@RequestParam(value = "incidencia", required = false) String incidencia) {

		OrdenTrabajo ordenTrabajo = ordenTrabajoService.findById(Long.parseLong(otID));
		if (!codigoProducto.isEmpty()) {
			if (incidencia.isEmpty()) {
				if (productosService.isProductoInOT(codigoProducto, otID)) {
					List<ProductosPedido> productoPedido = productosPedidoService
							.findProductoPedidoByOtAndProducto(codigoProducto, otID);
					productosPedidoService.decrementarCantidadPorRecoger(productoPedido.get(0));
				} else {
					model.addAttribute("error",
							"ERROR:El producto escaneado no se encuentra en la OT o ya ha sido recogido");
				}
			} else {
				if (productosService.isProductoInOT(codigoProducto, otID)) {
					List<ProductosPedido> productoPedido = productosPedidoService
							.findProductoPedidoByOtAndProducto(codigoProducto, otID);
					productosPedidoService.addIncidencia(productoPedido.get(0), incidencia);
					ordenTrabajoService.addIncidencia(ordenTrabajo);
					model.addAttribute("error", "Se ha registrado la incidencia con exito");
				} else {
					model.addAttribute("error",
							"ERROR:El producto escaneado no se encuentra en la OT o ya ha sido recogido");
				}
			}
		}
		List<Object> productos = productosService.findProductosByOt(ordenTrabajo);
		model.addAttribute("productosList", productos);
		model.addAttribute("otID", otID);
		return "almacenero/listProductosOT";
	}

	@RequestMapping("/ordenesTrabajo/noIncidence")
	public String getOTSinIncidencia(Model model, Principal principal) {
		String email = principal.getName();
		User almacenero = usersService.getUserByEmail(email);
		List<OrdenTrabajo> ordenesTrabajo = almaceneroService.findOrdenTrabajoByUser(almacenero);
		model.addAttribute("ordenTrabajoList", ordenesTrabajo);
		return "almacenero/listOTSinIncidencias";
	}

	@RequestMapping("/ordenesTrabajo/empaquetar/productos/")
	public String getProductosEmpaquetarOrdenTrabajo(Principal principal, Model model,
			@RequestParam(value = "otID", required = false) String otID) {
		if (otID != null) {
			OrdenTrabajo ordenTrabajo = ordenTrabajoService.findById(Long.parseLong(otID));
			List<ProductosPedido> productos = productosService.findProductosByOtNoIncidenciaNoEmpaquetado(ordenTrabajo);
			model.addAttribute("productosList", productos);
		}
		return "almacenero/listProductosEmpaquetar";
	}

	@RequestMapping("/ordenTrabajo/empaquetar")
	public String empaquetarOrdenTrabajo(Principal principal, Model model,
			@RequestParam(value = "idProducto", required = false) String id) {
		if (id != null) {
			ProductosPedido producto = productosPedidoService.findByProductoId(id);
			if (producto != null) {
				paqueteService.empaquetarProducto(producto);

			}
		}
		return "almacenero/listProductosEmpaquetar";
	}
}
