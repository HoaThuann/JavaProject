package com.web.clothes.ClothesWeb.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.web.clothes.ClothesWeb.dto.responseDto.CategoryPageResponseDto;
import com.web.clothes.ClothesWeb.dto.responseDto.CategoryResponseDto;
import com.web.clothes.ClothesWeb.dto.responseDto.FeedbackPageResponseDto;
import com.web.clothes.ClothesWeb.dto.responseDto.FeedbackResponseDto;
import com.web.clothes.ClothesWeb.entity.Category;
import com.web.clothes.ClothesWeb.entity.Feedback;
import com.web.clothes.ClothesWeb.service.FeedbackService;

@Controller
@RequestMapping(value = "/feedback")
public class FeedbackController {
	@Autowired
	private FeedbackService feedbackService;
	
	@RequestMapping("feedback")
	public String feedbackView() {
		return"admin/feedback";
	}
	
	//get page category
		@GetMapping(value = "/getCategoryPage")
		@ResponseBody
		public ResponseEntity<FeedbackPageResponseDto> getFeedbackPage(
				@RequestParam(defaultValue = "8") int size, @RequestParam(defaultValue = "0") int page) {

			Page<Feedback> feedbackPage = feedbackService.getAllFeedback(page, size);
			
			List<FeedbackResponseDto> feedbackResponseDtos = feedbackPage.stream()
					.map(category -> new CategoryResponseDto(category.getId(),
							category.getCategoryName()))
					.collect(Collectors.toList());
			
			CategoryPageResponseDto categoryPageResponseDto = new CategoryPageResponseDto(
					categoryPage.getTotalPages(), categoryPage.getNumber(), categoryPage.getSize(),
					categoryResponseDtos);
			
			return ResponseEntity.ok(categoryPageResponseDto);

		}
	
}
