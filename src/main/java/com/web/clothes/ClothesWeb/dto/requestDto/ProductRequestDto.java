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
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;

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
	private Integer id;
	@NotBlank(message="Title cannot be empty and must not exceed 50 characters.")
	@Size(min = 1, max = 50,message="Title cannot be empty and must not exceed 50 characters.")
	private String title;
	
    @Min(value=0, message="Price cannot be empty and must be a positive number.")
	private float price;
    
    @Min(value=0, message="Curent price must be a positive number.")
	private float currentPrice;
    
    @NotBlank(message = "Please choose gender.")
	private String gender;
	
    @NotBlank(message="Discription cannot be empty and must not exceed 1500 characters")
	@Size(min = 1, max = 1500,message="Discription cannot be empty and must not exceed 1500 characters")
	private String description;
	
	private Integer sizeAttributeValue;

	private Integer colorAttributeValue;
	
	@NotNull(message="Category cannot be empty.")
	private Integer category;
	
}
