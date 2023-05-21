package com.web.clothes.ClothesWeb.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.web.clothes.ClothesWeb.entity.Feedback;
import com.web.clothes.ClothesWeb.repository.FeedbackRepository;

@Service
public class FeedbackServiceImpl implements FeedbackService {
	@Autowired
	private FeedbackRepository feedbackRepository;

	@Override
	public Page<Feedback> getAllFeedback(int pageNumber, int szie) {
		PageRequest feedbackPageable = PageRequest.of(pageNumber, szie, Sort.by(Sort.Direction.ASC, "fullName"));
		
		 Page<Feedback> feedbackPage = feedbackRepository.findFeedbackPage(feedbackPageable);
		 
		return feedbackPage;
	}
}
