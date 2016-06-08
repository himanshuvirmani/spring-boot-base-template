/*
 * Copyright 2012-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.springboot.demo.web.rest;

import com.springboot.demo.domain.City;
import com.springboot.demo.domain.Hotel;
import com.springboot.demo.domain.Review;
import com.springboot.demo.service.CityService;
import com.springboot.demo.service.HotelService;
import com.springboot.demo.web.rest.dto.ReviewDetailsDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/hotel")
public class HotelController extends BaseController {

    private final Logger log = LoggerFactory.getLogger(HotelController.class);

    @Value("${cache.timeToLiveSeconds}")
    private String cacheTime;

    @Autowired
    private CityService cityService;

    @Autowired
    private HotelService hotelService;

    @RequestMapping(value = "/city/{city}/country/{country}/hotel/{hotel}", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @Transactional(readOnly = true)
    public Hotel getHotel(@PathVariable("city") String city,
                          @PathVariable("country") String country, @PathVariable("hotel") String hotel) {
        final City citi = cityService.getCity(city, country);
        return this.hotelService.getHotel(citi, hotel);
    }

    @RequestMapping(value = "/city/{city}/country/{country}/hotel/{hotel}/review", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @Transactional(readOnly = true)
    public Page<Review> getHotelReview(@PathVariable("city") String city,
                                       @PathVariable("country") String country, @PathVariable("hotel") String hotel) {
        final City citi = cityService.getCity(city, country);
        PageRequest pageRequest = new PageRequest(0, 4);
        log.info("Cache time config value " + cacheTime);
        return this.hotelService.getReviews(this.hotelService.getHotel(citi, hotel), pageRequest);
    }

    @RequestMapping(value = "/city/{city}/country/{country}/hotel/{hotel}/review", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Review addHotelReview(@PathVariable("city") String city,
                                 @PathVariable("country") String country, @PathVariable("hotel") String hotel, @RequestBody
                                 ReviewDetailsDto reviewDetails) {
        final City citi = cityService.getCity(city, country);
        log.info("Review Details date : - " + reviewDetails.toString());
        return this.hotelService.addReview(this.hotelService.getHotel(citi, hotel), reviewDetails);
    }
}
