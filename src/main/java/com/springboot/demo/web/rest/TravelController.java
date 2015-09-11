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
import com.springboot.demo.service.CityService;
import com.springboot.demo.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TravelController {

	@Autowired
	private CityService cityService;

	@Autowired
	private HotelService hotelService;

	@RequestMapping("/")
	@ResponseBody
	@Transactional(readOnly = true)
	public Hotel helloWorld() {
		//return this.cityService.getCity("Bath", "UK");
		City city = cityService.getCity("Bath", "UK");
		return this.hotelService.getHotel(city, "Bath Travelodge");
	}

}
