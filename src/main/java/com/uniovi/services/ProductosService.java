package com.uniovi.services;

import java.util.LinkedList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.uniovi.entities.Producto;
import com.uniovi.repositories.ProductosRepository;

@Service
public class ProductosService {

	@Autowired
	private ProductosRepository productosRepository;

	public Page<Producto> searchProductosByNameAndDescription(Pageable pageable, String searchText) {
		Page<Producto> productos = new PageImpl<Producto>(new LinkedList<Producto>());
		searchText = "%" + searchText + "%";
		productos = productosRepository.searchByNameAndDescription(pageable, searchText);
		return productos;
	}

	public Page<Producto> findAll(Pageable pageable) {
		return productosRepository.findAll(pageable);
	}

	public void addProducto(Producto producto) {
		productosRepository.save(producto);
	}

}