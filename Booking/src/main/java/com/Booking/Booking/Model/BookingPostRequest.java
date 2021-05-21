package com.Booking.Booking.Model;

import java.util.List;

import lombok.Data;


public @Data class BookingPostRequest {

	private String transporterId;
	private String loadId;
	private Long rate;
	private String unit;
	private List<String> truckId;
	
}
