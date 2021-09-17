package com.BookingBidding.Booking.Model;


import java.util.List;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import sharedEntity.Transporter;


@NoArgsConstructor
@AllArgsConstructor
public  @Data class ResponseTesting {
	
	private String loadingPointCity;  //loadtable
	private String unloadingPointCity; //loadtable
	private List<Truck> trucks;  //truckdata table
	private String transporterName;  //transporter table
}
