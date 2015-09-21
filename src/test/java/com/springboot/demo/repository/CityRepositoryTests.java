package com.springboot.demo.repository;

import com.springboot.demo.AbstractControllerTest;
import com.springboot.demo.AbstractTest;
import com.springboot.demo.Application;
import com.springboot.demo.domain.City;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

/**
 * Created by neerajsi on 18/09/15.
 */
@SqlGroup({
        @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:beforeTestRun.sql"),
        @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = "classpath:afterTestRun.sql")
})
//@DirtiesContext(classMode= DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)  //not needed as of now..
public class CityRepositoryTests extends AbstractTest {

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
