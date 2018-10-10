package com.uniovi.repositories;

import org.springframework.data.repository.CrudRepository;

import com.uniovi.entities.OrdenTrabajo;
import com.uniovi.entities.User;

public interface UsersRepository extends CrudRepository<User, Long> {
	
	User findByEmail(String email);

	//void addOrdenTrabajo(User almacenero, OrdenTrabajo ordenTrabajo);

}