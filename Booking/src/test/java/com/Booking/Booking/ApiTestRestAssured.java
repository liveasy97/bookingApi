//package com.Booking.Booking;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//import java.util.Arrays;
//
//import org.junit.jupiter.api.AfterAll;
//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
//import org.junit.jupiter.api.Order;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.TestMethodOrder;
//
//import com.Booking.Booking.Constants.BookingConstants;
//import com.Booking.Booking.Entities.BookingData;
//import com.Booking.Booking.Model.BookingPostRequest;
//import com.Booking.Booking.Model.BookingPutRequest;
//import com.fasterxml.jackson.databind.DeserializationFeature;
//import com.fasterxml.jackson.databind.ObjectMapper;
//
//import io.restassured.RestAssured;
//import io.restassured.response.Response;
//
//@TestMethodOrder(OrderAnnotation.class)
//public class ApiTestRestAssured {
//
//	private static String id1;
//	private static String id2;
//
//	private static long pageNo_Cancel_False_Completed_False_TransporterId = 0;
//	private static long cnt_Cancel_False_Completed_False_TransporterId = 0;
//
//	private static long pageNo_Cancel_True_Completed_False_TransporterId = 0;
//	private static long cnt_Cancel_True_Completed_False_TransporterId = 0;
//
//	private static long pageNo_Cancel_False_Completed_False_PostLoadId = 0;
//	private static long cnt_Cancel_False_Completed_False_PostLoadId = 0;
//
//	private static long pageNo_Cancel_True_Completed_False_PostLoadId = 0;
//	private static long cnt_Cancel_True_Completed_False_PostLoadId = 0;
//
//	private static long pageNo_Cancel_Completed = 0;
//	private static long cnt_Cancel_Completed = 0;
//
//	@BeforeAll
//	public static void setup() throws Exception {
//		RestAssured.baseURI = BookingConstants.BASE_URI;
//
//		Response response2;
//		pageNo_Cancel_False_Completed_False_TransporterId = 0;
//		cnt_Cancel_False_Completed_False_TransporterId = 0;
//		while (true) {
//			response2 = RestAssured.given().param("pageNo", pageNo_Cancel_False_Completed_False_TransporterId)
//					.param("transporterId", "transporterId:123").param("cancel", false).param("completed", false)
//					.header("accept", "application/json").header("Content-Type", "application/json").get().then()
//					.extract().response();
//
//			cnt_Cancel_False_Completed_False_TransporterId += response2.jsonPath().getList("$").size();
//			if (response2.jsonPath().getList("$").size() != BookingConstants.pageSize)
//				break;
//
//			pageNo_Cancel_False_Completed_False_TransporterId++;
//
//		}
//
//		Response response3;
//		pageNo_Cancel_False_Completed_False_PostLoadId = 0;
//		cnt_Cancel_False_Completed_False_PostLoadId = 0;
//		while (true) {
//			response3 = RestAssured.given().param("pageNo", pageNo_Cancel_False_Completed_False_PostLoadId)
//					.param("postLoadId", "shipper:123").param("cancel", false).param("completed", false)
//					.header("accept", "application/json").header("Content-Type", "application/json").get().then()
//					.extract().response();
//
//			cnt_Cancel_False_Completed_False_PostLoadId += response3.jsonPath().getList("$").size();
//			if (response3.jsonPath().getList("$").size() != BookingConstants.pageSize)
//				break;
//
//			pageNo_Cancel_False_Completed_False_PostLoadId++;
//
//		}
//
//		Response response4;
//		pageNo_Cancel_True_Completed_False_TransporterId = 0;
//		cnt_Cancel_True_Completed_False_TransporterId = 0;
//		while (true) {
//			response4 = RestAssured.given().param("pageNo", pageNo_Cancel_True_Completed_False_TransporterId)
//					.param("transporterId", "transporterId:123").param("cancel", true).param("completed", false)
//					.header("accept", "application/json").header("Content-Type", "application/json").get().then()
//					.extract().response();
//
//			cnt_Cancel_True_Completed_False_TransporterId += response4.jsonPath().getList("$").size();
//			if (response4.jsonPath().getList("$").size() != BookingConstants.pageSize)
//				break;
//
//			pageNo_Cancel_True_Completed_False_TransporterId++;
//
//		}
//
//		Response response5;
//		pageNo_Cancel_True_Completed_False_PostLoadId = 0;
//		cnt_Cancel_True_Completed_False_PostLoadId = 0;
//		while (true) {
//			response5 = RestAssured.given().param("pageNo", pageNo_Cancel_True_Completed_False_PostLoadId)
//					.param("postLoadId", "shipper:123").param("cancel", true).param("completed", false)
//					.header("accept", "application/json").header("Content-Type", "application/json").get().then()
//					.extract().response();
//
//			cnt_Cancel_True_Completed_False_PostLoadId += response5.jsonPath().getList("$").size();
//			if (response5.jsonPath().getList("$").size() != BookingConstants.pageSize)
//				break;
//
//			pageNo_Cancel_True_Completed_False_PostLoadId++;
//
//		}
//
//		Response response6;
//		pageNo_Cancel_Completed = 0;
//		cnt_Cancel_Completed = 0;
//		while (true) {
//			response6 = RestAssured.given().param("pageNo", pageNo_Cancel_Completed).param("cancel", true)
//					.param("completed", false).header("accept", "application/json")
//					.header("Content-Type", "application/json").get().then().extract().response();
//
//			cnt_Cancel_Completed += response6.jsonPath().getList("$").size();
//			if (response6.jsonPath().getList("$").size() != BookingConstants.pageSize)
//				break;
//
//			pageNo_Cancel_Completed++;
//
//		}
//
//		BookingPostRequest bookingPostRequest = new BookingPostRequest("transporterId:123", "load:123", "shipper:123",
//				(long) 20, BookingData.Unit.PER_TON, Arrays.asList("truck:123"), "12/11/2020");
//
//		String inputJson = mapToJson(bookingPostRequest);
//
//		Response response = RestAssured.given().header("", "").body(inputJson).header("accept", "application/json")
//				.header("Content-Type", "application/json").post().then().extract().response();
//
//		BookingPostRequest bookingPostRequest1 = new BookingPostRequest("transporterId:123", "load:1234", "shipper:123",
//				(long) 20, BookingData.Unit.PER_TON, Arrays.asList("truck:123"), "12/11/2020");
//
//		String inputJson1 = mapToJson(bookingPostRequest1);
//
//		Response response0 = RestAssured.given().header("", "").body(inputJson1).header("accept", "application/json")
//				.header("Content-Type", "application/json").post().then().extract().response();
//
//		assertEquals(200, response.statusCode());
//		assertEquals(BookingConstants.success, response.jsonPath().getString("status"));
//		id1 = response.jsonPath().getString("bookingId");
//
//		assertEquals(200, response0.statusCode());
//		assertEquals(BookingConstants.success, response0.jsonPath().getString("status"));
//		id2 = response0.jsonPath().getString("bookingId");
//
//	}
//
//	@Test
//	@Order(1)
//	public void addDataFailed_invalidLoadId_null() throws Exception {
//
//		BookingPostRequest bookingPostRequest = new BookingPostRequest("transporterId:123", null, "shipper:123",
//				(long) 20, BookingData.Unit.PER_TON, Arrays.asList("truck:5ce284b9-d55a-4d3d-82fd-b7ce8bb074af"),
//				"12/11/2020");
//
//		String inputJson = mapToJson(bookingPostRequest);
//
//		Response response = RestAssured.given().header("", "").body(inputJson).header("accept", "application/json")
//				.header("Content-Type", "application/json").post().then().extract().response();
//
//		assertEquals(200, response.statusCode());
//		assertEquals(BookingConstants.pLoadIdIsNull, response.jsonPath().getString("status"));
//
//	}
//
//	@Test
//	@Order(2)
//	public void addDataFailed_invalidUnitValue_null() throws Exception {
//
//		BookingPostRequest bookingPostRequest = new BookingPostRequest("transporterId:123", "load:123", "shipper:123",
//				(long) 20, null, Arrays.asList("truck:5ce284b9-d55a-4d3d-82fd-b7ce8bb074af"), "12/11/2020");
//
//		String inputJson = mapToJson(bookingPostRequest);
//
//		Response response = RestAssured.given().header("", "").body(inputJson).header("accept", "application/json")
//				.header("Content-Type", "application/json").post().then().extract().response();
//
//		assertEquals(200, response.statusCode());
//		assertEquals(BookingConstants.pUnitIsNull, response.jsonPath().getString("status"));
//
//	}
//
//	@Test
//	@Order(3)
//	public void addDataFailed_invalidTransporterId_null() throws Exception {
//
//		BookingPostRequest bookingPostRequest = new BookingPostRequest(null, "load:123", "shipper:123", (long) 20,
//				BookingData.Unit.PER_TON, Arrays.asList("truck:5ce284b9-d55a-4d3d-82fd-b7ce8bb074af"), "12/11/2020");
//
//		String inputJson = mapToJson(bookingPostRequest);
//
//		Response response = RestAssured.given().header("", "").body(inputJson).header("accept", "application/json")
//				.header("Content-Type", "application/json").post().then().extract().response();
//
//		assertEquals(200, response.statusCode());
//		assertEquals(BookingConstants.pTransporterIdIsNull, response.jsonPath().getString("status"));
//
//	}
//
//	@Test
//	@Order(4)
//	public void addDataFailed_invalidPostLoadId_null() throws Exception {
//
//		BookingPostRequest bookingPostRequest = new BookingPostRequest("transporter:123", "load:123", null, (long) 20,
//				BookingData.Unit.PER_TON, Arrays.asList("truck:5ce284b9-d55a-4d3d-82fd-b7ce8bb074af"), "12/11/2020");
//
//		String inputJson = mapToJson(bookingPostRequest);
//
//		Response response = RestAssured.given().header("", "").body(inputJson).header("accept", "application/json")
//				.header("Content-Type", "application/json").post().then().extract().response();
//
//		assertEquals(200, response.statusCode());
//		assertEquals(BookingConstants.pPostLoadIdIsNull, response.jsonPath().getString("status"));
//
//	}
//
//	@Test
//	@Order(5)
//	public void addDataFailed_invalidTruckId_null() throws Exception {
//
//		BookingPostRequest bookingPostRequest = new BookingPostRequest("transporter:123", "load:123", "shipper:123",
//				(long) 20, BookingData.Unit.PER_TON, null, "12/11/2020");
//
//		String inputJson = mapToJson(bookingPostRequest);
//
//		Response response = RestAssured.given().header("", "").body(inputJson).header("accept", "application/json")
//				.header("Content-Type", "application/json").post().then().extract().response();
//
//		assertEquals(200, response.statusCode());
//		assertEquals(BookingConstants.pTruckIdIsNull, response.jsonPath().getString("status"));
//
//	}
//
//	@Test
//	@Order(6)
//	public void addDataFailed_LoadIdAndTransporterIdExists() throws Exception {
//
//		BookingPostRequest bookingPostRequest = new BookingPostRequest("transporterId:123", "load:123", "shipper:123",
//				(long) 20, BookingData.Unit.PER_TON, Arrays.asList("truck:5ce284b9-d55a-4d3d-82fd-b7ce8bb074af"),
//				"12/11/2020");
//
//		String inputJson = mapToJson(bookingPostRequest);
//
//		Response response = RestAssured.given().header("", "").body(inputJson).header("accept", "application/json")
//				.header("Content-Type", "application/json").post().then().extract().response();
//
//		assertEquals(200, response.statusCode());
//		assertEquals(BookingConstants.pDataExists, response.jsonPath().getString("status"));
//
//	}
//
//	@Test
//	@Order(7)
//	public void getBookingDataWithIdSuccess() throws Exception {
//
//		Response response = RestAssured.given().header("", "").header("accept", "application/json")
//				.header("Content-Type", "application/json").get("/" + id1).then().extract().response();
//
//		assertEquals(200, response.statusCode());
//		assertEquals("transporterId:123", response.jsonPath().getString("transporterId"));
//
//		assertEquals("load:123", response.jsonPath().getString("loadId"));
//		assertEquals(20, Long.parseLong(response.jsonPath().getString("rate")));
//		assertEquals(String.valueOf(BookingData.Unit.PER_TON), response.jsonPath().getString("unitValue"));
//		assertEquals(String.valueOf(Arrays.asList("truck:123")), response.jsonPath().getString("truckId"));
//		assertEquals(false, Boolean.parseBoolean(response.jsonPath().getString("cancel")));
//		assertEquals(false, Boolean.parseBoolean(response.jsonPath().getString("completed")));
//
//	}
//
//	@Test
//	@Order(8)
//	public void getBookingDataWithIdFailed() throws Exception {
//
//		Response response = RestAssured.given().header("", "").header("accept", "application/json")
//				.header("Content-Type", "application/json").get("/booking:7089970").then().extract().response();
//
//		assertEquals(response.asString(), "");
//	}
//
//	@Test
//	@Order(9)
//	public void getBookingDataWithParam_transporterId() throws Exception {
//
//		long lastPageCount = cnt_Cancel_False_Completed_False_TransporterId % BookingConstants.pageSize;
//		long page = pageNo_Cancel_False_Completed_False_TransporterId;
//
//		if (lastPageCount >= BookingConstants.pageSize - 1)
//			page++;
//
//		Response response = RestAssured.given().param("pageNo", page).param("transporterId", "transporterId:123")
//				.param("cancel", false).param("completed", false).header("accept", "application/json")
//				.header("Content-Type", "application/json").get().then().extract().response();
//
//		assertEquals(200, response.statusCode());
//
//		if (lastPageCount <= BookingConstants.pageSize - 2) {
//
//			assertEquals(lastPageCount + 2, response.jsonPath().getList("$").size());
//
//		} else if (lastPageCount == BookingConstants.pageSize - 1) {
//			assertEquals(1, response.jsonPath().getList("$").size());
//		} else if (lastPageCount == BookingConstants.pageSize) {
//			assertEquals(2, response.jsonPath().getList("$").size());
//		}
//
//	}
//
//	@Test
//	@Order(10)
//	public void getBookingDataWithParam_postLoadId() throws Exception {
//
//		long lastPageCount = cnt_Cancel_False_Completed_False_PostLoadId % BookingConstants.pageSize;
//		long page = pageNo_Cancel_False_Completed_False_PostLoadId;
//
//		if (lastPageCount >= BookingConstants.pageSize)
//			page++;
//
//		Response response = RestAssured.given().param("pageNo", page).param("postLoadId", "shipper:123")
//				.header("accept", "application/json").param("cancel", false).param("completed", false)
//				.header("Content-Type", "application/json").get().then().extract().response();
//
//		assertEquals(200, response.statusCode());
//
//		if (lastPageCount <= BookingConstants.pageSize - 2) {
//
//			assertEquals(lastPageCount + 2, response.jsonPath().getList("$").size());
//
//		} else if (lastPageCount == BookingConstants.pageSize - 1) {
//			assertEquals(1, response.jsonPath().getList("$").size());
//		} else if (lastPageCount == BookingConstants.pageSize) {
//			assertEquals(2, response.jsonPath().getList("$").size());
//		}
//
//	}
//
//	@Test
//	@Order(11)
//	public void updateSuccess_Cancel_True() throws Exception {
//
//		BookingPutRequest bookingPutRequest = new BookingPutRequest((long) 1000, BookingData.Unit.PER_TRUCK,
//				Arrays.asList("truck:abcdef"), true, null, null, null);
//
//		String inputJson = mapToJson(bookingPutRequest);
//
//		Response response = RestAssured.given().header("", "").body(inputJson).header("accept", "application/json")
//				.header("Content-Type", "application/json").put("/" + id1).then().extract().response();
//
//		assertEquals(200, response.statusCode());
//		assertEquals(BookingConstants.UPDATE_SUCCESS, response.jsonPath().getString("status"));
//		assertEquals(1000, Long.parseLong(response.jsonPath().getString("rate")));
//		assertEquals(String.valueOf(BookingData.Unit.PER_TRUCK), response.jsonPath().getString("unitValue"));
//		assertEquals(String.valueOf(Arrays.asList("truck:abcdef")), (response.jsonPath().getString("truckId")));
//		assertEquals("true", (response.jsonPath().getString("cancel")));
//		assertEquals("false", (response.jsonPath().getString("completed")));
//		assertEquals("transporterId:123", (response.jsonPath().getString("transporterId")));
//		assertEquals("shipper:123", (response.jsonPath().getString("postLoadId")));
//		assertEquals("load:123", (response.jsonPath().getString("loadId")));
//		assertEquals("12/11/2020", (response.jsonPath().getString("bookingDate")));
//		assertEquals(null, (response.jsonPath().getString("completedDate")));
//
//	}
//
//	@Test
//	@Order(11)
//	public void getBookingDataWithParam_Cancel_True_Completed_False() throws Exception {
//
//		long lastPageCount = cnt_Cancel_Completed % BookingConstants.pageSize;
//		long page = pageNo_Cancel_Completed;
//
//		if (lastPageCount >= BookingConstants.pageSize)
//			page++;
//
//		Response response = RestAssured.given().param("pageNo", page).param("cancel", true).param("completed", false)
//				.header("accept", "application/json").header("Content-Type", "application/json").get().then().extract()
//				.response();
//
//		assertEquals(200, response.statusCode());
//
//		if (lastPageCount <= BookingConstants.pageSize - 1) {
//			assertEquals(lastPageCount + 1, response.jsonPath().getList("$").size());
//		} else if (lastPageCount == BookingConstants.pageSize) {
//			assertEquals(1, response.jsonPath().getList("$").size());
//		}
//
//	}
//
//	@Test
//	@Order(12)
//	public void getBookingDataWithParam_Cancel_True_transporterId() throws Exception {
//
//		long lastPageCount = cnt_Cancel_True_Completed_False_TransporterId % BookingConstants.pageSize;
//		long page = pageNo_Cancel_True_Completed_False_TransporterId;
//
//		if (lastPageCount >= BookingConstants.pageSize)
//			page++;
//
//		Response response = RestAssured.given().param("pageNo", page).param("transporterId", "transporterId:123")
//				.param("cancel", true).param("completed", false).header("accept", "application/json")
//				.header("Content-Type", "application/json").get().then().extract().response();
//
//		assertEquals(200, response.statusCode());
//
//		if (lastPageCount <= BookingConstants.pageSize - 1) {
//
//			assertEquals(lastPageCount + 1, response.jsonPath().getList("$").size());
//
//		} else if (lastPageCount == BookingConstants.pageSize) {
//			assertEquals(1, response.jsonPath().getList("$").size());
//		}
//
//	}
//
//	@Test
//	@Order(13)
//	public void getBookingDataWithParam_Cancel_True_postLoadId() throws Exception {
//
//		long lastPageCount = cnt_Cancel_True_Completed_False_PostLoadId % BookingConstants.pageSize;
//		long page = pageNo_Cancel_True_Completed_False_PostLoadId;
//
//		if (lastPageCount >= BookingConstants.pageSize)
//			page++;
//
//		Response response = RestAssured.given().param("pageNo", page).param("postLoadId", "shipper:123")
//				.param("cancel", true).param("completed", false).header("accept", "application/json")
//				.header("Content-Type", "application/json").get().then().extract().response();
//
//		assertEquals(200, response.statusCode());
//
//		if (lastPageCount <= BookingConstants.pageSize - 1) {
//
//			assertEquals(lastPageCount + 1, response.jsonPath().getList("$").size());
//
//		} else if (lastPageCount == BookingConstants.pageSize) {
//			assertEquals(1, response.jsonPath().getList("$").size());
//		}
//
//	}
//
//	@Test
//	@Order(13)
//	public void getBookingDataWithParam_Failed_Completed_True_Cancel_null_postLoadId() throws Exception {
//
//		Response response = RestAssured.given().param("postLoadId", "shipper:123").param("completed", true)
//				.header("accept", "application/json").header("Content-Type", "application/json").get().then().extract()
//				.response();
//
//		assertEquals(200, response.statusCode());
//		assertEquals(0, response.jsonPath().getList("$").size());
//
//	}
//
//	@Test
//	@Order(13)
//	public void getBookingDataWithParam_Failed_Completed_null_Cancel_True_transporterId() throws Exception {
//
//		Response response = RestAssured.given().param("transporterId", "transporterId:123").param("cancel", true)
//				.header("accept", "application/json").header("Content-Type", "application/json").get().then().extract()
//				.response();
//
//		assertEquals(200, response.statusCode());
//		assertEquals(0, response.jsonPath().getList("$").size());
//
//	}
//
//	@Test
//	@Order(13)
//	public void getBookingDataWithParam_Failed_Completed_True_Cancel_null_transporterId() throws Exception {
//
//		Response response = RestAssured.given().param("transporterId", "transporterId:123").param("completed", true)
//				.header("accept", "application/json").header("Content-Type", "application/json").get().then().extract()
//				.response();
//
//		assertEquals(200, response.statusCode());
//		assertEquals(0, response.jsonPath().getList("$").size());
//
//	}
//
//	@Test
//	@Order(13)
//	public void getBookingDataWithParam_Failed_Completed_null_Cancel_True_postLoadId() throws Exception {
//
//		Response response = RestAssured.given().param("postLoadId", "shipper:123").param("cancel", true)
//				.header("accept", "application/json").header("Content-Type", "application/json").get().then().extract()
//				.response();
//
//		assertEquals(200, response.statusCode());
//		assertEquals(0, response.jsonPath().getList("$").size());
//
//	}
//
//	@Test
//	@Order(14)
//	public void updateSuccess_Completed_True() throws Exception {
//
//		BookingPutRequest bookingPutRequest = new BookingPutRequest((long) 1000, BookingData.Unit.PER_TRUCK,
//				Arrays.asList("truck:abcdef"), null, true, null, null);
//
//		String inputJson = mapToJson(bookingPutRequest);
//
//		Response response = RestAssured.given().header("", "").body(inputJson).header("accept", "application/json")
//				.header("Content-Type", "application/json").put("/" + id2).then().extract().response();
//
//		assertEquals(200, response.statusCode());
//		assertEquals(BookingConstants.UPDATE_SUCCESS, response.jsonPath().getString("status"));
//		assertEquals(1000, Long.parseLong(response.jsonPath().getString("rate")));
//		assertEquals(String.valueOf(BookingData.Unit.PER_TRUCK), response.jsonPath().getString("unitValue"));
//		assertEquals(String.valueOf(Arrays.asList("truck:abcdef")), (response.jsonPath().getString("truckId")));
//		assertEquals("true", (response.jsonPath().getString("completed")));
//		assertEquals("false", (response.jsonPath().getString("cancel")));
//		assertEquals("transporterId:123", (response.jsonPath().getString("transporterId")));
//		assertEquals("shipper:123", (response.jsonPath().getString("postLoadId")));
//		assertEquals("load:1234", (response.jsonPath().getString("loadId")));
//		assertEquals("12/11/2020", (response.jsonPath().getString("bookingDate")));
//		assertEquals(null, (response.jsonPath().getString("completedDate")));
//
//	}
//
//	@Test
//	@Order(15)
//	public void updateSuccess_Cancel_Completed_null() throws Exception {
//
//		BookingPutRequest bookingPutRequest = new BookingPutRequest((long) 1500, BookingData.Unit.PER_TRUCK,
//				Arrays.asList("truck:abcdef"), null, null, null, null);
//
//		String inputJson = mapToJson(bookingPutRequest);
//
//		Response response = RestAssured.given().header("", "").body(inputJson).header("accept", "application/json")
//				.header("Content-Type", "application/json").put("/" + id2).then().extract().response();
//
//		assertEquals(200, response.statusCode());
//		assertEquals(BookingConstants.UPDATE_SUCCESS, response.jsonPath().getString("status"));
//		assertEquals(1500, Long.parseLong(response.jsonPath().getString("rate")));
//		assertEquals(String.valueOf(BookingData.Unit.PER_TRUCK), response.jsonPath().getString("unitValue"));
//		assertEquals(String.valueOf(Arrays.asList("truck:abcdef")), (response.jsonPath().getString("truckId")));
//		assertEquals("true", (response.jsonPath().getString("completed")));
//		assertEquals("false", (response.jsonPath().getString("cancel")));
//		assertEquals("transporterId:123", (response.jsonPath().getString("transporterId")));
//		assertEquals("shipper:123", (response.jsonPath().getString("postLoadId")));
//		assertEquals("load:1234", (response.jsonPath().getString("loadId")));
//		assertEquals("12/11/2020", (response.jsonPath().getString("bookingDate")));
//		assertEquals(null, (response.jsonPath().getString("completedDate")));
//
//	}
//
//	@Test
//	@Order(16)
//	public void updateSuccess_Cancel_True_Completed_False() throws Exception {
//
//		BookingPutRequest bookingPutRequest = new BookingPutRequest(null, null, null, true, false, null, null);
//
//		String inputJson = mapToJson(bookingPutRequest);
//
//		Response response = RestAssured.given().header("", "").body(inputJson).header("accept", "application/json")
//				.header("Content-Type", "application/json").put("/" + id2).then().extract().response();
//
//		assertEquals(200, response.statusCode());
//		assertEquals(BookingConstants.UPDATE_SUCCESS, response.jsonPath().getString("status"));
//		assertEquals(1500, Long.parseLong(response.jsonPath().getString("rate")));
//		assertEquals(String.valueOf(BookingData.Unit.PER_TRUCK), response.jsonPath().getString("unitValue"));
//		assertEquals(String.valueOf(Arrays.asList("truck:abcdef")), (response.jsonPath().getString("truckId")));
//		assertEquals("false", (response.jsonPath().getString("completed")));
//		assertEquals("true", (response.jsonPath().getString("cancel")));
//		assertEquals("transporterId:123", (response.jsonPath().getString("transporterId")));
//		assertEquals("shipper:123", (response.jsonPath().getString("postLoadId")));
//		assertEquals("load:1234", (response.jsonPath().getString("loadId")));
//		assertEquals("12/11/2020", (response.jsonPath().getString("bookingDate")));
//		assertEquals(null, (response.jsonPath().getString("completedDate")));
//
//	}
//
//	@Test
//	@Order(17)
//	public void updateSuccess_Cancel_False_Completed_True() throws Exception {
//
//		BookingPutRequest bookingPutRequest = new BookingPutRequest(null, null, null, false, true, null, null);
//
//		String inputJson = mapToJson(bookingPutRequest);
//
//		Response response = RestAssured.given().header("", "").body(inputJson).header("accept", "application/json")
//				.header("Content-Type", "application/json").put("/" + id2).then().extract().response();
//
//		assertEquals(200, response.statusCode());
//		assertEquals(BookingConstants.UPDATE_SUCCESS, response.jsonPath().getString("status"));
//		assertEquals(1500, Long.parseLong(response.jsonPath().getString("rate")));
//		assertEquals(String.valueOf(BookingData.Unit.PER_TRUCK), response.jsonPath().getString("unitValue"));
//		assertEquals(String.valueOf(Arrays.asList("truck:abcdef")), (response.jsonPath().getString("truckId")));
//		assertEquals("true", (response.jsonPath().getString("completed")));
//		assertEquals("false", (response.jsonPath().getString("cancel")));
//		assertEquals("transporterId:123", (response.jsonPath().getString("transporterId")));
//		assertEquals("shipper:123", (response.jsonPath().getString("postLoadId")));
//		assertEquals("load:1234", (response.jsonPath().getString("loadId")));
//		assertEquals("12/11/2020", (response.jsonPath().getString("bookingDate")));
//		assertEquals(null, (response.jsonPath().getString("completedDate")));
//
//	}
//
//	@Test
//	@Order(18)
//	public void updateFailed_Cancel_True_Completed_True() throws Exception {
//
//		BookingPutRequest bookingPutRequest = new BookingPutRequest(null, null, null, true, true, null, null);
//
//		String inputJson = mapToJson(bookingPutRequest);
//
//		Response response = RestAssured.given().header("", "").body(inputJson).header("accept", "application/json")
//				.header("Content-Type", "application/json").put("/" + id2).then().extract().response();
//
//		assertEquals(200, response.statusCode());
//		assertEquals(BookingConstants.uCancelAndCompleteTrue, response.jsonPath().getString("status"));
//
//	}
//
//	@Test
//	@Order(19)
//	public void updateFailed_Cancel_True_When_Completed_True_in_Database() throws Exception {
//
//		BookingPutRequest bookingPutRequest = new BookingPutRequest(null, null, null, true, null, null, null);
//
//		String inputJson = mapToJson(bookingPutRequest);
//
//		Response response = RestAssured.given().header("", "").body(inputJson).header("accept", "application/json")
//				.header("Content-Type", "application/json").put("/" + id2).then().extract().response();
//
//		assertEquals(200, response.statusCode());
//		assertEquals(BookingConstants.uCanelIsTrueWhenCompleteIsTrue, response.jsonPath().getString("status"));
//
//	}
//
//	@Test
//	@Order(20)
//	public void updateFailed_Account_Not_Found() throws Exception {
//
//		BookingPutRequest bookingPutRequest = new BookingPutRequest((long) 123, BookingData.Unit.PER_TON, null, null,
//				null, null, null);
//
//		String inputJson = mapToJson(bookingPutRequest);
//
//		Response response = RestAssured.given().header("", "").body(inputJson).header("accept", "application/json")
//				.header("Content-Type", "application/json").put("/booking:avnf").then().extract().response();
//
//		assertEquals(200, response.statusCode());
//		assertEquals(BookingConstants.uDataNotFound, response.jsonPath().getString("status"));
//
//	}
//
//	@Test
//	@Order(21)
//	public void deleteDataFailed() throws Exception {
//
//		Response response = RestAssured.given().header("", "").delete("/" + "booking:abcd").then().extract().response();
//
//		assertEquals(200, response.statusCode());
//		assertEquals(BookingConstants.ACCOUNT_NOT_FOUND, response.jsonPath().getString("status"));
//
//	}
//
//	@AfterAll
//	public static void deleteData() throws Exception {
//
//		Response response = RestAssured.given().header("", "").delete("/" + id1).then().extract().response();
//
//		Response response1 = RestAssured.given().header("", "").delete("/" + id2).then().extract().response();
//
//		assertEquals(200, response.statusCode());
//		assertEquals(BookingConstants.DELETE_SUCCESS, response.jsonPath().getString("status"));
//
//		assertEquals(200, response1.statusCode());
//		assertEquals(BookingConstants.DELETE_SUCCESS, response1.jsonPath().getString("status"));
//
//	}
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
//}
