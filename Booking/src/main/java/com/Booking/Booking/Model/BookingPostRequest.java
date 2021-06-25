package com.Booking.Booking.Model;

import java.util.List;

import com.Booking.Booking.Entities.BookingData.Unit;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public @Data class BookingPostRequest {

	private String transporterId;
	private String loadId;
	private String postLoadId;
	private Long rate;
	private Unit unitValue;
	private List<String> truckId;
	private String bookingDate;

}
