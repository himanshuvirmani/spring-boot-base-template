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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/city")
public class CityController {

    @Autowired
    private CityService cityService;

    @RequestMapping("/search/{keyword}")
    @ResponseBody
    @Transactional(readOnly = true)
    public Page<City> search(@PathVariable("keyword") String keyword) {
        CitySearchCriteria criteria = new CitySearchCriteria(keyword);
        PageRequest pageRequest = new PageRequest(1, 4);
        return this.cityService.findCities(criteria, pageRequest);
    }

}
