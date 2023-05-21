package com.web.clothes.ClothesWeb.service;

import java.util.List;
import java.util.Optional;

import org.springframework.web.multipart.MultipartFile;

import com.web.clothes.ClothesWeb.dto.responseDto.ImageResponseDto;
import com.web.clothes.ClothesWeb.entity.Image;
import com.web.clothes.ClothesWeb.entity.Product;

public interface ImageService {
	public void save(Image image);
	public void uploadFile(MultipartFile[] files,int defaultFileIndex,Product product);
	public List<ImageResponseDto> getImageByProduct(Product product);
	public void deleteByProduct(Product product);
}
