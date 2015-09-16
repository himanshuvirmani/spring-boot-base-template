/*
 * Copyright 2012-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.springboot.demo.domain;

import com.springboot.demo.domain.enums.HotelStatusEnum;
import com.springboot.demo.domain.enums.HotelStatusEventEnum;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.NaturalId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.support.StaticListableBeanFactory;
import org.springframework.core.task.SyncTaskExecutor;
import org.springframework.messaging.Message;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.StateMachineSystemConstants;
import org.springframework.statemachine.config.ObjectStateMachineFactory;
import org.springframework.statemachine.config.StateMachineConfig;
import org.springframework.statemachine.config.StateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.*;
import org.springframework.statemachine.listener.StateMachineListener;
import org.springframework.statemachine.state.State;
import org.springframework.statemachine.transition.Transition;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.EnumSet;
import java.util.Set;

@Data
@NoArgsConstructor
@Entity
public class Hotel implements Serializable {

    private static final Logger log = LoggerFactory.getLogger(Hotel.class);

    private static final long serialVersionUID = 1L;

    @Transient
    private StateMachine<HotelStatusEnum, HotelStatusEventEnum> stateMachine;

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    @NaturalId
    private City city;

    @Column(nullable = false)
    @NaturalId
    private String name;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String zip;

    @NotNull
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private HotelStatusEnum status = HotelStatusEnum.OPENED;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "hotel")
    private Set<Review> reviews;

    public Hotel(City city, String name) {
        this.city = city;
        this.name = name;
    }

    @PostLoad
    public void setStateMachineState() throws Exception {
        log.info("setStateMachineState on Post Load");
        StateMachineConfigBuilder<HotelStatusEnum, HotelStatusEventEnum> builder = new StateMachineConfigBuilder<HotelStatusEnum, HotelStatusEventEnum>();
        Config config = new Config(status);
        builder.apply(config);
        StateMachineConfig<HotelStatusEnum, HotelStatusEventEnum> stateMachineConfig = builder.getOrBuild();

        StateMachineTransitions<HotelStatusEnum, HotelStatusEventEnum> stateMachineTransitions = stateMachineConfig.getTransitions();
        StateMachineStates<HotelStatusEnum, HotelStatusEventEnum> stateMachineStates = stateMachineConfig.getStates();
        StateMachineConfigurationConfig<HotelStatusEnum, HotelStatusEventEnum> stateMachineConfigurationConfig = stateMachineConfig.getStateMachineConfigurationConfig();
        ObjectStateMachineFactory<HotelStatusEnum, HotelStatusEventEnum> stateMachineFactory = new ObjectStateMachineFactory<>(
                stateMachineConfigurationConfig, stateMachineTransitions, stateMachineStates);
        StaticListableBeanFactory beanFactory = new StaticListableBeanFactory();
        beanFactory.addBean(StateMachineSystemConstants.TASK_EXECUTOR_BEAN_NAME, new SyncTaskExecutor());
        beanFactory.addBean("taskScheduler", new ConcurrentTaskScheduler());
        stateMachineFactory.setBeanFactory(beanFactory);
        stateMachine = stateMachineFactory.getStateMachine();
        stateMachine.addStateListener(new StateMachineListener<HotelStatusEnum, HotelStatusEventEnum>() {
            @Override
            public void stateChanged(State<HotelStatusEnum, HotelStatusEventEnum> from, State<HotelStatusEnum, HotelStatusEventEnum> to) {
                log.info("stateChanged" + "from " + from + "to " + to );
            }

            @Override
            public void stateEntered(State<HotelStatusEnum, HotelStatusEventEnum> state) {
                log.info("stateEntered" + "state " + state);
            }

            @Override
            public void stateExited(State<HotelStatusEnum, HotelStatusEventEnum> state) {
                log.info("stateExited" + "state " + state);
            }

            @Override
            public void eventNotAccepted(Message<HotelStatusEventEnum> event) {

            }

            @Override
            public void transition(Transition<HotelStatusEnum, HotelStatusEventEnum> transition) {
                log.info("transition");
            }

            @Override
            public void transitionStarted(Transition<HotelStatusEnum, HotelStatusEventEnum> transition) {
                log.info("transitionStarted");
            }

            @Override
            public void transitionEnded(Transition<HotelStatusEnum, HotelStatusEventEnum> transition) {
                log.info("transitionEnded");
            }

            @Override
            public void stateMachineStarted(StateMachine<HotelStatusEnum, HotelStatusEventEnum> stateMachine) {
                log.info("stateMachineStarted");
            }

            @Override
            public void stateMachineStopped(StateMachine<HotelStatusEnum, HotelStatusEventEnum> stateMachine) {
                log.info("stateMachineStopped");
            }

            @Override
            public void stateMachineError(StateMachine<HotelStatusEnum, HotelStatusEventEnum> stateMachine, Exception exception) {
                log.info("stateMachineError");
            }

            @Override
            public void extendedStateChanged(Object key, Object value) {
                log.info("extendedStateChanged");
            }
        });
        stateMachine.start();
    }

    static class Config extends StateMachineConfigurerAdapter<HotelStatusEnum, HotelStatusEventEnum> {

        private HotelStatusEnum hotelStatusEnum;

        public Config(HotelStatusEnum status) {
            hotelStatusEnum = status;
        }

        @Override
        public void configure(StateMachineStateConfigurer<HotelStatusEnum, HotelStatusEventEnum> states)
                throws Exception {
            states
                    .withStates()
                    .initial(hotelStatusEnum)
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
}
