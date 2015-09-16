package com.springboot.demo.service;

import com.springboot.demo.domain.City;
import com.springboot.demo.domain.Hotel;
import com.springboot.demo.domain.Review;
import com.springboot.demo.domain.enums.HotelStatusEnum;
import com.springboot.demo.web.rest.dto.ReviewDetailsDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface HotelService {

    Hotel getHotel(City city, String name);

    Page<Review> getReviews(Hotel hotel, Pageable pageable);

    Review getReview(Hotel hotel, int index);

    Hotel manageHotel(City city, String name, HotelStatusEnum hotelStatusEnum);

    Review addReview(Hotel hotel, ReviewDetailsDto details);

    ReviewsSummary getReviewSummary(Hotel hotel);
}
