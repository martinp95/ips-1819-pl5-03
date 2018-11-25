package com.uniovi.controllers;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
	public String addProductoCarrito(Model model, @PathVariable Long id, @PathVariable int cantidad,
			Principal principal) {
		String email = principal.getName();
		User user = usersService.getUserByEmail(email);
		Producto producto = productosService.findById(id);
		ProductosCarrito productosCarrito = new ProductosCarrito(user, producto, cantidad);

		if (user.getProductosCarrito().contains(productosCarrito)) {
			model.addAttribute("error", producto.getId());
		} else if (producto.getStock() < cantidad) {
			model.addAttribute("errorStock", producto.getId());
		} else {
			productosCarritoService.addProductoCarrito(productosCarrito);
		}
		List<Producto> productos = productosService.findAll();
		model.addAttribute("productosList", productos);
		return "productos/listProductos :: tableListProductos";
	}

	@RequestMapping("/carrito/delete/{id}")
	public String delete(Model model, @PathVariable Long id, Principal principal) {
		String email = principal.getName();
		User user = usersService.getUserByEmail(email);
		Producto producto = productosService.findById(id);
		ProductosCarrito productoCarrito = new ProductosCarrito(user, producto, 0);
		productosCarritoService.deleteProduct(productoCarrito);
		List<ProductosCarrito> carrito = productosCarritoService.findAllByUser(user);
		model.addAttribute("carritoList", carrito);
		return "carrito/listCarrito :: tableCarrito";
	}

	@RequestMapping("/carrito/aumentarUnidad/{id}")
	public String aumentarUnidad(Model model, @PathVariable Long id, Principal principal) {
		String email = principal.getName();
		User user = usersService.getUserByEmail(email);
		Producto producto = productosService.findById(id);
		ProductosCarrito productoCarrito = new ProductosCarrito(user, producto, 0);
		productosCarritoService.aumentarUnidad(productoCarrito);
		List<ProductosCarrito> carrito = productosCarritoService.findAllByUser(user);
		model.addAttribute("carritoList", carrito);
		return "carrito/listCarrito :: tableCarrito";
	}

	@RequestMapping("/carrito/decrementarUnidad/{id}")
	public String decrementarUnidad(Model model, @PathVariable Long id, Principal principal) {
		String email = principal.getName();
		User user = usersService.getUserByEmail(email);
		Producto producto = productosService.findById(id);
		ProductosCarrito productoCarrito = new ProductosCarrito(user, producto, 0);
		productosCarritoService.decrementarUnidad(productoCarrito);
		List<ProductosCarrito> carrito = productosCarritoService.findAllByUser(user);
		model.addAttribute("carritoList", carrito);
		return "carrito/listCarrito :: tableCarrito";
	}
}