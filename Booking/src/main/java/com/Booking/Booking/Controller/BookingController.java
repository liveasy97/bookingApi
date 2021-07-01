package com.Booking.Booking.Controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.Booking.Booking.Entities.BookingData;
import com.Booking.Booking.Model.BookingDeleteResponse;
import com.Booking.Booking.Model.BookingPostRequest;
import com.Booking.Booking.Model.BookingPostResponse;
import com.Booking.Booking.Model.BookingPutRequest;
import com.Booking.Booking.Model.BookingPutResponse;
import com.Booking.Booking.Service.BookingService;
import com.Booking.Booking.EntityNotFoundException;

@RestController
public class BookingController {

	@Autowired
	private BookingService bookingService;

	@PostMapping("/booking")
	public BookingPostResponse addBooking(@Valid @RequestBody BookingPostRequest request) {
		return bookingService.addBooking(request);
	}

	@PutMapping("/booking/{bookingId}")
	public BookingPutResponse updateBooking(@Valid @RequestBody BookingPutRequest request,
			@PathVariable String bookingId) throws EntityNotFoundException {
		return bookingService.updateBooking(bookingId, request);
	}

	@GetMapping("/booking/{bookingId}")
	public BookingData getDataById(@PathVariable String bookingId) throws EntityNotFoundException {
		return bookingService.getDataById(bookingId);
	}

	@GetMapping("/booking")
	public List<BookingData> getData(@RequestParam(value = "pageNo", required = false) Integer pageNo,
			@RequestParam(value = "cancel", required = false) Boolean cancel,
			@RequestParam(value = "completed", required = false) Boolean completed,
			@RequestParam(value = "transporterId", required = false) String transporterId,
			@RequestParam(value = "postLoadId", required = false) String postLoadId) throws EntityNotFoundException {
		return bookingService.getDataById(pageNo, cancel, completed, transporterId, postLoadId);
	}

	@DeleteMapping("/booking/{bookingId}")
	public BookingDeleteResponse deleteBooking(@PathVariable String bookingId) throws EntityNotFoundException {
		return bookingService.deleteBooking(bookingId);
	}
}
