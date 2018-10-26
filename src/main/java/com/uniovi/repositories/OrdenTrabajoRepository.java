package com.uniovi.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.uniovi.entities.OrdenTrabajo;
import com.uniovi.entities.User;

public interface OrdenTrabajoRepository  extends CrudRepository<OrdenTrabajo, Long>{

	List<OrdenTrabajo> findByAlmacenero(User almacenero);
}
