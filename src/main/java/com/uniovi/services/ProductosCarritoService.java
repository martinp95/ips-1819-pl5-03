package com.uniovi.services;

import java.util.LinkedList;

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
	
	public Page<ProductosCarrito> searchProductosByNameAndDescription(Pageable pageable, String searchText,User usuarioCarrito) {
		Page<ProductosCarrito> carrito = new PageImpl<ProductosCarrito>(new LinkedList<ProductosCarrito>());
		searchText = "%" + searchText + "%";
		carrito = productosCarritoRepository.searchProductosByNameAndDescription(pageable, searchText,usuarioCarrito);
		return carrito;
	}

	public Page<ProductosCarrito> findAll(Pageable pageable) {
		return productosCarritoRepository.findAll(pageable);
	}

	public void addProducto(ProductosCarrito producto) {
		productosCarritoRepository.save(producto);
	}

	public Page<ProductosCarrito> findAllByUser(Pageable pageable, User usuarioCarrito) {
		Page<ProductosCarrito> carrito = new PageImpl<ProductosCarrito>(new LinkedList<ProductosCarrito>());		
		carrito = productosCarritoRepository.findProductsByUser(pageable, usuarioCarrito);
		return carrito;
	}
	
	public void addProductosCarrito(ProductosCarrito producto) {
		productosCarritoRepository.save(producto);
	}

	public void deleteProduct(User userSesion, Long idProducto) {
		ProductosCarrito pc = productosCarritoRepository.findByIdProductoIdUser(idProducto, userSesion.getId());
		productosCarritoRepository.delete(pc);
		
	}

	public void aumentarUnidad(User userSesion, Long idProducto) {
		ProductosCarrito pc = productosCarritoRepository.findByIdProductoIdUser(idProducto, userSesion.getId());
		pc.setCantidad(pc.getCantidad()+1);
		productosCarritoRepository.save(pc);
		
	}

	public void decrementarUnidad(User userSesion, Long idProducto) {
		ProductosCarrito pc = productosCarritoRepository.findByIdProductoIdUser(idProducto, userSesion.getId());
		pc.setCantidad(pc.getCantidad()-1);
		productosCarritoRepository.save(pc);		
	}
	
	
	

}
