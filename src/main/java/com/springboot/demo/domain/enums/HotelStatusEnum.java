package com.springboot.demo.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum HotelStatusEnum {

    CLOSED("closed"),
    OPENED("opened"),
    SHUTDOWN("shutdown");

    @Getter
    private String hotelStatus;

}