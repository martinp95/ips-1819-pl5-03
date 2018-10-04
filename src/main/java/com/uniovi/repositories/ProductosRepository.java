package com.uniovi.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.uniovi.entities.Producto;

public interface ProductosRepository extends CrudRepository<Producto, Long> {

	@Query("SELECT p FROM Producto p WHERE (LOWER(p.name) LIKE LOWER(?1) "
		    + "OR LOWER(p.description) LIKE LOWER(?1))")
	Page<Producto> searchByNameAndDescription(Pageable pageable, String searchText);

	@Query("SELECT p FROM Producto p")
	Page<Producto> findAll(Pageable pageable);

}
