package com.web.clothes.ClothesWeb.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.web.clothes.ClothesWeb.entity.User;



@Repository
public interface UserRepository extends JpaRepository<User,Integer> {
	public Optional<User> findUserByUserName(String userName);
}
