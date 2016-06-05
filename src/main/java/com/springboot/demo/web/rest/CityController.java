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
import com.springboot.demo.service.CityService;
import com.springboot.demo.service.criteria.CitySearchCriteria;
import com.springboot.demo.web.rest.errors.CityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/city")
public class CityController extends BaseController {

    @Autowired
    private CityService cityService;


    @RequestMapping(value = "/search/{keyword}", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @Transactional(readOnly = true)
    public Page<City> search(@PathVariable("keyword") String keyword) {
        CitySearchCriteria criteria = new CitySearchCriteria(keyword);
        PageRequest pageRequest = new PageRequest(1, 4);
        Page<City> result = this.cityService.findCities(criteria, pageRequest);
        if (result == null || result.getTotalElements() == 0) {
            throw new CityNotFoundException();
        }
        return result;
    }

    @RequestMapping(value = "/{city}/country/{country}", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @Transactional(readOnly = true)
    public City getCity(@PathVariable("city") String city, @PathVariable("country") String country) {
        City result = this.cityService.getCity(city, country);
        if (result == null) {
            throw new CityNotFoundException();
        }
        return result;
    }

}
