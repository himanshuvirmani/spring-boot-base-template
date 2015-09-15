package com.springboot.demo.service;

import com.springboot.demo.domain.Rating;

public interface ReviewsSummary {

  long getNumberOfReviewsWithRating(Rating rating);
}
