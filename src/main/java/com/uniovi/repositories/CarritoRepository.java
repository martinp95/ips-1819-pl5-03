package com.uniovi.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.uniovi.entities.Producto;
import com.uniovi.entities.ProductoComprado;
import com.uniovi.entities.User;

@Repository
public interface CarritoRepository extends CrudRepository<ProductoComprado, Long> {

	@Query("SELECT p FROM ProductoComprado p WHERE (LOWER(p.producto.name) LIKE LOWER(?1) "
		    + "OR LOWER(p.producto.description) LIKE LOWER(?1) and p.user = ?2)")
	Page<ProductoComprado> searchProductosByNameAndDescription(Pageable pageable, String searchText, User usuarioCarrito);

	@Query("SELECT p FROM ProductoComprado p where p.user = ?1")
	Page<ProductoComprado> findAll(Pageable pageable);	
	
	@Query("SELECT p FROM ProductoComprado p WHERE p.user = ?1)")
	Page<ProductoComprado> findProductsByUser(Pageable pageable, User usuarioCarrito);

	@Query("UPDATE ProductoComprado p SET p.unidades = "
			+ "(SELECT p.unidades FROM ProductoComprado where p.id = ?1) + 1 "
			+ "WHERE p.id = ?1")
	void aumentarUnidad(Long id);

	
	@Query("UPDATE ProductoComprado p SET p.unidades = "
			+ "(SELECT p.unidades FROM ProductoComprado where p.id = ?1) - 1 "
			+ "WHERE p.id = ?1")
	void decrementarUnidad(Long id);
}
