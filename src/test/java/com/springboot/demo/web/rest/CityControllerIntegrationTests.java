package com.springboot.demo.web.rest;

import com.springboot.demo.AbstractControllerTest;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Created by neerajsi on 16/09/15.
 */


/**
 * TODO
 * Explore on test utils  like EnvironmentTestUtils, ConfigFileApplicationContextInitializer, OutputCapture
 */

@Transactional
public class CityControllerIntegrationTests extends AbstractControllerTest {

    @Before
    public void setUp() {
        super.setUp();
    }


    @Test
    public void shouldSearchCity() throws Exception {

        ResultActions s = mvc.perform(MockMvcRequestBuilders.get("/city/search/T")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(4)));

        logger.info("shouldSearchCity " + s.andReturn().getResponse().getContentAsString());
    }

    @Test
    public void searchCity_CityNotFoundException_ShouldReturnHttpStatusCode404() throws Exception {

        mvc.perform(MockMvcRequestBuilders.get("/city/search/TTT").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
        logger.info("searchCity_CityNotFoundException_ShouldReturnHttpStatusCode404 " + content());
    }
}

