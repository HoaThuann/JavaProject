package com.web.clothes.ClothesWeb.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.web.clothes.ClothesWeb.repository.OrderDetailsRepository;

@Service
public class OrderDetailsServiceImpl implements OrderDetailsService{
	@Autowired
	private OrderDetailsRepository orderDetailsRepository;
}
