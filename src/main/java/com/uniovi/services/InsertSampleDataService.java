package com.uniovi.services;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uniovi.entities.Producto;
import com.uniovi.entities.User;

@Service
public class InsertSampleDataService {
	@Autowired
	private UsersService usersService;

	@Autowired
	private RoleService roleService;

	@Autowired
	private ProductosService productoService;

	@PostConstruct
	public void init() {
		User user1 = new User("email1@example.com", "Pedro");
		user1.setPassword("123456");
		user1.setRole(roleService.getRoles()[0]);
		user1.setRole(roleService.getRoles()[4]);
		User user2 = new User("email2@example.com", "Lucas");
		user2.setPassword("123456");
		user2.setRole(roleService.getRoles()[0]);
		user2.setRole(roleService.getRoles()[4]);
		User user3 = new User("email3@example.com", "María");
		user3.setPassword("123456");
		user3.setRole(roleService.getRoles()[0]);
		user3.setRole(roleService.getRoles()[4]);
		User user4 = new User("email4@example.com", "Marta");
		user4.setPassword("123456");
		user4.setRole(roleService.getRoles()[0]);
		user4.setRole(roleService.getRoles()[4]);
		User user5 = new User("email5@example.com", "Pelayo");
		user5.setPassword("123456");
		user5.setRole(roleService.getRoles()[0]);
		user5.setRole(roleService.getRoles()[4]);
		User user6 = new User("email6@example.com", "Edward");
		user6.setPassword("123456");
		user6.setRole(roleService.getRoles()[0]);
		user6.setRole(roleService.getRoles()[4]);
		User user7 = new User("email7@example.com", "Luis");
		user7.setPassword("123456");
		user7.setRole(roleService.getRoles()[0]);
		user7.setRole(roleService.getRoles()[4]);
		User user8 = new User("email8@example.com", "Juan");
		user8.setPassword("123456");
		user8.setRole(roleService.getRoles()[0]);
		user8.setRole(roleService.getRoles()[4]);
		User user9 = new User("email9@example.com", "Marcela");
		user9.setPassword("123456");
		user9.setRole(roleService.getRoles()[0]);
		user9.setRole(roleService.getRoles()[4]);
		User user10 = new User("email10@example.com", "Marina");
		user10.setPassword("123456");
		user10.setRole(roleService.getRoles()[0]);
		user10.setRole(roleService.getRoles()[4]);
		User user11 = new User("email11@example.com", "Marina");
		user11.setPassword("123456");
		user11.setRole(roleService.getRoles()[1]);
		User almacenero1 = new User("almacenero1@example.com", "Paco");
		almacenero1.setPassword("123456");
		almacenero1.setRole(roleService.getRoles()[2]);

		User empresa1 = new User("empresa1@example.com", "EDP");
		empresa1.setPassword("123456");
		empresa1.setRole(roleService.getRoles()[0]);
		empresa1.setRole(roleService.getRoles()[3]);

		usersService.addUser(user1);
		usersService.addUser(user2);
		usersService.addUser(user3);
		usersService.addUser(user4);
		usersService.addUser(user5);
		usersService.addUser(user6);

		usersService.addUser(user7);
		usersService.addUser(user8);
		usersService.addUser(user9);
		usersService.addUser(user10);
		usersService.addUser(user11);
		usersService.addUser(almacenero1);

		usersService.addUser(empresa1);

		Producto producto = new Producto("Teclado", "teclado retroiluminado", 10.2, 10, 2, "derecha", 3, 2, 5);
		Producto producto2 = new Producto("Teclado", "teclado mecanico", 10.2, 10, 2, "derecha", 2, 2, 5);
		Producto producto3 = new Producto("Ratón", "ratón retroiluminado", 10.2, 10, 2, "izquierda", 3, 2, 5);
		Producto producto4 = new Producto("Ratón", "ratón mecanico", 10.2, 10, 2, "izquierda", 3, 2, 5);
		Producto producto5 = new Producto("Procesador", "Procesador intell i7", 250, 10, 1, "izquierda", 1, 1, 5);
		Producto producto6 = new Producto("Procesador", "Procesador intell i5", 250, 10, 1, "derecha", 1, 1, 5);
		Producto producto7 = new Producto("Procesador", "Procesador intell i3", 250, 10, 1, "izquierda", 1, 2, 5);
		Producto producto8 = new Producto("Procesador", "Procesador intell pentium", 250, 10, 1, "derecha", 1, 2, 5);
		Producto producto9 = new Producto("Procesador", "Procesador intell xeon", 250, 10, 1, "izquierda", 1, 3, 5);

		Producto producto10 = new Producto("Pantalla", "Pantalla  xeon", 25, 10, 1, "izquierda", 1, 4, 5);
		Producto producto11 = new Producto("Pantalla", "Pantalla  xeon", 25, 10, 1, "izquierda", 1, 5, 5);
		Producto producto12 = new Producto("Pantalla", "Pantalla  xeon", 25, 10, 1, "izquierda", 1, 6, 5);
		Producto producto13 = new Producto("Pantalla", "Pantalla  xeon", 25, 10, 1, "izquierda", 1, 7, 5);
		Producto producto14 = new Producto("Pantalla", "Pantalla  xeon", 25, 10, 1, "izquierda", 1, 8, 5);
		Producto producto15 = new Producto("Pantalla", "Pantalla  xeon", 25, 10, 1, "izquierda", 1, 9, 5);
		Producto producto16 = new Producto("Pantalla", "Pantalla  xeon", 25, 10, 1, "izquierda", 1, 10, 5);
		Producto producto17 = new Producto("Pantalla", "Pantalla  xeon", 25, 10, 1, "izquierda", 1, 11, 5);

		productoService.addProducto(producto);
		productoService.addProducto(producto2);
		productoService.addProducto(producto3);
		productoService.addProducto(producto4);
		productoService.addProducto(producto5);
		productoService.addProducto(producto6);
		productoService.addProducto(producto7);
		productoService.addProducto(producto8);
		productoService.addProducto(producto9);
		productoService.addProducto(producto10);
		productoService.addProducto(producto11);
		productoService.addProducto(producto12);
		productoService.addProducto(producto13);
		productoService.addProducto(producto14);
		productoService.addProducto(producto15);
		productoService.addProducto(producto16);
		productoService.addProducto(producto17);
	}
}
