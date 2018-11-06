package com.uniovi.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uniovi.entities.OrdenTrabajo;
import com.uniovi.entities.Paquete;
import com.uniovi.entities.ProductosPedido;
import com.uniovi.repositories.OrdenTrabajoRepository;
import com.uniovi.repositories.PaqueteRepository;

@Service
public class PaqueteService {

	@Autowired
	private PaqueteRepository paqueteRepository;
	@Autowired
	private OrdenTrabajoRepository ordenTrabajoRepository;

	// Conocer la ot del producto pedido para encontrar el paquete correspondiente
	// al producto
	public void empaquetarProducto(ProductosPedido productoPedido, Long otID) {
		OrdenTrabajo ot = ordenTrabajoRepository.findById(otID);
		Paquete paquete = paqueteRepository.findByOt(ot);
		paquete.addProducto(productoPedido);
		//no lo persiste
		paqueteRepository.save(paquete);
	}

	public void crearPaqueteDeOT(OrdenTrabajo ordenTrabajo) {
		Paquete paquete = paqueteRepository.findByOt(ordenTrabajo);
		if (paquete == null) {
			paquete = new Paquete();
			paquete.setOt(ordenTrabajo);
		}
		paqueteRepository.save(paquete);
	}
}