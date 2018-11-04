package com.uniovi.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.uniovi.entities.Pedido;

public interface PedidosRepository extends CrudRepository<Pedido, Long> {

	Pedido findById(Long idPedido);

	@Query("select p from Pedido p where p.pedidoOrdenesTrabajo IS EMPTY and p.pagado = True order by p.fecha")
	List<Pedido> findPedidosNoAsignadosOrderByFecha();

	
	@Query("select p from Pedido p where p.pagado = False order by p.fecha")
	List<Pedido> findPedidosNoPagadosOrderByFecha();
}