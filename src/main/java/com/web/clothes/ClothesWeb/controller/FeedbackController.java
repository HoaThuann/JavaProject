package com.web.clothes.ClothesWeb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.web.clothes.ClothesWeb.service.FeedbackService;

@Controller
public class FeedbackController {
	@Autowired
	private FeedbackService feedbackService;
	
	@RequestMapping("feedback")
	public String feedback() {
		return"admin/feedback";
	}
	
	@RequestMapping("feedbackDetail")
	public String feedbackView() {
		return"admin/feedbackDetail";
	}
}
