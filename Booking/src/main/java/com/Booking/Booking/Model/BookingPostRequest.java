package com.Booking.Booking.Model;

import java.util.List;

import lombok.Data;


public @Data class BookingPostRequest {

	private String transporterId;
	private String loadId;
	private String postLoadId;
	private Long rate;
	private String unitValue;
	private List<String> truckId;
	private String bookingDate;
	
}
