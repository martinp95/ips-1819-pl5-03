package com.uniovi.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uniovi.entities.OrdenTrabajo;
import com.uniovi.repositories.OrdenTrabajoRepository;

@Service
public class OrdenTrabajoService {

	@Autowired
	private OrdenTrabajoRepository ordenTrabajoRepository;
	
//	public List<Producto> findProductosOrdenTrabajo(OrdenTrabajo ordenTrabajo){
//		return ordenTrabajoRepository.findProductosOrderByUbicacion(ordenTrabajo);
//	}
	
	public void addOrdenTrabajo(OrdenTrabajo ordenTrabjo) {
		ordenTrabajoRepository.save(ordenTrabjo);
	}

	public OrdenTrabajo findById(Long id) {
		return ordenTrabajoRepository.findOne(id);
	}
}
