package com.uniovi.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.uniovi.entities.Pedido;


public interface PedidosRepository extends CrudRepository<Pedido, Long>{

	@Query("SELECT p FROM Pedido p where p.asignado = false order by p.fecha")	
	List<Pedido> findNoAsignadosOrderByFecha();

	Pedido findById(Long idPedido);
	

}
