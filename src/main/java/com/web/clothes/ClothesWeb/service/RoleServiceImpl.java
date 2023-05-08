package com.web.clothes.ClothesWeb.service;

import java.util.Optional;

import javax.swing.text.html.Option;

import org.springframework.stereotype.Service;

import com.web.clothes.ClothesWeb.entity.Role;
import com.web.clothes.ClothesWeb.repository.RoleRespository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService{
	private final RoleRespository roleRespository;
	
	@Override
    public Optional<Role> getRoleByName(String roleName) {
        Optional<Role> role = roleRespository.findRoleByRoleName(roleName);
        return role;
    }

}
