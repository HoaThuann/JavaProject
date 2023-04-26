package com.web.clothes.ClothesWeb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class c {
	@GetMapping(value="/")
	public String save() {
		return "hello";
	}
}
