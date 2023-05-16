package com.web.clothes.ClothesWeb.dto.requestDto;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import com.web.clothes.ClothesWeb.entity.AttributeValue;
import com.web.clothes.ClothesWeb.entity.Cart;
import com.web.clothes.ClothesWeb.entity.Category;
import com.web.clothes.ClothesWeb.entity.Image;
import com.web.clothes.ClothesWeb.entity.OrderDetails;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class ProductRequestDto {
	
	@NotBlank(message="Title cannot be empty and must not exceed 50 characters")
	@Size(min = 1, max = 50,message="Title cannot be empty and must not exceed 50 characters")
	private String title;
	
	@NotBlank(message="Price cannot be empty")
	private float price;
	
	private float currentPrice;
	
	@NotBlank(message="Gender cannot be empty")
	private boolean gender;
	
	@NotBlank(message="Discription cannot be empty and must not exceed 1500 characters")
	@Size(min = 1, max = 1500,message="Discription cannot be empty and must not exceed 1500 characters")
	private String description;
	
	private Integer sizeAttributeValue;

	private Integer colorAttributeValue;
	
	@NotBlank(message="Category cannot be empty")
	private Integer categoryId;
	
}
