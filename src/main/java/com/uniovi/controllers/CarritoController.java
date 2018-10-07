package com.uniovi.controllers;

import java.security.Principal;
import java.util.LinkedList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.uniovi.entities.ProductoComprado;
import com.uniovi.entities.User;
import com.uniovi.services.CarritoService;
import com.uniovi.services.UsersService;

@Controller
public class CarritoController {

	@Autowired
	private CarritoService carritoService;

	@Autowired
	private UsersService userService;

	@RequestMapping("/carrito")
	public String getCarrito(Model model, @RequestParam(value = "", required = false) String searchText,
			Pageable pageable, Principal principal) {

		Page<ProductoComprado> carrito = new PageImpl<ProductoComprado>(new LinkedList<ProductoComprado>());

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String email = auth.getName();
		User usuarioSesion = userService.getUserByEmail(email); // user sesion

		if (searchText != null && !searchText.isEmpty()) {
			carrito = carritoService.searchProductosByNameAndDescription(pageable, searchText, usuarioSesion);
		} else {
			carrito = carritoService.findAllByUser(pageable, usuarioSesion);
		}
		model.addAttribute("carritoList", carrito.getContent());
		model.addAttribute("page", carrito);
		return "carrito/list";
	}

	@RequestMapping("/carrito/delete/{id}")
	public String delete(@PathVariable Long id) {
		carritoService.deleteProduct(id);
		return "redirect:/carrito/list";
	}
	
	@RequestMapping("/carrito/aumentarUnidad/{id}")
	public String aumentarUnidad(@PathVariable Long id) {
		carritoService.aumentarUnidad(id);
		return "redirect:/carrito/list";
	}
	
	@RequestMapping("/carrito/decrementarUnidadUnidad/{id}")
	public String decrementarUnidad(@PathVariable Long id) {
		carritoService.decrementarUnidad(id);
		return "redirect:/carrito/list";
	}

}
