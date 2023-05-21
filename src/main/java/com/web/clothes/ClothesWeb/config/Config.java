package com.web.clothes.ClothesWeb.config;

import java.text.NumberFormat;
import java.util.Locale;

import javax.servlet.MultipartConfigElement;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.multipart.support.MultipartFilter;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;

@Configuration
public class Config {
	 @Bean
	 public MultipartConfigElement multipartConfigElement() {
	     return new MultipartConfigElement("");
	 }

	 @Bean
	 public MultipartResolver multipartResolver() {
	     org.springframework.web.multipart.commons.CommonsMultipartResolver multipartResolver = new org.springframework.web.multipart.commons.CommonsMultipartResolver();
	     multipartResolver.setMaxUploadSize(1000000);
	     return multipartResolver;
	 }
	 
	 @Bean
	 public NumberFormat moneyFormat() {
		 return NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
	 }
	 

	
	  
}
