package com.uniovi.services;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
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

	public List<ProductosCarrito> findAllByUser(User usuarioCarrito) {
		List<ProductosCarrito> carrito = productosCarritoRepository.findProductsByUser(usuarioCarrito);
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
