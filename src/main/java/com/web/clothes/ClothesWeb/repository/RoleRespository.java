package com.web.clothes.ClothesWeb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.web.clothes.ClothesWeb.entity.Role;

@Repository
public interface RoleRespository extends JpaRepository<Role,Integer>{
	 public Role findRoleByRoleName(String roleName);
}
