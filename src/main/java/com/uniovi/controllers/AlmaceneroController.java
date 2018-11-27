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
import com.uniovi.entities.PedidoAlmacen;
import com.uniovi.entities.PedidosOrdenTrabajo;
import com.uniovi.entities.ProductosPedido;
import com.uniovi.entities.User;
import com.uniovi.services.AlmaceneroService;
import com.uniovi.services.OrdenTrabajoService;
import com.uniovi.services.PaqueteService;
import com.uniovi.services.PedidoAlmacenService;
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
	@Autowired
	private PedidoAlmacenService pedidoAlmacenService;

	private final int NUM_MIN_PEDIDO = 5;
	private final int NUM_MAX_PEDIDO = 5;

	@RequestMapping(value = "/almacenero/asignar", method = RequestMethod.POST)
	public String addPedido(Principal principal, Model model,
			@RequestParam(value = "pedidoID", required = false) String pedidoID) {
		if (pedidoID != null) {
			String email = principal.getName();
			User almacenero = usersService.getUserByEmail(email);
			OrdenTrabajo ordenTrabajo = new OrdenTrabajo(almacenero);
			ordenTrabajoService.addOrdenTrabajo(ordenTrabajo);
			PedidosOrdenTrabajo pedidoOrdenTrabajo;
			int tam = pedidoService.findById(Long.parseLong(pedidoID)).getSize();
			if (tam < NUM_MIN_PEDIDO) {
				pedidoOrdenTrabajo = new PedidosOrdenTrabajo(pedidoService.findById(Long.parseLong(pedidoID)),
						ordenTrabajo);
				pedidoOrdenTrabajoService.addPedidoOrdenTrabajo(pedidoOrdenTrabajo);
				List<Pedido> pedidosNoAsignados = pedidoService.findNoAsignadosOrderByFecha();
				for (Pedido p : pedidosNoAsignados) {
					if (p.getSize() + tam <= NUM_MIN_PEDIDO) {
						pedidoOrdenTrabajo = new PedidosOrdenTrabajo(p, ordenTrabajo);
						pedidoOrdenTrabajoService.addPedidoOrdenTrabajo(pedidoOrdenTrabajo);
						tam += p.getSize();
					}
				}
				return "redirect:/ordenesTrabajo";
			}
			if (tam > NUM_MAX_PEDIDO) {
				int tamaux = tam;
				Pedido pFinal = pedidoService.findById(Long.parseLong(pedidoID));
				pedidoOrdenTrabajo = new PedidosOrdenTrabajo(pFinal, ordenTrabajo);
				pedidoOrdenTrabajoService.addPedidoOrdenTrabajo(pedidoOrdenTrabajo);
				tamaux -= NUM_MAX_PEDIDO;
				do {
					ordenTrabajo = new OrdenTrabajo(null);
					ordenTrabajoService.addOrdenTrabajo(ordenTrabajo);
					pedidoOrdenTrabajo = new PedidosOrdenTrabajo(pFinal, ordenTrabajo);
					pedidoOrdenTrabajoService.addPedidoOrdenTrabajo(pedidoOrdenTrabajo);
					tamaux -= NUM_MAX_PEDIDO;
				} while (tamaux > 0);
				return "redirect:/ordenesTrabajo";
			}
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
		if (productos.isEmpty()) {
			ordenTrabajoService.eliminarIncidencia(ordenTrabajo);
		}
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

	@RequestMapping("/ordenesTrabajo/empaquetar/productos")
	public String getProductosEmpaquetarOrdenTrabajo(Principal principal, Model model,
			@RequestParam(value = "otID", required = false) String otID) {
		if (otID != null) {
			OrdenTrabajo ordenTrabajo = ordenTrabajoService.findById(Long.parseLong(otID));
			List<Object> productos = productosService.findProductosByOtAndNoEmpaquetado(ordenTrabajo);
			paqueteService.crearPaqueteDeOT(ordenTrabajo);
			model.addAttribute("productosList", productos);
			model.addAttribute("otID", otID);
		}
		return "almacenero/listProductosEmpaquetar";
	}

	@RequestMapping("/ordenTrabajo/empaquetar")
	public String empaquetarOrdenTrabajo(Principal principal, Model model,
			@RequestParam(value = "idProducto", required = false) String idProducto,
			@RequestParam(value = "otID", required = false) String otID) {
		if (!idProducto.isEmpty()) {
			List<ProductosPedido> productosPedido = productosPedidoService
					.findProductoPedidoByOtAndProductoAndNoEmpaquetado(idProducto, otID);
			if (!productosPedido.isEmpty()) {
				ProductosPedido productoPedido = productosPedido.get(0);
				if (productoPedido != null && productoPedido.getCantidadPorEmpaquetar() == 0) {
					paqueteService.empaquetarProducto(productoPedido, Long.parseLong(otID));
				} else if (productoPedido != null) {
					paqueteService.decrementarCantidadPorEmpaquetar(productoPedido);
				}
			}
		}
		OrdenTrabajo ordenTrabajo = ordenTrabajoService.findById(Long.parseLong(otID));
		List<Object> productos = productosService.findProductosByOtAndNoEmpaquetado(ordenTrabajo);
		if (productos.isEmpty()) {
			ordenTrabajoService.cambiarEmpaquetada(ordenTrabajo);
			String albaran = ordenTrabajoService.generarAlbaran(ordenTrabajo);
			model.addAttribute("albaran", albaran);
			return "almacenero/albaran";
		}
		model.addAttribute("productosList", productos);
		model.addAttribute("otID", otID);
		return "almacenero/listProductosEmpaquetar";
	}

	@RequestMapping("/ordenesTrabajo/empaquetadas")
	public String getOtsEmpaquetadas(Principal principal, Model model) {
		String email = principal.getName();
		User almacenero = usersService.getUserByEmail(email);
		List<OrdenTrabajo> ordenesTrabajo = almaceneroService.findOrdenTabajoByUserAndEmpaquetada(almacenero);
		model.addAttribute("ordenTrabajoList", ordenesTrabajo);
		return "almacenero/listOTEmpaquetadas";
	}

	@RequestMapping("/ordenesTrabajo/albaran")
	public String generarAlbaran(Principal principal, Model model,
			@RequestParam(value = "otID", required = false) String otID) {
		if (otID != null) {
			OrdenTrabajo ordenTrabajo = ordenTrabajoService.findById(Long.parseLong(otID));
			String albaran = ordenTrabajoService.generarAlbaran(ordenTrabajo);
			model.addAttribute("albaran", albaran);
		}
		return "almacenero/albaran";
	}

	@RequestMapping("/pedidosAlmacen")
	public String getPedidosAlmacen(Principal principal, Model model) {
		List<PedidoAlmacen> pedidoAlmacen = pedidoAlmacenService.findAll();
		model.addAttribute("pedidoAlmacen", pedidoAlmacen);
		return "almacenero/listPedidosAlmacen";
	}

	@RequestMapping("/pedidosAlmacen/marcarEntregado")
	public String marcarPedidoAlmacenEntregado(Principal principal, Model model,
			@RequestParam(value = "paID", required = false) String paID) {
		if (paID != null) {
			PedidoAlmacen pa = pedidoAlmacenService.findByID(Long.parseLong(paID));
			productosService.anadirStock(pa.getProducto(), pa.getCantidad());
			pedidoAlmacenService.remove(pa);
		}
		List<PedidoAlmacen> pedidoAlmacen = pedidoAlmacenService.findAll();
		model.addAttribute("pedidoAlmacen", pedidoAlmacen);
		return "almacenero/listPedidosAlmacen";
	}

	@RequestMapping("informe/volumenCompras")
	public String informeVolumenCompras(Principal principal, Model model) {
		List<Object[]> informe = pedidoService.informeVolumenCompras();
		model.addAttribute("informe", informe);
		return "almacenero/informeVolumenCompra";
	}

	@RequestMapping("informe/volumenComprasMetodoPago")
	public String informeVolumenComprasMetodoPago(Principal principal, Model model) {
		List<Object[]> informe = pedidoService.informeVolumenComprasMetodoPago();
		model.addAttribute("informe", informe);
		return "";
	}
}
