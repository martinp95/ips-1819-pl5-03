package com.uniovi.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uniovi.entities.OrdenTrabajo;
import com.uniovi.entities.Producto;
import com.uniovi.entities.ProductosPedido;
import com.uniovi.repositories.ProductosRepository;

@Service
public class ProductosService {

	@Autowired
	private ProductosRepository productosRepository;

	public List<Producto> searchProductosByNameAndDescription(String searchText) {
		searchText = "%" + searchText + "%";
		List<Producto> productos  = productosRepository.searchByNameAndDescription(searchText);
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
}
