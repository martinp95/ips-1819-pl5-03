package com.uniovi.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.uniovi.entities.PedidoAlmacen;
import com.uniovi.entities.Producto;

public interface PedidoAlmacenRepository extends CrudRepository<PedidoAlmacen, Long> {
	
	List<PedidoAlmacen> findAll();

	PedidoAlmacen findPedidoAlmacenByProducto(Producto producto);

}
