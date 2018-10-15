package com.uniovi.services;

import java.util.LinkedList;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.uniovi.entities.ProductosCarrito;
import com.uniovi.entities.User;
import com.uniovi.repositories.ProductosCarritoRepository;

@Service
public class ProductosCarritoService {

	@Autowired
	private ProductosCarritoRepository productosCarritoRepository;

	public void addProductoCarrito(ProductosCarrito productosCarrito) {
		productosCarritoRepository.save(productosCarrito);
	}

	public Page<ProductosCarrito> searchProductosByNameAndDescription(Pageable pageable, String searchText,
			User usuarioCarrito) {
		Page<ProductosCarrito> carrito = new PageImpl<ProductosCarrito>(new LinkedList<ProductosCarrito>());
		searchText = "%" + searchText + "%";
		carrito = productosCarritoRepository.searchProductosByNameAndDescription(pageable, searchText, usuarioCarrito);
		return carrito;
	}

	public Page<ProductosCarrito> findAllByUser(Pageable pageable, User usuarioCarrito) {
		Page<ProductosCarrito> carrito = new PageImpl<ProductosCarrito>(new LinkedList<ProductosCarrito>());
		carrito = productosCarritoRepository.findProductsByUser(pageable, usuarioCarrito);
		return carrito;
	}

	public void deleteProduct(ProductosCarrito productoCarrito) {
		productosCarritoRepository.delete(productoCarrito);
	}

	public void aumentarUnidad(ProductosCarrito productoCarrito) {
		ProductosCarrito productoCarritoPersist = productosCarritoRepository.findProductoCarrito(productoCarrito);
		productoCarritoPersist.setCantidad(productoCarritoPersist.getCantidad() + 1);
		productoCarritoPersist.calcularPrecioProductoCantidad();
		productosCarritoRepository.save(productoCarritoPersist);
	}

	public void decrementarUnidad(ProductosCarrito productoCarrito) {
		ProductosCarrito productoCarritoPersist = productosCarritoRepository.findProductoCarrito(productoCarrito);
		if (productoCarritoPersist.getCantidad() <= 1) {
			productosCarritoRepository.delete(productoCarrito);
		} else {
			productoCarritoPersist.setCantidad(productoCarritoPersist.getCantidad() - 1);
			productoCarritoPersist.calcularPrecioProductoCantidad();
			productosCarritoRepository.save(productoCarritoPersist);
		}
	}

	public void deleteCarrito(User user) {
		productosCarritoRepository.deleteCarritoByUser(user);
	}

	public double sumPrecioProductosCarrito(Set<ProductosCarrito> carrito) {
		double total = 0;
		for (ProductosCarrito productosCarrito : carrito) {
			total += productosCarrito.getPrecioProductoCantidad();
		}
		return total;
	}
}
