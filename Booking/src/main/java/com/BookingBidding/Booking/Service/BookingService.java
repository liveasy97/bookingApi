package com.BookingBidding.Booking.Service;

import java.net.ConnectException;
import java.util.List;

//import com.Booking.Booking.Entities.BookingData;
import com.BookingBidding.Booking.Model.BookingDeleteResponse;
import com.BookingBidding.Booking.Model.BookingPostRequest;
import com.BookingBidding.Booking.Model.BookingPostResponse;
import com.BookingBidding.Booking.Model.BookingPutRequest;
import com.BookingBidding.Booking.Model.BookingPutResponse;
import com.BookingBidding.Booking.Model.ResponseTesting;

import sharedEntity.BookingData;

public interface BookingService {

	BookingPostResponse addBooking(BookingPostRequest request);

	BookingPutResponse updateBooking(String bookingId, BookingPutRequest request);

	BookingData getDataById(String Id);

	List<BookingData> getDataById(Integer pageNo, Boolean cancel, Boolean completed, String transporterId,
			String postLoadId);

	BookingDeleteResponse deleteBooking(String bookingId);
	
	void updating_load_status_by_loadid(String loadid, String inputJson) throws ConnectException, Exception;
	
	List<ResponseTesting> getDataTesting(Integer pageNo, Boolean cancel, Boolean completed, String transporterId,String postLoadId);
}
