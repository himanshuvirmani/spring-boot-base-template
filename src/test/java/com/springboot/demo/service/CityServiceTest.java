package com.springboot.demo.service;

import com.springboot.demo.AbstractTest;
import com.springboot.demo.domain.City;
import com.springboot.demo.repository.CityRepository;
import com.springboot.demo.repository.HotelRepository;
import com.springboot.demo.service.criteria.CitySearchCriteria;
import com.springboot.demo.service.impl.CityServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

/**
 * Created by himanshu.virmani on 20/09/15.
 */
public class CityServiceTest extends AbstractTest {

    private CityService cityService;

    @Mock
    private CityRepository cityRepository;

    @Mock
    private HotelRepository hotelRepository;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        cityService = new CityServiceImpl(cityRepository, hotelRepository);
    }

    @Test
    public void testSearchCities() {
        PageRequest pageRequest = new PageRequest(0, 4);
        when(cityRepository.findByNameContainingAndCountryContainingAllIgnoringCase
                ("Test", "", pageRequest)) //we can also use Mockito.any etc if response is independent.
                .thenReturn(getEntityListStubData());
        CitySearchCriteria criteria = new CitySearchCriteria("Test");

        Page<City> cities = cityService.findCities(criteria, pageRequest);
        logger.info("testSearchCities " + cities.getContent());
        Assert.assertNotNull("failure - expected not null", cities.getContent());
        Assert.assertEquals("failure - expected 1", 1L,
                cities.getTotalElements());

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
