package com.Booking.Booking;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BookingApplicationTests {

	@Test
	void sample() {
		List<String> truck =new ArrayList<String>();
		truck.add("tr");
		BookingPostRequest request = new BookingPostRequest("1","2","3",2L,"PER_TON",truck,null);
		BookingData data = new BookingData();
		BookingPostResponse response = new BookingPostResponse();
		
		when(bookingDao.save(data)).thenReturn(data);
		
		assertEquals("Success",service.addBooking(request).getStatus());
	}

}
