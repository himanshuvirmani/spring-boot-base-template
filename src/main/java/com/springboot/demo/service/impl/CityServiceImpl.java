package com.springboot.demo.service.impl;

import com.springboot.demo.domain.City;
import com.springboot.demo.domain.HotelSummary;
import com.springboot.demo.repository.CityRepository;
import com.springboot.demo.repository.HotelRepository;
import com.springboot.demo.repository.redis.impl.HashCacheRedisRepository;
import com.springboot.demo.repository.redis.impl.ValueCacheRedisRepository;
import com.springboot.demo.service.CityService;
import com.springboot.demo.service.criteria.CitySearchCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.concurrent.TimeUnit;

@Component("cityService")
@Transactional
public class CityServiceImpl implements CityService {

    private final CityRepository cityRepository;

    private final HotelRepository hotelRepository;

    @Autowired
    private HashCacheRedisRepository<City> valueCacheRedisRepository;

    @Autowired
    public CityServiceImpl(CityRepository cityRepository, HotelRepository hotelRepository) {
        this.cityRepository = cityRepository;
        this.hotelRepository = hotelRepository;
    }

    @Override
    public Page<City> findCities(CitySearchCriteria criteria, Pageable pageable) {

        Assert.notNull(criteria, "Criteria must not be null");
        String name = criteria.getName();

        if (!StringUtils.hasLength(name)) {
            return this.cityRepository.findAll(null);
        }

        String country = "";
        int splitPos = name.lastIndexOf(",");

        if (splitPos >= 0) {
            country = name.substring(splitPos + 1);
            name = name.substring(0, splitPos);
        }

        return this.cityRepository
                .findByNameContainingAndCountryContainingAllIgnoringCase(name.trim(),
                        country.trim(), pageable);
    }

    @Override
    public City getCity(String name, String country) {
        Assert.notNull(name, "Name must not be null");
        Assert.notNull(country, "Country must not be null");
        City city = valueCacheRedisRepository.multiGet("city:" + name + ":country:" + country, City.class);

        if (city == null) {
            city = cityRepository.findByNameAndCountryAllIgnoringCase(name, country);
            if (city != null) {
                valueCacheRedisRepository.multiPut("city:" + name + ":country:" + country, city);
                valueCacheRedisRepository.expire("city:" + name + ":country:" + country, 60, TimeUnit.SECONDS);
            }
        }
        return city;
    }

    @Override
    public Page<HotelSummary> getHotels(City city, Pageable pageable) {
        Assert.notNull(city, "City must not be null");
        return this.hotelRepository.findByCity(city, pageable);
    }
}
