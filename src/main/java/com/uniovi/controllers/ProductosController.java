package com.uniovi.controllers;

import java.security.Principal;
import java.util.LinkedList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.uniovi.entities.Producto;
import com.uniovi.services.ProductosService;

@Controller
public class ProductosController {

	@Autowired
	private ProductosService productosService;

	@RequestMapping("/productos")
	public String getListado(Model model, @RequestParam(value = "", required = false) String searchText,
			Pageable pageable, Principal principal) {

		Page<Producto> productos = new PageImpl<Producto>(new LinkedList<Producto>());

		if (searchText != null && !searchText.isEmpty()) {
			productos = productosService.searchProductosByNameAndDescription(pageable, searchText);
		} else {
			productos = productosService.findAll(pageable);
		}
		model.addAttribute("productosList", productos.getContent());
		model.addAttribute("page", productos);
		return "productos/listProductos";
	}

}
