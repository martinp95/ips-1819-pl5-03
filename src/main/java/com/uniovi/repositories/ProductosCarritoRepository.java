package com.uniovi.repositories;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.uniovi.entities.ProductosCarrito;
import com.uniovi.entities.User;

public interface ProductosCarritoRepository extends CrudRepository<ProductosCarrito, Long> {

	@Query("SELECT p FROM ProductosCarrito p WHERE p.user = ?1)")
	List<ProductosCarrito> findProductsByUser(User usuarioCarrito);

	@Query("SELECT p FROM ProductosCarrito p where p = ?1")
	ProductosCarrito findProductoCarrito(ProductosCarrito productoCarrito);

	@Transactional
	@Modifying
	@Query("DELETE FROM ProductosCarrito p WHERE p.user = ?1")
	void deleteCarritoByUser(User user);
}
