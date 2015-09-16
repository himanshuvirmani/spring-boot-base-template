package com.springboot.demo.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum HotelStatusEventEnum {

    CLOSE("close"),
    OPEN("open"),
    SHUTDOWN("shutdown");

    @Getter
    private String hotelStatusEvent;

}