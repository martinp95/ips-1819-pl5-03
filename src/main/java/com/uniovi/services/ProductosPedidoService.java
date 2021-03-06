package com.uniovi.services;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uniovi.entities.Pedido;
import com.uniovi.entities.ProductosCarrito;
import com.uniovi.entities.ProductosPedido;
import com.uniovi.repositories.ProductosPedidoRepository;

@Service
public class ProductosPedidoService {

	@Autowired
	private ProductosPedidoRepository productosPedidoRepository;

	public void addProductosPedido(Pedido pedido, Set<ProductosCarrito> productosCarrito) {
		for (ProductosCarrito productosCarro : productosCarrito) {
			productosPedidoRepository.save(new ProductosPedido(pedido, productosCarro.getProducto(),
					productosCarro.getCantidad(), productosCarro.getProducto().getPrecio()));
		}
	}

	public List<ProductosPedido> findProductoPedidoByOtAndProducto(String producto, String ordenTrabajo) {
		return productosPedidoRepository.getProductoPedidoByProductoIDAndOtID(producto, ordenTrabajo);
	}

	public void decrementarCantidadPorRecoger(ProductosPedido productosPedido) {
		productosPedido.setCantidadPorRecoger(productosPedido.getCantidadPorRecoger() - 1);
		productosPedido.setIncidencia("");
		productosPedido.setTieneIncidencia(false);
		productosPedidoRepository.save(productosPedido);
	}

	public void addIncidencia(ProductosPedido productosPedido, String incidencia) {
		productosPedido.setIncidencia(incidencia);
		productosPedido.setTieneIncidencia(true);
		productosPedidoRepository.save(productosPedido);
	}

	public ProductosPedido findByProductoId(String id) {
		return productosPedidoRepository.findByProductoId(id);
	}

	public List<ProductosPedido> findProductoPedidoByOtAndProductoAndNoEmpaquetado(String idProducto, String otID) {
		return productosPedidoRepository.getProductoPedidoByProductoIDAndOtIDAndNoEmpaquetado(idProducto,otID);
	}

}
