package com.uniovi.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uniovi.entities.OrdenTrabajo;
import com.uniovi.entities.User;
import com.uniovi.repositories.OrdenTrabajoRepository;

@Service
public class AlmaceneroService {

	@Autowired
	private OrdenTrabajoRepository ordenTrabajoRepository;

	public List<OrdenTrabajo> findOrdenTrabajoByUser(User almacenero) {
		return ordenTrabajoRepository.findByAlmacenero(almacenero);
	}
}
