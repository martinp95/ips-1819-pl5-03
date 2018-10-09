package com.uniovi.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.uniovi.entities.Almacenero;
import com.uniovi.entities.Pedido;
import com.uniovi.entities.ProductosCarrito;


public interface PedidosRepository extends CrudRepository<Pedido, Long>{

	@Query("SELECT p FROM Pedido p where p.asignado = false order by p.fecha")	
	List<Pedido> findNoAsignadosOrderByFecha();

	

}
