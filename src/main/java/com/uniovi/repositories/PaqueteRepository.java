package com.uniovi.repositories;

import org.springframework.data.repository.CrudRepository;

import com.uniovi.entities.OrdenTrabajo;
import com.uniovi.entities.Paquete;

public interface PaqueteRepository  extends CrudRepository<Paquete, Long>{

	
	Paquete findByOrdenTrabajo(OrdenTrabajo ordenTrabajo);
	

}
