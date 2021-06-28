package com.Booking.Booking;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.Booking.Booking.Dao.BookingDao;
import com.Booking.Booking.Entities.BookingData;
import com.Booking.Booking.Model.BookingPostRequest;
import com.Booking.Booking.Model.BookingPostResponse;
import com.Booking.Booking.Service.BookingServiceImpl;

@SpringBootTest
class BookingApplicationTests {

	@Autowired
	BookingServiceImpl service;
	
	@MockBean
	BookingDao bookingDao;
	
	@Test
	void simple() {
		List<String> truck =new ArrayList<String>();
		truck.add("tr");
		BookingPostRequest request = new BookingPostRequest("1","2","3",2L,"PER_TON",truck,null);
		BookingData data = new BookingData();
		BookingPostResponse response = new BookingPostResponse();
		
		when(bookingDao.save(data)).thenReturn(data);
		
		assertEquals("Success",service.addBooking(request).getStatus());
	}

}
