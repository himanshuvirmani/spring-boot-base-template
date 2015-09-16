package com.springboot.demo.service.impl;

import com.springboot.demo.domain.*;
import com.springboot.demo.repository.HotelRepository;
import com.springboot.demo.repository.ReviewRepository;
import com.springboot.demo.service.HotelService;
import com.springboot.demo.service.ReviewsSummary;
import com.springboot.demo.web.rest.dto.ReviewDetailsDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component("hotelService")
@Transactional
public class HotelServiceImpl implements HotelService {

    private final HotelRepository hotelRepository;

    private final ReviewRepository reviewRepository;

    @Autowired
    public HotelServiceImpl(HotelRepository hotelRepository,
                            ReviewRepository reviewRepository) {
        this.hotelRepository = hotelRepository;
        this.reviewRepository = reviewRepository;
    }

    @Override
    public Hotel getHotel(City city, String name) {
        Assert.notNull(city, "City must not be null");
        Assert.hasLength(name, "Name must not be empty");
        return this.hotelRepository.findByCityAndName(city, name);
    }

    @Override
    public Page<Review> getReviews(Hotel hotel, Pageable pageable) {
        Assert.notNull(hotel, "Hotel must not be null");
        return this.reviewRepository.findByHotel(hotel, pageable);
    }

    @Override
    public Review getReview(Hotel hotel, int reviewNumber) {
        Assert.notNull(hotel, "Hotel must not be null");
        return this.reviewRepository.findByHotelAndIndex(hotel, reviewNumber);
    }

    @Override
    public Review addReview(Hotel hotel, ReviewDetailsDto details) {
        Review review = new Review(hotel, 1, details);
        return reviewRepository.save(review);
    }

    @Override
    public ReviewsSummary getReviewSummary(Hotel hotel) {
        List<RatingCount> ratingCounts = this.hotelRepository.findRatingCounts(hotel);
        return new ReviewsSummaryImpl(ratingCounts);
    }

    private static class ReviewsSummaryImpl implements ReviewsSummary {

        private final Map<Rating, Long> ratingCount;

        public ReviewsSummaryImpl(List<RatingCount> ratingCounts) {
            this.ratingCount = new HashMap<Rating, Long>();
            for (RatingCount ratingCount : ratingCounts) {
                this.ratingCount.put(ratingCount.getRating(), ratingCount.getCount());
            }
        }

        @Override
        public long getNumberOfReviewsWithRating(Rating rating) {
            Long count = this.ratingCount.get(rating);
            return count == null ? 0 : count;
        }
    }
}
