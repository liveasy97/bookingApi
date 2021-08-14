package com.Booking.Booking.Service;

import java.net.ConnectException;
import java.util.List;

import com.Booking.Booking.Entities.BookingData;
import com.Booking.Booking.Model.BookingDeleteResponse;
import com.Booking.Booking.Model.BookingPostRequest;
import com.Booking.Booking.Model.BookingPostResponse;
import com.Booking.Booking.Model.BookingPutRequest;
import com.Booking.Booking.Model.BookingPutResponse;

public interface BookingService {

	BookingPostResponse addBooking(BookingPostRequest request);

	BookingPutResponse updateBooking(String bookingId, BookingPutRequest request);

	BookingData getDataById(String Id);

	List<BookingData> getDataById(Integer pageNo, Boolean cancel, Boolean completed, String transporterId,
			String postLoadId);

	BookingDeleteResponse deleteBooking(String bookingId);
	
	void updating_load_status_by_loadid(String loadid, String inputJson) throws ConnectException, Exception;

}
