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

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.springboot.demo.domain.util.CustomDateTimeDeserializer;
import com.springboot.demo.domain.util.CustomDateTimeSerializer;
import com.springboot.demo.web.rest.dto.ReviewDetailsDto;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.Type;
import org.hibernate.envers.Audited;
import org.joda.time.DateTime;
import org.springframework.util.Assert;

@Data
@EqualsAndHashCode(callSuper=true)
@Entity
public class Review extends BaseEntity {

	@ManyToOne(optional = false)
	private Hotel hotel;

	@Column(nullable = false, name = "idx")
	private int index;

	@Column(nullable = false)
	@Enumerated(EnumType.ORDINAL)
	private Rating rating;

	@NotNull
	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	@JsonSerialize(using = CustomDateTimeSerializer.class)
	@JsonDeserialize(using = CustomDateTimeDeserializer.class)
	private DateTime checkInDate;

	@Column(nullable = false)
	@Enumerated(EnumType.ORDINAL)
	@Audited
	private TripType tripType;

	@Column(nullable = false)
	@Audited
	private String title;

	@Column(nullable = false, length = 5000)
	@Audited
	private String details;

	protected Review() {
	}

	public Review(Hotel hotel, int index, ReviewDetailsDto details) {
		Assert.notNull(hotel, "Hotel must not be null");
		Assert.notNull(details, "Details must not be null");
		this.hotel = hotel;
		this.index = index;
		this.rating = details.getRating();
		this.checkInDate = details.getCheckInDate();
		this.tripType = details.getTripType();
		this.title = details.getTitle();
		this.details = details.getDetails();
	}

}
