package com.uniovi.services;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uniovi.entities.OrdenTrabajo;
import com.uniovi.entities.Producto;
import com.uniovi.entities.ProductosCarrito;
import com.uniovi.entities.ProductosPedido;
import com.uniovi.repositories.ProductosRepository;

@Service
public class ProductosService {

	@Autowired
	private ProductosRepository productosRepository;
	@Autowired
	private PedidoAlmacenService pedidoAlmacenService;

	public List<Producto> searchProductosByNameAndDescription(String searchText) {
		searchText = "%" + searchText + "%";
		List<Producto> productos = productosRepository.searchByNameAndDescription(searchText);
		return productos;
	}

	public List<Producto> findAll() {
		return productosRepository.findAll();
	}

	public void addProducto(Producto producto) {
		productosRepository.save(producto);
	}

	public Producto findById(Long id) {
		return productosRepository.findOne(id);
	}

	public List<Object> findProductosByOt(OrdenTrabajo ordenTrabajo) {
		return productosRepository.findProductosByOtOrderByPosicionAlmacen(ordenTrabajo);
	}

	public boolean isProductoInOT(String producto, String ordenTrabajo) {
		List<Producto> productos = productosRepository.getProductoByProductoIDAndOtID(producto, ordenTrabajo);
		return !productos.isEmpty();
	}

	public List<ProductosPedido> findProductosByOtNoIncidenciaNoEmpaquetado(OrdenTrabajo ordenTrabajo) {
		return productosRepository.findProductoByOtNoincidenciaNoEmpaquetado(ordenTrabajo);
	}

	public List<Object> findProductosByOtAndNoEmpaquetado(OrdenTrabajo ordenTrabajo) {
		return productosRepository.findProductosByOtAndNoEmpaquetado(ordenTrabajo);
	}

	public void descontarStock(Set<ProductosCarrito> productosCarrito) {
		Producto producto;
		for (ProductosCarrito productosCarro : productosCarrito) {
			producto = productosCarro.getProducto();
			producto.setStock(producto.getStock() - productosCarro.getCantidad());
			productosRepository.save(producto);
			if (producto.getStock() < producto.getStockMinimo()) {
				// Comprobar si ya ha y un pedido para ese producto y en caso de haberlo aumento
				// la cantidad, sino si creo el pedido.
				pedidoAlmacenService.crearPedido(producto, producto.getStockMaximo() - producto.getStock());
			}
		}
	}

	public void anadirStock(Producto producto, int cantidad) {
		producto.setStock(producto.getStock() + cantidad);
		productosRepository.save(producto);
	}

}
