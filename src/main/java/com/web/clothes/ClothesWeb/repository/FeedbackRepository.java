package com.web.clothes.ClothesWeb.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.web.clothes.ClothesWeb.entity.Feedback;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback,Integer>{
	Page<Feedback> findFeedbackPage(Pageable pageable);
}
