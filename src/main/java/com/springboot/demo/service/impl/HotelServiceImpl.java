package com.springboot.demo.service.impl;

import com.springboot.demo.annotation.StatesOnTransition;
import com.springboot.demo.domain.*;
import com.springboot.demo.domain.enums.HotelStatusEnum;
import com.springboot.demo.domain.enums.HotelStatusEventEnum;
import com.springboot.demo.repository.HotelRepository;
import com.springboot.demo.repository.ReviewRepository;
import com.springboot.demo.service.HotelService;
import com.springboot.demo.service.ReviewsSummary;
import com.springboot.demo.web.rest.dto.ReviewDetailsDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.ExtendedState;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.annotation.WithStateMachine;
import org.springframework.statemachine.support.StateMachineUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component("hotelService")
@Transactional
public class HotelServiceImpl implements HotelService {

    private static final Logger log = LoggerFactory.getLogger(HotelServiceImpl.class);

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
    public Hotel manageHotel(City city, String name, HotelStatusEnum hotelStatusEnum) {
        final Hotel hotel = this.hotelRepository.findByCityAndName(city, name);
        switch (hotelStatusEnum) {
            case OPENED:
                hotel.getStateMachine().sendEvent(MessageBuilder.withPayload(HotelStatusEventEnum.OPEN)
                        .setHeader(("hotel"), hotel).build());
                break;
            case CLOSED:
                hotel.getStateMachine().sendEvent(MessageBuilder.withPayload(HotelStatusEventEnum.CLOSE)
                        .setHeader(("hotel"), hotel).build());
                break;
            case SHUTDOWN:
                break;
        }
        log.info("Returning Hotel");
        return this.hotelRepository.findByCityAndName(city, name);
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

    @StatesOnTransition(target = HotelStatusEnum.OPENED)
    public void openEvent(ExtendedState extendedState) {
        final Hotel hotel = (Hotel)extendedState.getVariables().get("hotel");
        hotel.setStatus(HotelStatusEnum.OPENED);
        hotelRepository.save(hotel);
        log.info("Hotel Opened");
    }

    @StatesOnTransition(target = HotelStatusEnum.CLOSED)
    public void closeEvent(ExtendedState extendedState) {
        final Hotel hotel = (Hotel)extendedState.getVariables().get("hotel");
        hotel.setStatus(HotelStatusEnum.CLOSED);
        hotelRepository.save(hotel);
        log.info("Hotel Closed");
    }
}
