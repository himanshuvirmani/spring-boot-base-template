package com.springboot.demo.web.rest;

import com.springboot.demo.Application;
import com.springboot.demo.domain.City;
import com.springboot.demo.service.CityService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.util.MatcherAssertionErrors.assertThat;


/**
 * Created by neerajsi on 16/09/15.
 */

@RunWith(SpringJUnit4ClassRunner.class)

/**
 * Spring Boot provides a @SpringApplicationConfiguration annotation
 * as an alternative to the standard spring-test @ContextConfiguration annotation.
 * If you use @SpringApplicationConfiguration to configure the ApplicationContext used in your tests,
 * it will be created via SpringApplication and you will get the additional Spring Boot features.
 */
@SpringApplicationConfiguration(classes = Application.class)


/**
 * If you want a web application to start up and listen on its normal port,
 * so you can test it with HTTP (e.g. using RestTemplate),
 * annotate your test class (or one of its superclasses) with @IntegrationTest.
 * This can be very useful because it means you can test the full stack of your application,
 * but also inject its components into the test class and use them to assert the internal state of
 * the application after an HTTP interaction.
 *
 */
@WebAppConfiguration

/**
 * To change the port you can add environment properties to @IntegrationTest as colon- or
 * equals-separated name-value pairs,
 *
 * e.g. @IntegrationTest("server.port:9000").
 *
 * Additionally you can set the server.port and management.port properties to 0 in order to
 * run your integration tests using random ports.
 */
@IntegrationTest


/**
 * TODO
 *  Explore on test utils  like EnvironmentTestUtils, ConfigFileApplicationContextInitializer, OutputCapture
 *
 */
public class CityControllerIntegrationTests {


    @Autowired
    private CityService cityService;

    final String BASE_URL = "http://localhost:8080/";

    @Before
    public void setup() {
    }


    @Test
    public void shouldSearchCity() {

        RestTemplate rest = new TestRestTemplate();

        ResponseEntity<City> response = rest.getForEntity(BASE_URL, City.class, Collections.EMPTY_MAP);
        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));

        City cities = response.getBody();
    }

}
