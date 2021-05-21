package com.Booking.Booking.Service;

import java.util.List;

import com.Booking.Booking.Entities.BookingData;
import com.Booking.Booking.Model.BookingPostRequest;
import com.Booking.Booking.Model.BookingPostResponse;
import com.Booking.Booking.Model.BookingPutRequest;
import com.Booking.Booking.Model.BookingPutResponse;

public interface BookingService {

	BookingPostResponse addBooking(BookingPostRequest request);
	BookingPutResponse updateBooking(String bookingId, BookingPutRequest request);
	BookingData getDataById(String Id);
	List<BookingData> getDataById(Integer pageNo, Boolean cancel, Boolean completed);
    void deleteBooking(String bookingId);

}
