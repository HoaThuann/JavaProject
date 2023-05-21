package com.web.clothes.ClothesWeb.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.apache.commons.io.FilenameUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.web.clothes.ClothesWeb.dto.responseDto.ImageResponseDto;
import com.web.clothes.ClothesWeb.dto.responseDto.ProductResponseDto;
import com.web.clothes.ClothesWeb.entity.Image;
import com.web.clothes.ClothesWeb.entity.Product;
import com.web.clothes.ClothesWeb.repository.ImageRepository;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService{
	
	private final ImageRepository imageRepository;
	private static String UPLOADED_FOLDER = System.getProperty("user.dir") + "//src//main//resources//static//upload//";
//	private static String UPLOADED_FOLDER =Paths.get("src/main/resources/static/upload").toAbsolutePath().toString();
	@Override
	public void save(Image image) {
		
		imageRepository.save(image);
	}
	@Override
	public void uploadFile(MultipartFile[] files,int defaultFileIndex,Product product) {
		for (int i = 0; i < files.length; i++) {
			MultipartFile file = files[i];
			String nameForShow = file.getOriginalFilename();
			if (file.isEmpty()) {
				System.out.println(1);
				continue;
			}
			try {
				// Lưu ảnh vào thư mục tạm thời
				byte[] bytes = file.getBytes();
				System.out.println(2);
				String randomFileName = UUID.randomUUID().toString() + "."
						+ FilenameUtils.getExtension(file.getOriginalFilename());
				Path dir = Paths.get(UPLOADED_FOLDER);
				Path path = Paths.get(UPLOADED_FOLDER + randomFileName);
				if (!Files.exists(dir)) {
					System.out.println(3);
					Files.createDirectories(dir);
				}
				Files.write(path, bytes);
				System.out.println(4);
				// Tạo đối tượng Image và lưu xuống cơ sở dữ liệu
				System.out.println(5);
				Image image = new Image();
				image.setInmageForShow(nameForShow);
				image.setInmageForSave(randomFileName);
				if (i == defaultFileIndex) {
					image.setIsDefault(true);
				}
				image.setProduct(product);

				System.out.println(6);
				save(image);
				System.out.println(7);
			} catch (IOException e) {
				e.printStackTrace();
				
			}
		}
		
	}
	@Override
	public List<ImageResponseDto> getImageByProduct(Product product) {
		List<Image> images = imageRepository.getImageByProduct(product);
		List<ImageResponseDto> imageResponseDtos = images.stream()
				.map(image -> new ImageResponseDto(image.getId(),
						image.getInmageForShow(),image.getInmageForSave(),image.getIsDefault()))
				.collect(Collectors.toList());
		return imageResponseDtos;
	}
	@Override
	public void deleteByProduct(Product product) {
		List<Image> images = imageRepository.getImageByProduct(product);
		for (Image image : images) {
			imageRepository.delete(image);
		}
	}
	
}
