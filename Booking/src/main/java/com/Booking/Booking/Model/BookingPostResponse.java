package com.Booking.Booking.Model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Id;

import com.Booking.Booking.Entities.BookingData.Unit;

import lombok.Data;

public @Data class BookingPostResponse {

	private String status;
	
	private String bookingId;
	private String transporterId;
	private String loadId;
	private String postLoadId;
	private Long rate;
	private Unit unitValue;
	@Column(name="truckIds")
	@ElementCollection(targetClass=String.class)
	private List<String> truckId;
	private Boolean cancel;
	private Boolean completed;
	
}
