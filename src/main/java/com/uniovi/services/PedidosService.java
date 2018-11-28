package com.uniovi.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uniovi.entities.Pedido;
import com.uniovi.entities.ProductosPedido;
import com.uniovi.repositories.PedidosRepository;
import com.uniovi.repositories.ProductosPedidoRepository;

@Service
public class PedidosService {

	@Autowired
	private PedidosRepository pedidosRepository;
	@Autowired
	private ProductosPedidoRepository productosPedidoRepository;

	public void addPedido(Pedido pedido) {
		pedidosRepository.save(pedido);
	}

	public List<Pedido> findNoAsignadosOrderByFecha() {
		List<Pedido> pedidosNoAsignados = pedidosRepository.findPedidosNoAsignadosOrderByFecha();
		return pedidosNoAsignados;
	}

	public Pedido findById(Long id) {
		return pedidosRepository.findById(id);
	}

	public List<Pedido> findNoPagadosOrderByFecha() {
		List<Pedido> pedidosNoPagados = pedidosRepository.findPedidosNoPagadosOrderByFecha();
		return pedidosNoPagados;
	}

	public void pagarPedido(String pedidoID) {
		Pedido pedido = pedidosRepository.findById(Long.parseLong(pedidoID));
		pedido.setPagado(true);
		pedidosRepository.save(pedido);
	}

	public void setTotalPedido(Pedido pedido) {
		List<ProductosPedido> productosPedido = productosPedidoRepository.findByPedido(pedido);
		double total = 0;
		for (ProductosPedido pp : productosPedido) {
			total += pp.getPrecioUnidad() * pp.getCantidad()
					+ (pp.getProducto().getIva().getPorcentaje() * pp.getProducto().getPrecio() * pp.getCantidad());
		}
		pedido.setTotal(total);
		pedidosRepository.save(pedido);
	}

	public List<Object[]> informeVolumenCompras() {
		List<Pedido> pedidos = pedidosRepository.findAllOrderByFecha();
		List<Object[]> informe = new ArrayList<Object[]>();
		double cantidadParticular = 0;
		double cantidadEmpresa = 0;
		Date actual = null;
		if (!pedidos.isEmpty()) {
			actual = pedidos.get(0).getFecha();

			for (Pedido pedido : pedidos) {
				if (pedido.getFecha().equals(actual)) {
					if (pedido.getUser().getRole().contains("ROLE_PARTICULAR")) {
						cantidadParticular += pedido.getTotal();
					} else if (pedido.getUser().getRole().contains("ROLE_EMPRESA")) {
						cantidadEmpresa += pedido.getTotal();
					}
				} else {
					informe.add(new Object[] { actual.toString(), cantidadParticular + "", cantidadEmpresa + "" });
					cantidadParticular = 0;
					cantidadEmpresa = 0;
					actual = pedido.getFecha();
					if (pedido.getUser().getRole().contains("ROLE_PARTICULAR")) {
						cantidadParticular += pedido.getTotal();
					} else if (pedido.getUser().getRole().contains("ROLE_EMPRESA")) {
						cantidadEmpresa += pedido.getTotal();
					}
				}
			}
			informe.add(new Object[] { actual.toString(), cantidadParticular + "", cantidadEmpresa + "" });
		}
		return informe;
	}

	public List<Object[]> informeVolumenComprasMetodoPago() {
		List<Object[]> listado = pedidosRepository.findSumTotalPedidosGroupByFechaTipoPago();
		List<Object[]> informe = new ArrayList<Object[]>();
		Date actual = null;
		double factura = 0;
		double tarjeta = 0;
		double contrareembolso = 0;
		double transferencia = 0;
		if (!listado.isEmpty()) {
			actual = (Date) listado.get(0)[0];
			for (int i = 0; i < listado.size(); i++) {
				if (actual.equals((Date) listado.get(i)[0])) {
					if ("FACTURA".equals((String) listado.get(i)[1])) {
						factura = (Double) listado.get(i)[2];
					} else if ("TARJETA".equals((String) listado.get(i)[1])) {
						tarjeta = (Double) listado.get(i)[2];
					} else if ("CONTRAREEMBOLSO".equals((String) listado.get(i)[1])) {
						contrareembolso = (Double) listado.get(i)[2];
					} else if ("TRANSFERENCIA".equals((String) listado.get(i)[1])) {
						transferencia = (Double) listado.get(i)[2];
					}
				} else {
					informe.add(new Object[] { actual.toString(), factura + "", tarjeta + "", contrareembolso + "",
							transferencia + "" });
					factura = 0;
					tarjeta = 0;
					contrareembolso = 0;
					transferencia = 0;
					actual = (Date) listado.get(i)[0];
					if ("FACTURA".equals((String) listado.get(i)[1])) {
						factura = (Double) listado.get(i)[2];
					} else if ("TARJETA".equals((String) listado.get(i)[1])) {
						tarjeta = (Double) listado.get(i)[2];
					} else if ("CONTRAREEMBOLSO".equals((String) listado.get(i)[1])) {
						contrareembolso = (Double) listado.get(i)[2];
					} else if ("TRANSFERENCIA".equals((String) listado.get(i)[1])) {
						transferencia = (Double) listado.get(i)[2];
					}
				}
			}
			informe.add(new Object[] { actual.toString(), factura + "", tarjeta + "", contrareembolso + "",
					transferencia + "" });
		}
		return informe;
	}
}
