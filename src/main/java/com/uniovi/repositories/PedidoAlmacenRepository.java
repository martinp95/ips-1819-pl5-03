package com.uniovi.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.uniovi.entities.PedidoAlmacen;

public interface PedidoAlmacenRepository extends CrudRepository<PedidoAlmacen, Long> {
	
	List<PedidoAlmacen> findAll();

}
