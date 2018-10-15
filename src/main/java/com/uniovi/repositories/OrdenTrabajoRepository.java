package com.uniovi.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.uniovi.entities.OrdenTrabajo;
import com.uniovi.entities.Pedido;

public interface OrdenTrabajoRepository  extends CrudRepository<OrdenTrabajo, Long>{

	
	@Query("select ot.pedido from OrdenTrabajo ot")
	List<Pedido> findPedidosAsignados();

}
