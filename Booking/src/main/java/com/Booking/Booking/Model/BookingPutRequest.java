package com.Booking.Booking.Model;

import java.util.List;

import com.Booking.Booking.Entities.BookingData.Unit;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public @Data class BookingPutRequest {

	private Long rate;
	private Unit unitValue;
	private List<String> truckId;
	private Boolean cancel;
	private Boolean completed;
	private String bookingDate;
	private String completedDate;

}
