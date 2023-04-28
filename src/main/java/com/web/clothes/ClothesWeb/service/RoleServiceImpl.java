package com.web.clothes.ClothesWeb.service;

import org.springframework.stereotype.Service;

import com.web.clothes.ClothesWeb.entity.Role;
import com.web.clothes.ClothesWeb.repository.RoleRespository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService{
	private final RoleRespository roleRespository;
	
	@Override
    public Role getRoleByName(String roleName) {
        Role role = roleRespository.findRoleByRoleName(roleName);
        return role;
    }

}
