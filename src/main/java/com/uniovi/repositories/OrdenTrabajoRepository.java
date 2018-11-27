package com.uniovi.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.uniovi.entities.OrdenTrabajo;
import com.uniovi.entities.User;

public interface OrdenTrabajoRepository extends CrudRepository<OrdenTrabajo, Long> {

	List<OrdenTrabajo> findByAlmacenero(User almacenero);

	OrdenTrabajo findById(Long otID);

	@Query(value = "SELECT * FROM ORDEN_TRABAJO where almacenero_id=?1 and empaquetada = true", nativeQuery = true)
	List<OrdenTrabajo> findByAlmaceneroAndEmpaquetada(User almacenero);

	@Query("Select o from OrdenTrabajo o order by o.fecha")
	List<OrdenTrabajo> findAllOrderByFecha();
}
