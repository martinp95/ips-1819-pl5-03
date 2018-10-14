package com.uniovi.repositories;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.uniovi.entities.ProductosCarrito;
import com.uniovi.entities.User;

public interface ProductosCarritoRepository extends CrudRepository<ProductosCarrito, Long> {

	@Query("SELECT p FROM ProductosCarrito p WHERE (LOWER(p.producto.name) LIKE LOWER(?1) "
			+ "OR LOWER(p.producto.description) LIKE LOWER(?1) and p.user = ?2)")
	Page<ProductosCarrito> searchProductosByNameAndDescription(Pageable pageable, String searchText,
			User usuarioCarrito);

	@Query("SELECT p FROM ProductosCarrito p WHERE p.user = ?1)")
	Page<ProductosCarrito> findProductsByUser(Pageable pageable, User usuarioCarrito);

	@Query("SELECT p FROM ProductosCarrito p where p = ?1")
	ProductosCarrito findProductoCarrito(ProductosCarrito productoCarrito);

	@Transactional
	@Modifying
	@Query("DELETE FROM ProductosCarrito p WHERE p.user = ?1")
	void deleteCarritoByUser(User user);
}
