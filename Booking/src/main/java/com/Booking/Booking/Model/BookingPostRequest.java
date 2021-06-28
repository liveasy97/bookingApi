package com.Booking.Booking.Model;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public @Data class BookingPostRequest {

	private String transporterId;
	private String loadId;
	private String postLoadId;
	private Long rate;
	private String unitValue;
	private List<String> truckId;
	private String bookingDate;
	
}
