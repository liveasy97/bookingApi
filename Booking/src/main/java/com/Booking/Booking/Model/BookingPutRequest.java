package com.Booking.Booking.Model;

import java.util.List;

import lombok.Data;


public @Data class BookingPutRequest {

	private Long rate;
	private String unit;
	private List<String> truckId;
	private Boolean cancel;
	private Boolean completed;
	
}
