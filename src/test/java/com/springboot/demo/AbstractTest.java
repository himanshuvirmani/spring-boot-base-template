package com.springboot.demo;

import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * The AbstractTest class is the parent of all JUnit test classes. This class
 * configures the test ApplicationContext and test runner environment.
 * <p/>
 * Web application context here because of SpringFox Swagger. Ideally it should
 * be required only for controller tests.
 *
 * @author Himanshu Virmani
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(
        classes = Application.class)
@ActiveProfiles("test")
//@WebAppConfiguration needed due to swagger failures while running test. https://github.com/springfox/springfox/issues/654
@WebAppConfiguration
public abstract class AbstractTest {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

}
