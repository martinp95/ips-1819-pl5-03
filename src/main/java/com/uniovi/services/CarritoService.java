package com.uniovi.services;

import java.util.LinkedList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.uniovi.entities.ProductoComprado;
import com.uniovi.entities.User;
import com.uniovi.repositories.CarritoRepository;

@Service
public class CarritoService {
	
	@Autowired
	private CarritoRepository carritoRepository;
	
	public Page<ProductoComprado> searchProductosByNameAndDescription(Pageable pageable, String searchText,User usuarioCarrito) {
		Page<ProductoComprado> carrito = new PageImpl<ProductoComprado>(new LinkedList<ProductoComprado>());
		searchText = "%" + searchText + "%";
		carrito = carritoRepository.searchProductosByNameAndDescription(pageable, searchText,usuarioCarrito);
		return carrito;
	}

	public Page<ProductoComprado> findAll(Pageable pageable) {
		return carritoRepository.findAll(pageable);
	}

	public void addProducto(ProductoComprado producto) {
		carritoRepository.save(producto);
	}

	public Page<ProductoComprado> findAllByUser(Pageable pageable, User usuarioCarrito) {
		Page<ProductoComprado> carrito = new PageImpl<ProductoComprado>(new LinkedList<ProductoComprado>());		
		carrito = carritoRepository.findProductsByUser(pageable, usuarioCarrito);
		return carrito;
	}

	public void deleteProduct(Long id) {
		carritoRepository.delete(id);		
	}

	public void aumentarUnidad(Long id) {
		carritoRepository.aumentarUnidad(id);	
		
	}

	public void decrementarUnidad(Long id) {
		carritoRepository.decrementarUnidad(id);	
		
	}
	
	public void addProductoComprado(ProductoComprado producto) {
		carritoRepository.save(producto);
	}

}
