package com.web.clothes.ClothesWeb.service;

import org.springframework.data.domain.Page;

import com.web.clothes.ClothesWeb.entity.Feedback;

public interface FeedbackService {
	public Page<Feedback> getAllFeedback(int pageNumber, int szie);
}
