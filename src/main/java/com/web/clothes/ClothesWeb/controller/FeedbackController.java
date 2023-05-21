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

import com.web.clothes.ClothesWeb.dto.responseDto.FeedbackPageResponseDto;
import com.web.clothes.ClothesWeb.dto.responseDto.FeedbackResponseDto;
import com.web.clothes.ClothesWeb.entity.Feedback;
import com.web.clothes.ClothesWeb.service.FeedbackService;

@Controller
@RequestMapping(value = "/feedback")
public class FeedbackController {
	@Autowired
	private FeedbackService feedbackService;

	@RequestMapping("/view")
	public String feedbackView() {
		return "admin/feedback";
	}

	// get page category
	@GetMapping(value = "/getFeedbackPage")
	@ResponseBody
	public ResponseEntity<FeedbackPageResponseDto> getFeedbackPage(@RequestParam(defaultValue = "8") int size,
			@RequestParam(defaultValue = "0") int page) {

		Page<Feedback> feedbackPage = feedbackService.getAllFeedback(page, size);

		List<FeedbackResponseDto> feedbackResponseDtos = feedbackPage.stream()
				.map(feedback -> new FeedbackResponseDto(feedback.getEmail(), feedback.getFullName(),
						feedback.getPhone(), feedback.getSubjectName()))
				.collect(Collectors.toList());

		FeedbackPageResponseDto feedbackPageResponseDto = new FeedbackPageResponseDto(feedbackPage.getTotalPages(),
				feedbackPage.getNumber(), feedbackPage.getSize(), feedbackResponseDtos);

		return ResponseEntity.ok(feedbackPageResponseDto);

	}

}
