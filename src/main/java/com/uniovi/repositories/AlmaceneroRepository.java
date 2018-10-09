package com.uniovi.repositories;

import org.springframework.data.repository.CrudRepository;

import com.uniovi.entities.Almacenero;

public interface AlmaceneroRepository extends CrudRepository<Almacenero, Long>{

	Almacenero findByEmail(String email);

}
