package com.uniovi.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uniovi.entities.OrdenTrabajo;
import com.uniovi.entities.Producto;
import com.uniovi.repositories.OrdenTrabajoRepository;

@Service
public class OrdenTrabajoService {

	@Autowired
	private OrdenTrabajoRepository ordenTrabajoRepository;
	
	public List<Producto> findProductosOrdenTrabajo(OrdenTrabajo ordenTrabajo){
		return ordenTrabajoRepository.findProductosOrderByUbicacion(ordenTrabajo);
	}
}
