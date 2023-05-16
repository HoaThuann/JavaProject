package com.web.clothes.ClothesWeb.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.web.clothes.ClothesWeb.entity.Image;
import com.web.clothes.ClothesWeb.entity.OrderDetails;
@Repository
public interface ImageRepository extends JpaRepository<Image,Integer>{
	
}
