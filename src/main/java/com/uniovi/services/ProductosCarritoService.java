package com.uniovi.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uniovi.entities.ProductosCarrito;
import com.uniovi.repositories.ProductosCarritoRepository;

@Service
public class ProductosCarritoService {
	
	@Autowired
	private ProductosCarritoRepository productosCarritoRepository;

	public void addProductoCarrito(ProductosCarrito productosCarrito) {
		productosCarritoRepository.save(productosCarrito);
	}
	
	

}
