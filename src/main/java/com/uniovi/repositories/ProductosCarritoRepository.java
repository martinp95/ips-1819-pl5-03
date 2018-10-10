package com.uniovi.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.uniovi.entities.Producto;
import com.uniovi.entities.ProductosCarrito;
import com.uniovi.entities.User;

public interface ProductosCarritoRepository extends CrudRepository<ProductosCarrito, Long> {
	
	@Query("SELECT p FROM ProductosCarrito p WHERE (LOWER(p.producto.name) LIKE LOWER(?1) "
			+ "OR LOWER(p.producto.description) LIKE LOWER(?1) and p.user = ?2)")
	Page<ProductosCarrito> searchProductosByNameAndDescription(Pageable pageable, String searchText,
			User usuarioCarrito);

	@Query("SELECT p FROM ProductosCarrito p where p.user = ?1")
	Page<ProductosCarrito> findAll(Pageable pageable);

	@Query("SELECT p FROM ProductosCarrito p WHERE p.user = ?1)")
	Page<ProductosCarrito> findProductsByUser(Pageable pageable, User usuarioCarrito);

//	@Query("UPDATE ProductosCarrito p SET p.cantidad = "
//			+ "(SELECT p.cantidad FROM ProductosCarrito where p.id = ?1) + 1 " + "WHERE p.id = ?1")
//	void aumentarUnidad(Long id);
//
//	@Query("UPDATE ProductosCarrito p SET p.cantidad = "
//			+ "(SELECT p.cantidad FROM ProductosCarrito where p.id = ?1) - 1 " + "WHERE p.id = ?1")
//	void decrementarUnidad(Long id);

	@Query("SELECT p FROM ProductosCarrito p where p.producto = ?1 and p.user = ?2")
	ProductosCarrito findByIdProductoIdUser(Long idProducto,Long IdUser);	
	
	
}
