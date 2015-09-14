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

package com.springboot.demo.web.rest.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.springboot.demo.domain.Rating;
import com.springboot.demo.domain.TripType;
import com.springboot.demo.domain.util.CustomDateTimeDeserializer;
import com.springboot.demo.domain.util.CustomDateTimeSerializer;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

@Data
public class ReviewDetailsDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private Rating rating;

	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	@JsonSerialize(using = CustomDateTimeSerializer.class)
	@JsonDeserialize(using = CustomDateTimeDeserializer.class)
	private DateTime checkInDate;

	private TripType tripType;

	private String title;

	private String details;

}
