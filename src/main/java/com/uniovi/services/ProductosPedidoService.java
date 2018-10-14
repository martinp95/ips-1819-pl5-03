package com.uniovi.services;

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
			productosPedidoRepository.save(new ProductosPedido(pedido, productosCarro.getProducto()));
		}
	}

}