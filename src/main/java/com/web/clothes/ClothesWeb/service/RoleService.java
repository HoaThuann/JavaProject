package com.web.clothes.ClothesWeb.service;

import java.util.Optional;

import com.web.clothes.ClothesWeb.entity.Role;

public interface RoleService {
	public Optional<Role> getRoleByName(String roleName);
}
