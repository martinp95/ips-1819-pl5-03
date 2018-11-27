package com.uniovi.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.uniovi.entities.User;

public interface UsersRepository extends CrudRepository<User, Long> {
	
	User findByEmail(String email);
	
	@Query("select u from User u")
	List<User> findAll();	

//	@Query("select u from User u where 'ROLE_ALMACENERO' IN u.role")
//	List<User> findAllAlmacenero();	

}