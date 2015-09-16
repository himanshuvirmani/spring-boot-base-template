package com.springboot.demo.config.statemachine;

import com.springboot.demo.domain.enums.HotelStatusEnum;
import com.springboot.demo.domain.enums.HotelStatusEventEnum;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;

import java.util.EnumSet;

/**
 * Created by himanshu.virmani on 16/09/15.
 */
/*@Configuration
@EnableStateMachine*/
public class HotelStatusConfigurer extends EnumStateMachineConfigurerAdapter<HotelStatusEnum, HotelStatusEventEnum> {

    @Override
    public void configure(StateMachineStateConfigurer<HotelStatusEnum, HotelStatusEventEnum> states)
            throws Exception {
        states
                .withStates()
                .initial(HotelStatusEnum.OPENED)
                .states(EnumSet.allOf(HotelStatusEnum.class));
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<HotelStatusEnum, HotelStatusEventEnum> transitions)
            throws Exception {
        transitions
                .withExternal()
                .source(HotelStatusEnum.OPENED).target(HotelStatusEnum.CLOSED)
                .event(HotelStatusEventEnum.CLOSE)
                .and()
                .withExternal()
                .source(HotelStatusEnum.CLOSED).target(HotelStatusEnum.OPENED)
                .event(HotelStatusEventEnum.OPEN);
    }
}
