package com.BookingBidding.Booking.Model;


import java.util.List;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import sharedEntity.BookingData.Unit;
import sharedEntity.Transporter;


@NoArgsConstructor
@AllArgsConstructor
public  @Data class ResponseTesting {
	
	private String bookingId;
	private String loadId;
	private String transporterId;
	private String postLoadId;
	private Long rate;
	private Unit  unitValue;
	private List<String> truckId;
	private Boolean cancel;
	private Boolean completed;
	private String bookingDate;
	private String completedDate;	
	private String loadingPointCity;  //loadtable
	private String unloadingPointCity; //loadtable
	private List<Truck> trucks;  //truckdata table
	private String transporterName;  //transporter table
}
