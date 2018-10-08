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
import com.uniovi.entities.ProductosCarrito;
import com.uniovi.entities.User;
import com.uniovi.services.ProductosCarritoService;
import com.uniovi.services.ProductosService;
import com.uniovi.services.UsersService;

@Controller
public class CarritoCompraController {

	@Autowired
	private UsersService usersService;
	@Autowired
	private ProductosService productosService;
	@Autowired
	private ProductosCarritoService productosCarritoService;

	@RequestMapping(value = "/carrito/add/{id}/{cantidad}")
	public String addProductoCarrito(Model model, Pageable pageable, @PathVariable Long id,@PathVariable int cantidad, Principal principal) {
		String email = principal.getName();
		User user = usersService.getUserByEmail(email);
		Producto producto = productosService.getProducto(id);

		ProductosCarrito productosCarrito = new ProductosCarrito(user, producto, cantidad);
		
		if (user.getProductosCarrito().contains(productosCarrito)) {
			model.addAttribute("error", user.getId());
		} else {
			productosCarritoService.addProductoCarrito(productosCarrito);
		}

		Page<Producto> productos = productosService.findAll(pageable);
		model.addAttribute("productosList", productos.getContent());
		model.addAttribute("page", productos);
		return "productos/listProductos :: tableListProductos";
	}

}