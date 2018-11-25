package com.uniovi.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uniovi.entities.OrdenTrabajo;
import com.uniovi.entities.Pedido;
import com.uniovi.entities.Producto;
import com.uniovi.entities.ProductosPedido;
import com.uniovi.entities.User;
import com.uniovi.repositories.OrdenTrabajoRepository;
import com.uniovi.repositories.PedidosRepository;
import com.uniovi.repositories.ProductosPedidoRepository;
import com.uniovi.repositories.ProductosRepository;

@Service
public class OrdenTrabajoService {

	@Autowired
	private OrdenTrabajoRepository ordenTrabajoRepository;
	@Autowired
	private PedidosRepository pedidosRepository;
	@Autowired
	private ProductosRepository productosRepository;
	@Autowired
	private ProductosPedidoRepository productoPedidoRepository;

	public void addOrdenTrabajo(OrdenTrabajo ordenTrabjo) {
		ordenTrabajoRepository.save(ordenTrabjo);
	}

	public OrdenTrabajo findById(Long id) {
		return ordenTrabajoRepository.findOne(id);
	}

	public void addIncidencia(OrdenTrabajo ordenTrabajo) {
		ordenTrabajo.setIncidencia(true);
		ordenTrabajoRepository.save(ordenTrabajo);
	}

	public void eliminarIncidencia(OrdenTrabajo ordenTrabajo) {
		ordenTrabajo.setIncidencia(false);
		ordenTrabajoRepository.save(ordenTrabajo);
	}

	public void cambiarEmpaquetada(OrdenTrabajo ordenTrabajo) {
		ordenTrabajo.setEmpaquetada(true);
		ordenTrabajoRepository.save(ordenTrabajo);
	}

	public String generarAlbaran(OrdenTrabajo ordenTrabajo) {
		String albaran = "";

		List<Pedido> pedidos = pedidosRepository.findPedidoByOrdenTrabajo(ordenTrabajo);
		for (Pedido pedido : pedidos) {
			albaran += "Albaran:\nPaquete Etiqueta:";
			albaran += ordenTrabajo.getPaquete().getId() + "\n";
			albaran += "\tPedido:" + pedido.getId() + ", " + pedido.getFecha() + "\n";
			User user = pedido.getUser();
			albaran += "\t\tUsuario:" + user.getName() + ", Email:" + user.getEmail() + "\n";
			albaran += "\t\tProductos:\n";
			List<Producto> productos = productosRepository.findProductosByPedido(pedido);
			for (Producto producto : productos) {
				ProductosPedido pp = productoPedidoRepository.findProductoPedidoByPedidoAndProducto(pedido, producto);
				albaran += "\t\t\tNombre:" + producto.getName() + ", Descripcion:" + producto.getDescription()
						+ ", Precio unidad:" + producto.getPrecio() + ", Cantidad:" + pp.getCantidad() + "\n";
			}
			albaran += "--------------------------------------------------------------------------------------------\n";
		}
		return albaran;
	}
}
