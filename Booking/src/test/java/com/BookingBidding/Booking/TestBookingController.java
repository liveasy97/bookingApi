//package com.Booking.Booking;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.Mockito.when;
//
//import java.util.Arrays;
//import java.util.List;
//
//import org.junit.jupiter.api.Test;
//import org.mockito.Mockito;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.mock.web.MockHttpServletResponse;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.MvcResult;
//import org.springframework.test.web.servlet.RequestBuilder;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//
//import com.Booking.Booking.Constants.BookingConstants;
//import com.Booking.Booking.Controller.BookingController;
//import com.Booking.Booking.Dao.BookingDao;
//import com.Booking.Booking.Entities.BookingData;
//import com.Booking.Booking.Model.BookingDeleteResponse;
//import com.Booking.Booking.Model.BookingPostRequest;
//import com.Booking.Booking.Model.BookingPostResponse;
//import com.Booking.Booking.Model.BookingPutRequest;
//import com.Booking.Booking.Model.BookingPutResponse;
//import com.Booking.Booking.Service.BookingServiceImpl;
//import com.fasterxml.jackson.databind.DeserializationFeature;
//import com.fasterxml.jackson.databind.ObjectMapper;
//
//@WebMvcTest(value = BookingController.class)
//public class TestBookingController {
//	@Autowired
//	private MockMvc mockMvc;
//
//	@MockBean
//	private BookingDao bookingDao;
//
//	@MockBean
//	private BookingServiceImpl bookingService;
//
//	private static String mapToJson(Object object) throws Exception {
//		ObjectMapper objectMapper = new ObjectMapper();
//		objectMapper.configure(DeserializationFeature.FAIL_ON_NUMBERS_FOR_ENUMS, false);
//		objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
//		objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT, true);
//		objectMapper.configure(DeserializationFeature.READ_ENUMS_USING_TO_STRING, true);
//
//		return objectMapper.writeValueAsString(object);
//	}
//
//	@Test
//	public void addData() throws Exception {
//
//		BookingPostRequest bookingPostRequest = new BookingPostRequest(BookingConstants.TRANSPORTER_ID,
//				BookingConstants.LOAD_ID, BookingConstants.POST_LOAD_ID, (long) 20, BookingData.Unit.PER_TON,
//				Arrays.asList("truck:5ce284b9-d55a-4d3d-82fd-b7ce8bb074af"), null);
//
//		BookingPostResponse bookingPostResponse = new BookingPostResponse(BookingConstants.success, BookingConstants.ID,
//				BookingConstants.TRANSPORTER_ID, BookingConstants.LOAD_ID, BookingConstants.POST_LOAD_ID, (long) 20,
//				BookingData.Unit.PER_TON, Arrays.asList("truck:5ce284b9-d55a-4d3d-82fd-b7ce8bb074af"), false, false,
//				null, null);
//
//		when(bookingService.addBooking(Mockito.any(BookingPostRequest.class))).thenReturn(bookingPostResponse);
//
//		String inputJson = mapToJson(bookingPostRequest);
//
//		String expectedJson = mapToJson(bookingPostResponse);
//
//		String URI = BookingConstants.URI;
//
//		RequestBuilder requestBuilder = MockMvcRequestBuilders.post(URI).accept(MediaType.APPLICATION_JSON)
//				.content(inputJson).contentType(MediaType.APPLICATION_JSON);
//
//		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
//		MockHttpServletResponse response = result.getResponse();
//
//		String outputInJson = response.getContentAsString();
//
//		assertThat(outputInJson).isEqualTo(expectedJson);
//		assertEquals(HttpStatus.OK.value(), response.getStatus());
//
//	}
//
//	@Test
//	public void getBookingDataWithId() throws Exception {
//
//		List<BookingData> listBookingData = createBookingData();
//
//		when(bookingService.getDataById(BookingConstants.ID)).thenReturn(listBookingData.get(0));
//
//		String URI = BookingConstants.ID_URI;
//		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(URI).accept(MediaType.APPLICATION_JSON);
//
//		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
//		MockHttpServletResponse response = result.getResponse();
//
//		String expectedJson = mapToJson(listBookingData.get(0));
//		String outputInJson = result.getResponse().getContentAsString();
//
//		assertEquals(HttpStatus.OK.value(), response.getStatus());
//		assertEquals(expectedJson, outputInJson);
//
//	}
//
//	@Test
//	public void getBookingDataWithParameters() throws Exception {
//
//		List<BookingData> listBookingData = createBookingData();
//
//		when(bookingService.getDataById(0, true, false, BookingConstants.TRANSPORTER_ID, null))
//				.thenReturn(listBookingData.subList(6, 7));
//
//		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(BookingConstants.URI)
//				.queryParam("transporterId", BookingConstants.TRANSPORTER_ID).queryParam("pageNo", String.valueOf(0))
//				.queryParam("completed", String.valueOf(false)).queryParam("cancel", String.valueOf(true))
//				.accept(MediaType.APPLICATION_JSON);
//
//		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
//		MockHttpServletResponse response = result.getResponse();
//
//		String outputInJson = response.getContentAsString();
//		String expectedJson = mapToJson(listBookingData.subList(6, 7));
//
//		assertEquals(expectedJson, outputInJson);
//		assertEquals(HttpStatus.OK.value(), response.getStatus());
//	}
//
//	@Test
//	public void updateData() throws Exception {
//
//		List<BookingData> listBookingData = createBookingData();
//
//		when(bookingService.getDataById(BookingConstants.ID)).thenReturn(listBookingData.get(0));
//
//		BookingPutRequest bookingPutRequest = new BookingPutRequest((long) 10000, BookingData.Unit.PER_TRUCK,
//				Arrays.asList("truck:abc"), true, false, null, null);
//
//		BookingPutResponse response = new BookingPutResponse(BookingConstants.UPDATE_SUCCESS, BookingConstants.ID,
//				BookingConstants.TRANSPORTER_ID, BookingConstants.LOAD_ID, BookingConstants.POST_LOAD_ID, (long) 10000,
//				BookingData.Unit.PER_TRUCK, Arrays.asList("truck:abc"), true, false, null, null);
//
//		String inputJson = mapToJson(bookingPutRequest);
//
//		String expectedJson = mapToJson(response);
//
//		when(bookingService.updateBooking(BookingConstants.ID, bookingPutRequest)).thenReturn(response);
//
//		RequestBuilder requestBuilder = MockMvcRequestBuilders.put(BookingConstants.ID_URI)
//				.accept(MediaType.APPLICATION_JSON).content(inputJson).contentType(MediaType.APPLICATION_JSON);
//
//		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
//		MockHttpServletResponse response1 = result.getResponse();
//		String outputInJson = result.getResponse().getContentAsString();
//
//		assertEquals(expectedJson, outputInJson);
//		assertEquals(HttpStatus.OK.value(), response1.getStatus());
//
//	}
//
//	@Test
//	public void deleteData() throws Exception {
//
//		List<BookingData> listBookingData = createBookingData();
//
//		when(bookingService.getDataById(BookingConstants.ID)).thenReturn(listBookingData.get(0));
//
//		BookingDeleteResponse response = new BookingDeleteResponse(BookingConstants.DELETE_SUCCESS);
//
//		String expectedJson = mapToJson(response);
//
//		when(bookingService.deleteBooking(BookingConstants.ID)).thenReturn(response);
//
//		RequestBuilder requestBuilder = MockMvcRequestBuilders.delete(BookingConstants.ID_URI)
//				.accept(MediaType.APPLICATION_JSON);
//
//		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
//		MockHttpServletResponse response1 = result.getResponse();
//		String outputInJson = result.getResponse().getContentAsString();
//
//		assertEquals(expectedJson, outputInJson);
//		assertEquals(HttpStatus.OK.value(), response1.getStatus());
//
//	}
//
//	public List<BookingData> createBookingData() {
//		List<BookingData> bookingList = Arrays.asList(
//
//				new BookingData(BookingConstants.ID, BookingConstants.TRANSPORTER_ID, BookingConstants.LOAD_ID,
//						BookingConstants.POST_LOAD_ID, (long) 20, BookingData.Unit.PER_TON,
//						Arrays.asList("truck:5ce284b9-d55a-4d3d-82fd-b7ce8bb074af"), false, false, null, null),
//
//				new BookingData("id:1", BookingConstants.TRANSPORTER_ID, "load:123", BookingConstants.POST_LOAD_ID,
//						(long) 20, BookingData.Unit.PER_TON,
//						Arrays.asList("truck:5ce284b9-d55a-4d3d-82fd-b7ce8bb074af"), false, true, null, null),
//
//				new BookingData("id:2", BookingConstants.TRANSPORTER_ID, "load:1234", BookingConstants.POST_LOAD_ID,
//						(long) 20, BookingData.Unit.PER_TON,
//						Arrays.asList("truck:5ce284b9-d55a-4d3d-82fd-b7ce8bb074af"), true, false, null, null),
//
//				new BookingData("id:3", "transporter:123", BookingConstants.LOAD_ID, BookingConstants.POST_LOAD_ID,
//						(long) 20, BookingData.Unit.PER_TON,
//						Arrays.asList("truck:5ce284b9-d55a-4d3d-82fd-b7ce8bb074af"), true, false, null, null),
//
//				new BookingData("id:4", "transporter:1234", "load:123", BookingConstants.POST_LOAD_ID, (long) 20,
//						BookingData.Unit.PER_TON, Arrays.asList("truck:5ce284b9-d55a-4d3d-82fd-b7ce8bb074af"), false,
//						true, null, null),
//
//				new BookingData("id:5", "transporter:647", BookingConstants.LOAD_ID, BookingConstants.POST_LOAD_ID,
//						(long) 20, BookingData.Unit.PER_TON,
//						Arrays.asList("truck:5ce284b9-d55a-4d3d-82fd-b7ce8bb074af"), false, true, null, null),
//
//				new BookingData("id:6", BookingConstants.TRANSPORTER_ID, "load:abc", BookingConstants.POST_LOAD_ID,
//						(long) 20, BookingData.Unit.PER_TON, Arrays.asList("truck:5ce284b9-d55a-4d"), true, false, null,
//						null)
//
//		);
//
//		return bookingList;
//	}
//}
