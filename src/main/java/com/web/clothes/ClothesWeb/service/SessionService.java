package com.web.clothes.ClothesWeb.service;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SessionService {
	@Autowired
	HttpSession session;
	
	public void set(String name, Object value) {
		session.setAttribute(name, value);
	}
	
	public void remove(String name) {
		session.removeAttribute(name);
	}
	
	public<T> T get(String name) {
		return (T) session.getAttribute(name);
	}
}
