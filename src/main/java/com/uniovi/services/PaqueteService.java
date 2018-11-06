package com.uniovi.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uniovi.entities.OrdenTrabajo;
import com.uniovi.entities.Paquete;
import com.uniovi.entities.ProductosPedido;
import com.uniovi.repositories.PaqueteRepository;
import com.uniovi.repositories.ProductosPedidoRepository;

@Service
public class PaqueteService {
	
	@Autowired
	PaqueteRepository paqueteRepository;
	
	@Autowired
	ProductosPedidoRepository pdRepository;;

	//Conocer la ot del producto pedido para encontrar el paquete correspondiente al producto
	public void empaquetarProducto(ProductosPedido producto) {
		OrdenTrabajo ot = pdRepository.findOtByProductoPedido(producto);
		Paquete paquete = paqueteRepository.findByOt(ot);
		paquete.addProducto(producto);
		paqueteRepository.save(paquete);
	}

	public void crearPaqueteDeOT(OrdenTrabajo ordenTrabajo) {
		Paquete paquete = paqueteRepository.findByOt(ordenTrabajo);
		if(paquete == null) {
			paquete = new Paquete();
			paquete.setOt(ordenTrabajo);
		}
		paqueteRepository.save(paquete);
	}



}
