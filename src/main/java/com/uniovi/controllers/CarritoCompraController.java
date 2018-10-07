package com.uniovi.controllers;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.uniovi.entities.Producto;
import com.uniovi.entities.User;
import com.uniovi.services.ProductosService;
import com.uniovi.services.UsersService;

@Controller
public class CarritoCompraController {

	@Autowired
	private UsersService usersService;
	@Autowired
	private ProductosService productosService;

	@RequestMapping(value = "/carrito/add/{id}")
	public String addProductoCarrito(Model model, Pageable pageable, @PathVariable Long id, Principal principal) {
		String email = principal.getName();
		User user = usersService.getUserByEmail(email);
		// Comprobar si el user tiene carrito, sino se crea, si lo tiene se añade el
		//if (user.getCarrito() == null) {
			
		//} else {

			// si ya tiene carrito y el producto ya lo tiene añadido le envio un error para
			// que lo modifique desde el carrito

			// sino lo añdo y lo salvo
		//}

		Page<Producto> productos = productosService.findAll(pageable);
		model.addAttribute("productosList", productos.getContent());
		model.addAttribute("page", productos);
		return "home";
	}

}