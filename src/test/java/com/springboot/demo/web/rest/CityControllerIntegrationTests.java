package com.springboot.demo.web.rest;

import com.springboot.demo.Application;
import com.springboot.demo.service.CityService;
import com.springboot.demo.util.TestUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;

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
//@SpringApplicationConfiguration(classes = Application.class)

/**
 * Note the use of the MockServletContext to set up an empty WebApplicationContext
 * so the HelloController can be created in the @Before and passed to MockMvcBuilders.standaloneSetup().
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
//@IntegrationTest


/**
 * TODO
 *  Explore on test utils  like EnvironmentTestUtils, ConfigFileApplicationContextInitializer, OutputCapture
 *
 */

@ActiveProfiles("test")
public class CityControllerIntegrationTests {


    final String BASE_URL = "http://localhost:8080/";

    private MockMvc mvc;

    @Autowired
    private CityService cityServiceMock;

    @Autowired
    private WebApplicationContext wac;

    @Before
    public void setUp() throws Exception {
//        mvc = MockMvcBuilders.standaloneSetup(new CityController()).build();
        this.mvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }


    @Test
    public void shouldSearchCity() throws Exception {

        ResultActions s = mvc.perform(MockMvcRequestBuilders.get("/city/search/T")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(4)));

        System.out.println("Hello" + s.andReturn().getResponse().getContentAsString());
    }

    @Test
    public void searchCity_CityNotFoundException_ShouldReturnHttpStatusCode404() throws Exception {

        mvc.perform(MockMvcRequestBuilders.get("/city/search/TTT").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
        System.out.println("Hello" + content());
    }
}

