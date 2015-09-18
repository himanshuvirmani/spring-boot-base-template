package com.springboot.demo.repository;

import com.springboot.demo.Application;
import com.springboot.demo.domain.City;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;


import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

/**
 * Created by neerajsi on 18/09/15.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@ActiveProfiles("test")
public class CityRepositoryTests {

    @Autowired
    private CityRepository cityRepository;

    @Test
    public void findsFirstPageOfCities() {

        Page<City> cities = this.cityRepository.findAll(new PageRequest(0, 10));
        assertThat(cities.getTotalElements(), is(greaterThan(20L)));
    }

    @Test
    public void findByNameAndCountry() {
        City city = this.cityRepository.findByNameAndCountryAllIgnoringCase("Melbourne",
                "Australia");
        assertThat(city, notNullValue());
        assertThat(city.getName(), is(equalTo("Melbourne")));
    }

    @Test
    public void findContaining() {
        Page<City> cities = this.cityRepository
                .findByNameContainingAndCountryContainingAllIgnoringCase("", "UK",
                        new PageRequest(0, 10));
        assertThat(cities.getTotalElements(), is(equalTo(3L)));
    }

}
