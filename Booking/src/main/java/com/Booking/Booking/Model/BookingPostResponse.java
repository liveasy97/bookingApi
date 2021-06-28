package com.Booking.Booking.Model;

import java.util.List;


import com.Booking.Booking.Entities.BookingData.Unit;

import lombok.Data;

@AllArgsConstructor
@NoArgsConstructor
public @Data class BookingPostResponse {

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
	private String bookingDate;
	private String completedDate;
	
}
