package com.web.clothes.ClothesWeb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.web.clothes.ClothesWeb.entity.Attribute;
@Repository
public interface AttributeRepository extends JpaRepository<Attribute,Integer>{

}
