package com.web.clothes.ClothesWeb.dto.responseDto;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.springframework.format.annotation.DateTimeFormat;

import com.web.clothes.ClothesWeb.entity.Category;
import com.web.clothes.ClothesWeb.entity.Image;
import com.web.clothes.ClothesWeb.entity.ProductAttributeValue;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class ProductResponseDto {

	private Integer id;

	private String title;
	
	private String price;

	private String currentPrice;

	private boolean gender;

	private String description;

	private LocalDate createAt ;

	private LocalDate updateAt;
	
	private String categoryName;
	
	private ProductAttributeValueResponseDto productAttributeValue;

	private List<ImageResponseDto> images;

	public ProductResponseDto(Integer id, String title, String price, String currentPrice, boolean gender,
			String description, LocalDate createAt, LocalDate updateAt, String categoryName,
			List<ImageResponseDto> images) {
		super();
		this.id = id;
		this.title = title;
		this.price = price;
		this.currentPrice = currentPrice;
		this.gender = gender;
		this.description = description;
		this.createAt = createAt;
		this.updateAt = updateAt;
		this.categoryName = categoryName;
		this.images = images;
	}
	
	
}
