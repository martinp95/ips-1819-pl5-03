package com.uniovi.services;

import org.springframework.stereotype.Service;

@Service
public class RoleService {

    String[] roles = { "ROLE_USER", "ROLE_ADMIN" , "ROLE_ALMACENERO" };

    public String[] getRoles() {
	return roles;
    }

}
