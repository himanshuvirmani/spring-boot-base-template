package com.springboot.demo.common;

import java.time.ZonedDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * This class returns the current time.
 *
 * @author Petri Kainulainen
 */
@Component("dateTimeService")
public class CurrentTimeDateTimeService implements DateTimeService {

  private static final Logger LOGGER = LoggerFactory.getLogger(CurrentTimeDateTimeService.class);

  @Override
  public ZonedDateTime getCurrentDateAndTime() {
    ZonedDateTime currentDateAndTime = ZonedDateTime.now();

    LOGGER.info("Returning current date and time: {}", currentDateAndTime);

    return currentDateAndTime;
  }
}
