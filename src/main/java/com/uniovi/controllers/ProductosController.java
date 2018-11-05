package com.uniovi.controllers;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.uniovi.entities.Producto;
import com.uniovi.entities.ProductosCarrito;
import com.uniovi.entities.User;
import com.uniovi.services.ProductosCarritoService;
import com.uniovi.services.ProductosService;
import com.uniovi.services.UsersService;

@Controller
public class ProductosController {

	@Autowired
	private UsersService usersService;
	@Autowired
	private ProductosService productosService;
	@Autowired
	private ProductosCarritoService productosCarritoService;

	@RequestMapping("/productos")
	public String getListado(Model model, @RequestParam(value = "", required = false) String searchText,
			Principal principal) {

		List<Producto> productos;

		if (searchText != null && !searchText.isEmpty()) {
			productos = productosService.searchProductosByNameAndDescription(searchText);
		} else {
			productos = productosService.findAll();
		}
		List<ProductosCarrito> carrito;

		String email = principal.getName();
		User user = usersService.getUserByEmail(email);
		carrito = productosCarritoService.findAllByUser(user);
		double precioTotal = 0.0;
		for (ProductosCarrito productosCarrito : carrito) {
			precioTotal += productosCarrito.getPrecioProductoCantidad();
		}
		model.addAttribute("carritoList", carrito);
		model.addAttribute("productosList", productos);
		model.addAttribute("total", precioTotal);
		return "productos/listProductos";
	}
}