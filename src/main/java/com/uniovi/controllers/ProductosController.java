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
			@RequestParam(value = "", required = false) String category,
			Principal principal) {
		String email = principal.getName();
		User user = usersService.getUserByEmail(email);

		if (user.getRole().contains("ROLE_ALMACENERO")) {
			return "redirect:/home";
		} else {

			List<Producto> productos;
			
			List<Producto> categorias;
			categorias = productosService.findCategories();

			if (searchText != null && !searchText.isEmpty()) {
				productos = productosService.searchProductosByNameAndDescription(searchText);
			}
			else if (category !=null){
				//ocultar lista categorias
				//document.getElementById('categorias').hidden = true;
				//desocultar lista productos
				//document.getElementById('productos').hidden = false;
				productos = productosService.findByCategory(category);
			}
			else {
				//desocultar lista categorias
				//document.getElementById('categorias').hidden = false;
				//ocultar lista productos
				//document.getElementById('productos').hidden = true;
				productos = productosService.findAll();
			}

			//CARRITO
			List<ProductosCarrito> carrito;

			carrito = productosCarritoService.findAllByUser(user);
			double precioTotal = 0.0;
			double precioTotalConIva = 0.0;
			for (ProductosCarrito productosCarrito : carrito) {
				precioTotal += productosCarrito.getPrecioProductoCantidad();
				precioTotalConIva += (productosCarrito.getPrecioProductoCantidad() 
						* productosCarrito.getProducto().getIva().getPorcentaje())
						+productosCarrito.getPrecioProductoCantidad();
			}
			model.addAttribute("carritoList", carrito);
			model.addAttribute("productosList", productos);
			model.addAttribute("total", precioTotal);
			model.addAttribute("totalIva", precioTotalConIva);

			model.addAttribute("categoriasList", categorias);
			
			return "productos/listProductos";
		}
	}
}