package com.uniovi.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.uniovi.entities.User;

public interface UsersRepository extends CrudRepository<User, Long> {
	
	User findByEmail(String email);

	@Query("select u from User u where u.role = 'ROLE_ALMACENERO'")
	List<User> findAllAlmacenero();	

}