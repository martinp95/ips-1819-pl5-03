package com.uniovi.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uniovi.entities.Iva;
import com.uniovi.repositories.IvaRepository;

@Service
public class IvaService {
	
	@Autowired
	private IvaRepository ivaRepository;
	
	public void addIva(Iva iva) {
		ivaRepository.save(iva);
	}

}
