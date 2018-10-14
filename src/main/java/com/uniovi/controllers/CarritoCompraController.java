package com.uniovi.controllers;

import java.security.Principal;
import java.util.LinkedList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
	public String addProductoCarrito(Model model, Pageable pageable, @PathVariable Long id, @PathVariable int cantidad,
			Principal principal) {
		String email = principal.getName();
		User user = usersService.getUserByEmail(email);
		Producto producto = productosService.getProducto(id);

		ProductosCarrito productosCarrito = new ProductosCarrito(user, producto, cantidad);

		if (user.getProductosCarrito().contains(productosCarrito)) {
			model.addAttribute("error", producto.getId());
		} else {
			productosCarritoService.addProductoCarrito(productosCarrito);
		}

		Page<Producto> productos = productosService.findAll(pageable);
		model.addAttribute("productosList", productos.getContent());
		model.addAttribute("page", productos);
		return "productos/listProductos :: tableListProductos";
	}

	@RequestMapping("/carrito")
	public String getCarrito(Model model, @RequestParam(value = "", required = false) String searchText,
			Pageable pageable, Principal principal) {

		Page<ProductosCarrito> carrito = new PageImpl<ProductosCarrito>(new LinkedList<ProductosCarrito>());

		String email = principal.getName();
		User user = usersService.getUserByEmail(email);

		if (searchText != null && !searchText.isEmpty()) {
			carrito = productosCarritoService.searchProductosByNameAndDescription(pageable, searchText, user);
		} else {
			carrito = productosCarritoService.findAllByUser(pageable, user);
		}
		model.addAttribute("carritoList", carrito.getContent());
		model.addAttribute("page", carrito);
		return "carrito/listCarrito";
	}

	@RequestMapping("/carrito/delete/{id}")
	public String delete(Model model, Pageable pageable, @PathVariable Long id, Principal principal) {
		String email = principal.getName();
		User user = usersService.getUserByEmail(email);
		Producto producto = productosService.getProducto(id);
		ProductosCarrito productoCarrito = new ProductosCarrito(user, producto, 0);
		productosCarritoService.deleteProduct(productoCarrito);

		Page<ProductosCarrito> carrito = productosCarritoService.findAllByUser(pageable, user);
		model.addAttribute("carritoList", carrito.getContent());
		model.addAttribute("page", carrito);

		return "carrito/listCarrito :: tableCarrito";
	}

	@RequestMapping("/carrito/aumentarUnidad/{id}")
	public String aumentarUnidad(Model model, Pageable pageable, @PathVariable Long id, Principal principal) {
		String email = principal.getName();
		User user = usersService.getUserByEmail(email);
		Producto producto = productosService.getProducto(id);
		ProductosCarrito productoCarrito = new ProductosCarrito(user, producto, 0);
		productosCarritoService.aumentarUnidad(productoCarrito);
		
		Page<ProductosCarrito> carrito = productosCarritoService.findAllByUser(pageable, user);
		model.addAttribute("carritoList", carrito.getContent());
		model.addAttribute("page", carrito);

		return "carrito/listCarrito :: tableCarrito";
	}

	@RequestMapping("/carrito/decrementarUnidad/{id}")
	public String decrementarUnidad(Model model, Pageable pageable, @PathVariable Long id, Principal principal) {
		String email = principal.getName();
		User user = usersService.getUserByEmail(email);
		Producto producto = productosService.getProducto(id);
		ProductosCarrito productoCarrito = new ProductosCarrito(user, producto, 0);
		productosCarritoService.decrementarUnidad(productoCarrito);

		Page<ProductosCarrito> carrito = productosCarritoService.findAllByUser(pageable, user);
		model.addAttribute("carritoList", carrito.getContent());
		model.addAttribute("page", carrito);

		return "carrito/listCarrito :: tableCarrito";
	}

}