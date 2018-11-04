package com.uniovi.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uniovi.entities.Paquete;
import com.uniovi.entities.ProductosPedido;
import com.uniovi.repositories.PaqueteRepository;

@Service
public class PaqueteService {
	
	@Autowired
	PaqueteRepository paqueteRepository;

	//Mirar si hay un paquete para una ot dada, si lo hay añade producto a paquete
	// sino crea paquete y añade producto
	public void empaquetarProducto(ProductosPedido producto) {
		Paquete paquete = paqueteRepository.findPaqueteByOt(producto);
		if(paquete == null) {
			paquete = new Paquete();
			
		}
		paquete.addProducto(producto);
		paqueteRepository.save(paquete);
	}

}
