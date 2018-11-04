package com.uniovi.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.uniovi.entities.Paquete;
import com.uniovi.entities.ProductosPedido;

public interface PaqueteRepository  extends CrudRepository<Paquete, Long>{

	
	/*
	 * Seleccionar el paquete (null si lo fuera) de una orden de trabajo de un producto dado.
	 * Si ya hay paquete para esa ot devolvera ese paquete, sino devolvera null
	 */
//	@Query("Select pp.paquete from ProductosPedido pp "
//			+ "where pp.pedido.pedidoOrdenesTrabajo.ordenTrabajo = "
//			+ "(Select pp.pedido.pedidoOrdenesTrabajo.ordenTrabajo from ProductosPedido pp where pp = ?1)")
	
	//consulta para que pueda ejecutarse
	@Query("select pp.paquete from ProductosPedido pp where pp = ?1")
	Paquete findPaqueteByOt(ProductosPedido producto); 

}
