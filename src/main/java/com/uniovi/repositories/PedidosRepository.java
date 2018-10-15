package com.uniovi.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.uniovi.entities.Pedido;


public interface PedidosRepository extends CrudRepository<Pedido, Long>{

	


	Pedido findById(Long idPedido);

	
	@Query("Select p from Pedido p order by p.fecha")
	List<Pedido> findPedidosOrderByFecha();


	@Query("select p from Pedido p where p.ordenesTrabajo IS EMPTY order by p.fecha")
	List<Pedido> findPedidosNoAsignadosOrderByFecha();
	
	

}
