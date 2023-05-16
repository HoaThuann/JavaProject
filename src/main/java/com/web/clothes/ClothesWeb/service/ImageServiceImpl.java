package com.web.clothes.ClothesWeb.service;

import org.springframework.stereotype.Service;

import com.web.clothes.ClothesWeb.entity.Image;
import com.web.clothes.ClothesWeb.repository.ImageRepository;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService{
	
	private final ImageRepository imageRepository;

	@Override
	public void save(Image image) {
		imageRepository.save(image);
	}
	
}
