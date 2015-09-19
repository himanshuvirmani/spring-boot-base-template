package com.springboot.demo.web.rest;

import com.springboot.demo.AbstractControllerTest;
import com.springboot.demo.domain.City;
import com.springboot.demo.service.CityService;
import com.springboot.demo.service.criteria.CitySearchCriteria;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by himanshu.virmani on 19/09/15.
 */
@Transactional
public class CityControllerTest extends AbstractControllerTest{

    @Mock
    private CityService cityService;

    @InjectMocks
    private CityController cityController;

    @Before
    public void setUp() {
        // Initialize Mockito annotated components
        MockitoAnnotations.initMocks(this);
        // Prepare the Spring MVC Mock components for standalone testing
        setUp(cityController);
    }

    @Test
    public void shouldSearchCity() throws Exception {

        CitySearchCriteria criteria = new CitySearchCriteria("T");
        PageRequest pageRequest = new PageRequest(1, 4);
        // Create some test data
        Page<City> list = getEntityListStubData();

        when(cityService.findCities(criteria,pageRequest)).thenReturn(list);

        ResultActions s = mvc.perform(MockMvcRequestBuilders.get("/city/search/T")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(1)));

        logger.info("shouldSearchCity " + s.andReturn().getResponse().getContentAsString());
    }

    private Page<City> getEntityListStubData() {
        List<City> list = new ArrayList<>();
        list.add(getEntityStubData());
        PageImpl<City> page = new PageImpl<>(list);
        return page;
    }

    private City getEntityStubData() {
        City entity = new City();
        entity.setId(1L);
        entity.setName("Miami");
        entity.setState("Florida");
        return entity;
    }
}
