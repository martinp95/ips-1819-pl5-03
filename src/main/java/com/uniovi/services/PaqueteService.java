package com.uniovi.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uniovi.entities.OrdenTrabajo;
import com.uniovi.entities.Paquete;
import com.uniovi.entities.ProductosPedido;
import com.uniovi.repositories.OrdenTrabajoRepository;
import com.uniovi.repositories.PaqueteRepository;
import com.uniovi.repositories.ProductosPedidoRepository;

@Service
public class PaqueteService {

	@Autowired
	private PaqueteRepository paqueteRepository;
	@Autowired
	private OrdenTrabajoRepository ordenTrabajoRepository;
	@Autowired
	private ProductosPedidoRepository productosPedidoRepository;

	// Conocer la ot del producto pedido para encontrar el paquete correspondiente
	// al producto
	public void empaquetarProducto(ProductosPedido productoPedido, Long otID) {
		OrdenTrabajo ot = ordenTrabajoRepository.findById(otID);
		Paquete paquete = paqueteRepository.findByOrdenTrabajo(ot);
		productoPedido.setPaquete(paquete);
		productosPedidoRepository.save(productoPedido);
	}

	public void crearPaqueteDeOT(OrdenTrabajo ordenTrabajo) {
		Paquete paquete = paqueteRepository.findByOrdenTrabajo(ordenTrabajo);
		if (paquete == null) {
			paquete = new Paquete();
			paquete.setOrdenTrabajo(ordenTrabajo);
			ordenTrabajo.setPaquete(paquete);
		}
		paqueteRepository.save(paquete);
		ordenTrabajoRepository.save(ordenTrabajo);
	}
}