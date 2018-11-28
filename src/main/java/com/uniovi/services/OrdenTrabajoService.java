package com.uniovi.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uniovi.entities.OrdenTrabajo;
import com.uniovi.entities.Pedido;
import com.uniovi.entities.PedidosOrdenTrabajo;
import com.uniovi.entities.Producto;
import com.uniovi.entities.ProductosPedido;
import com.uniovi.entities.User;
import com.uniovi.repositories.OrdenTrabajoRepository;
import com.uniovi.repositories.PedidosRepository;
import com.uniovi.repositories.ProductosPedidoRepository;
import com.uniovi.repositories.ProductosRepository;
import com.uniovi.util.Util;

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
	@Autowired
	private UsersService usersService;

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
				albaran += "\t\t\tCategoria:" + producto.getName() + ", Nombre:" + producto.getDescription()
				+ ", Precio unidad sin IVA:" + producto.getPrecio() + ", Precio unidad con IVA: "
				+ ((producto.getPrecio() * producto.getIva().getPorcentaje()) + producto.getPrecio())
				+ ", Cantidad:" + pp.getCantidad() + "\n";
			}
			albaran += "--------------------------------------------------------------------------------------------\n";

		}
		return albaran;
	}

	public List<Date> findDatesEntreInicioFin() {
		List<Date> fechas = new ArrayList<Date>();
		List<OrdenTrabajo> ots = ordenTrabajoRepository.findAllOrderByFecha();
		if (!ots.isEmpty()) {
			Date inicio = ots.get(0).getFecha();
			Date fin = ots.get(ots.size() - 1).getFecha();

			while (inicio.before(fin)) {
				fechas.add(inicio);
				inicio = Util.addDays(inicio, 1);
			}
			fechas.add(fin);
		}
		return fechas;
	}

	public List<OrdenTrabajo> findAllOrderByFecha() {
		return ordenTrabajoRepository.findAllOrderByFecha();
	}

	public List<Object[]> informeOts() {
		List<Date> fechas = findDatesEntreInicioFin();
		List<Object[]> informe = new ArrayList<Object[]>();
		List<OrdenTrabajo> ots = ordenTrabajoRepository.findAllOrderByFecha();

		List<User> almaceneros = usersService.findAllAlmacenero();
		HashMap<Long,Integer> empleadoOt;

		for(Date fecha : fechas) {
			//inicializar hasmap por cada fecha
			empleadoOt = new HashMap<Long,Integer>();
			for(User u: almaceneros) {
				empleadoOt.put(u.getId(), 0);
			}		

			for(OrdenTrabajo ot : ots) {
				if(ot.getFecha().equals(fecha) && ot.getAlmacenero() != null) {
					//aumentamos 1 ot asignada al almacenero
					int numAux = empleadoOt.get(ot.getAlmacenero().getId()) + 1;
					empleadoOt.put(ot.getAlmacenero().getId(),numAux);
				}
			}
			Object[] entrada = new Object[100];
			entrada[0] = fecha;
			int i=1;
			for (Integer numeroOt : empleadoOt.values()) {
				entrada[i] =numeroOt;
				i++;			    
			}
			informe.add(entrada);
		}
		return informe;
	}

	public List<Object[]> informeOtsRecogidas() {
		List<Date> fechas = findDatesEntreInicioFin();
		List<Object[]> informe = new ArrayList<Object[]>();
		List<OrdenTrabajo> ots = ordenTrabajoRepository.findAllOrderByFecha();

		List<User> almaceneros = usersService.findAllAlmacenero();
		HashMap<Long,Integer> empleadoOt;

		for(Date fecha : fechas) {
			//inicializar hasmap por cada fecha
			empleadoOt = new HashMap<Long,Integer>();
			for(User u: almaceneros) {
				empleadoOt.put(u.getId(), 0);
			}		

			for(OrdenTrabajo ot : ots) {
				if(ot.getFecha().equals(fecha) && ot.getAlmacenero() != null) {
					for (PedidosOrdenTrabajo pot: ot.getPedidoOrdenesTrabajo()) {
						for (ProductosPedido pp: pot.getPedido().getProductosPedido()) {
							int num = pp.getCantidad() - pp.getCantidadPorRecoger();
							int numAux = empleadoOt.get(ot.getAlmacenero().getId()) + num;
							empleadoOt.put(ot.getAlmacenero().getId(),numAux);
						}
					}
				}
			}
			Object[] entrada = new Object[100];
			entrada[0] = fecha;
			int i=1;
			for (Integer numeroOt : empleadoOt.values()) {
				entrada[i] =numeroOt;
				i++;			    
			}
			informe.add(entrada);
		}
		return informe;
	}

	public List<Object[]> informeOtsEmpaquetadas() {
		List<Date> fechas = findDatesEntreInicioFin();
		List<Object[]> informe = new ArrayList<Object[]>();
		List<OrdenTrabajo> ots = ordenTrabajoRepository.findAllOrderByFecha();

		List<User> almaceneros = usersService.findAllAlmacenero();
		HashMap<Long,Integer> empleadoOt;

		for(Date fecha : fechas) {
			//inicializar hasmap por cada fecha
			empleadoOt = new HashMap<Long,Integer>();
			for(User u: almaceneros) {
				empleadoOt.put(u.getId(), 0);
			}		

			for(OrdenTrabajo ot : ots) {
				if(ot.getFecha().equals(fecha) && ot.getAlmacenero() != null) {
					for (PedidosOrdenTrabajo pot: ot.getPedidoOrdenesTrabajo()) {
						for (ProductosPedido pp: pot.getPedido().getProductosPedido()) {
							int num = pp.getCantidad() - pp.getCantidadPorEmpaquetar();
							int numAux = empleadoOt.get(ot.getAlmacenero().getId()) + num;
							empleadoOt.put(ot.getAlmacenero().getId(),numAux);
						}
					}
				}
			}
			Object[] entrada = new Object[100];
			entrada[0] = fecha;
			int i=1;
			for (Integer numeroOt : empleadoOt.values()) {
				entrada[i] =numeroOt;
				i++;			    
			}
			informe.add(entrada);
		}
		return informe;
	}

}
