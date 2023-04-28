package com.web.clothes.ClothesWeb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.web.clothes.ClothesWeb.entity.AttributeValue;

@Repository
public interface AttributeValueRepository extends JpaRepository<AttributeValue,Integer>{

}
