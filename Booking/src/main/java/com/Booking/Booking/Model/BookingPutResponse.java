package com.Booking.Booking.Model;

import java.util.List;

import com.Booking.Booking.Entities.BookingData.Unit;

import lombok.Data;

public @Data class BookingPutResponse {
	
	private String status;
	
	private String bookingId;
	private String transporterId;
	private String loadId;
	private String postLoadId;
	private Long rate;
	private Unit unitValue;
	private List<String> truckId;
	private Boolean cancel;
	private Boolean completed;
	
	
}
