package com.BookingBidding.Bidding;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import com.BookingBidding.Bidding.ErrorConstants.Constants;
import com.BookingBidding.Bidding.Model.BidPostRequest;
import com.BookingBidding.Bidding.Model.BidPutRequest;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import sharedEntity.BiddingData;
import io.restassured.path.json.JsonPath;

@TestMethodOrder(OrderAnnotation.class)
public class ApiTestRestAssured {

	private static String id1;
	private static String id2;

	private static long pageNo_TransporterId = 0;
	private static long cnt_TransporterId = 0;

	private static long pageNo_LoadId = 0;
	private static long cnt_LoadId = 0;

	private static long pageNo_transporterId_LoadId = 0;
	private static long cnt_TransporterId_LoadId = 0;

	@BeforeAll
	public static void setup() throws Exception {
		RestAssured.baseURI = Constants.BASE_URI;

		Response response2;
		pageNo_TransporterId = 0;
		cnt_TransporterId = 0;
		while (true) {
			response2 = RestAssured.given().param("pageNo", pageNo_TransporterId)
					.param("transporterId", "transporterId:123").header("accept", "application/json")
					.header("Content-Type", "application/json").get().then().extract().response();

			cnt_TransporterId += response2.jsonPath().getList("$").size();
			if (response2.jsonPath().getList("$").size() != Constants.pageSize)
				break;

			pageNo_TransporterId++;

		}

		Response response3;
		pageNo_LoadId = 0;
		cnt_LoadId = 0;
		while (true) {
			response3 = RestAssured.given().param("pageNo", pageNo_LoadId).param("loadId", "load:123")
					.header("accept", "application/json").header("Content-Type", "application/json").get().then()
					.extract().response();

			cnt_LoadId += response3.jsonPath().getList("$").size();
			if (response3.jsonPath().getList("$").size() != Constants.pageSize)
				break;

			pageNo_LoadId++;

		}

		Response response4;
		pageNo_transporterId_LoadId = 0;
		cnt_TransporterId_LoadId = 0;
		while (true) {
			response4 = RestAssured.given().param("pageNo", pageNo_transporterId_LoadId)
					.param("transporterId", "transporter:123").param("loadId", "load:123")
					.header("accept", "application/json").header("Content-Type", "application/json").get().then()
					.extract().response();

			cnt_TransporterId_LoadId += response4.jsonPath().getList("$").size();
			if (response4.jsonPath().getList("$").size() != Constants.pageSize)
				break;

			pageNo_transporterId_LoadId++;

		}

		BidPostRequest bidPostRequest = new BidPostRequest("transporterId:123", "load:123", (long) 20,
				BiddingData.Unit.PER_TON, Arrays.asList("truck:123"), "11/12/2011");

		String inputJson = mapToJson(bidPostRequest);

		Response response = RestAssured.given().header("", "").body(inputJson).header("accept", "application/json")
				.header("Content-Type", "application/json").post().then().extract().response();

		BidPostRequest bidPostRequest1 = new BidPostRequest("transporterId:123", "load:1234", (long) 20,
				BiddingData.Unit.PER_TON, Arrays.asList("truck:123"), "11/12/2011");

		String inputJson1 = mapToJson(bidPostRequest1);

		Response response0 = RestAssured.given().header("", "").body(inputJson1).header("accept", "application/json")
				.header("Content-Type", "application/json").post().then().extract().response();

		assertEquals(201, response.statusCode());
		assertEquals(Constants.success, response.jsonPath().getString("status"));
		id1 = response.jsonPath().getString("bidId");
//		assertEquals(Constants.success, response.jsonPath().getString("status"));
		assertEquals("transporterId:123", response.jsonPath().getString("transporterId"));
		assertEquals("load:123", response.jsonPath().getString("loadId"));
		assertEquals("20", response.jsonPath().getString("currentBid"));
		assertEquals(String.valueOf(BiddingData.Unit.PER_TON), response.jsonPath().getString("unitValue"));
		assertEquals(String.valueOf(Arrays.asList("truck:123")), response.jsonPath().getString("truckId"));
		assertEquals("true", response.jsonPath().getString("transporterApproval"));
		assertEquals("false", response.jsonPath().getString("shipperApproval"));
		assertEquals("11/12/2011", response.jsonPath().getString("biddingDate"));

		assertEquals(201, response0.statusCode());
//		assertEquals(Constants.success, response0.jsonPath().getString("status"));
		id2 = response0.jsonPath().getString("bidId");
		assertEquals(Constants.success, response0.jsonPath().getString("status"));
		assertEquals("transporterId:123", response0.jsonPath().getString("transporterId"));
		assertEquals("load:1234", response0.jsonPath().getString("loadId"));
		assertEquals("20", response0.jsonPath().getString("currentBid"));
		assertEquals(String.valueOf(BiddingData.Unit.PER_TON), response0.jsonPath().getString("unitValue"));
		assertEquals(String.valueOf(Arrays.asList("truck:123")), response0.jsonPath().getString("truckId"));
		assertEquals("true", response0.jsonPath().getString("transporterApproval"));
		assertEquals("false", response0.jsonPath().getString("shipperApproval"));
		assertEquals("11/12/2011", response0.jsonPath().getString("biddingDate"));

	}

	@Test
	@Order(1)
	public void addDataFailed_addDataFailed_invalidLoadId_null() throws Exception {

		BidPostRequest bidPostRequest = new BidPostRequest("transporterId:123", null, (long) 20,
				BiddingData.Unit.PER_TON, Arrays.asList("truck:123"), null);

		String inputJson = mapToJson(bidPostRequest);

		Response response = RestAssured.given().header("", "").body(inputJson).header("accept", "application/json")
				.header("Content-Type", "application/json").post().then().extract().response();

		assertEquals(400, response.statusCode());
		JsonPath jsonPathEvaluator = response.jsonPath();
		assertEquals("Validation error", jsonPathEvaluator.get("apierror.message").toString());

	}

	@Test
	@Order(1)
	public void addDataFailed_addDataFailed_invalidTransporterId_null() throws Exception {

		BidPostRequest bidPostRequest = new BidPostRequest(null, "load:12345", (long) 20, BiddingData.Unit.PER_TON,
				Arrays.asList("truck:123"), null);

		String inputJson = mapToJson(bidPostRequest);

		Response response = RestAssured.given().header("", "").body(inputJson).header("accept", "application/json")
				.header("Content-Type", "application/json").post().then().extract().response();

		assertEquals(400, response.statusCode());
		JsonPath jsonPathEvaluator = response.jsonPath();
		assertEquals("Validation error", jsonPathEvaluator.get("apierror.message").toString());

	}

	@Test
	@Order(2)
	public void addDataFailed_LoadIdAndTransporterIdExists() throws Exception {

		BidPostRequest bidPostRequest = new BidPostRequest("transporterId:123", "load:123", (long) 23,
				BiddingData.Unit.PER_TON, Arrays.asList("truck:123"), null);
		String inputJson = mapToJson(bidPostRequest);

		Response response = RestAssured.given().header("", "").body(inputJson).header("accept", "application/json")
				.header("Content-Type", "application/json").post().then().extract().response();

		assertEquals(409, response.statusCode());
		JsonPath jsonPathEvaluator = response.jsonPath();
		assertEquals("You have already bid on thid load !", jsonPathEvaluator.get("apierror.message").toString());

	}

	@Test
	@Order(3)
	public void addDataFailed_invalidRate_null() throws Exception {
		BidPostRequest bidPostRequest = new BidPostRequest("transporterId:0de885e0-5f43-4c68-8dde-b0f9ff81cb69",
				"load:1345", null, BiddingData.Unit.PER_TON, Arrays.asList("truck:123"), null);

		String inputJson = mapToJson(bidPostRequest);

		Response response = RestAssured.given().header("", "").body(inputJson).header("accept", "application/json")
				.header("Content-Type", "application/json").post().then().extract().response();

		assertEquals(400, response.statusCode());
		JsonPath jsonPathEvaluator = response.jsonPath();
		assertEquals("Validation error", jsonPathEvaluator.get("apierror.message").toString());

	}

	@Test
	@Order(4)
	public void addDataFailed_invalidUnitValue_null() throws Exception {
		BidPostRequest bidPostRequest = new BidPostRequest("transporterId:0de885e0-5f43-4c68-8dde-b0f9ff81cb69",
				"load:1345", (long) 23, null, Arrays.asList("truck:123"), null);
		String inputJson = mapToJson(bidPostRequest);

		Response response = RestAssured.given().header("", "").body(inputJson).header("accept", "application/json")
				.header("Content-Type", "application/json").post().then().extract().response();

		assertEquals(422, response.statusCode());
		JsonPath jsonPathEvaluator = response.jsonPath();
		assertEquals("Error: Failed: Cannot provide unknown unitValue", jsonPathEvaluator.get("apierror.message").toString());

	}

	@Test
	@Order(5)
	public void getBiddingDataWithIdSuccess() throws Exception {

		Response response = RestAssured.given().header("", "").header("accept", "application/json")
				.header("Content-Type", "application/json").get("/" + id1).then().extract().response();

		assertEquals(200, response.statusCode());
		assertEquals("transporterId:123", response.jsonPath().getString("transporterId"));

		assertEquals("load:123", response.jsonPath().getString("loadId"));
		assertEquals(20, Long.parseLong(response.jsonPath().getString("currentBid")));
		assertEquals(String.valueOf(BiddingData.Unit.PER_TON), response.jsonPath().getString("unitValue"));
		assertEquals(String.valueOf(Arrays.asList("truck:123")), response.jsonPath().getString("truckId"));
		assertEquals(true, Boolean.parseBoolean(response.jsonPath().getString("transporterApproval")));
		assertEquals(false, Boolean.parseBoolean(response.jsonPath().getString("shipperApproval")));
		assertEquals("11/12/2011", response.jsonPath().getString("biddingDate"));

	}

	@Test
	@Order(6)
	public void getBiddingDataWithIdFailed() throws Exception {

		Response response = RestAssured.given().header("", "").header("accept", "application/json")
				.header("Content-Type", "application/json").get("/bid:7089970").then().extract().response();

		assertTrue(response.asString().contains("NOT_FOUND"));
	}

	@Test
	@Order(7)
	public void getBiddingDataWithParam_transporterId() throws Exception {

		long lastPageCount = cnt_TransporterId % Constants.pageSize;
		long page = pageNo_TransporterId;

		if (lastPageCount >= Constants.pageSize - 1)
			page++;

		Response response = RestAssured.given().param("pageNo", page).param("transporterId", "transporterId:123")
				.header("accept", "application/json").header("Content-Type", "application/json").get().then().extract()
				.response();

		assertEquals(200, response.statusCode());

		if (lastPageCount <= Constants.pageSize - 2) {

			assertEquals(lastPageCount + 2, response.jsonPath().getList("$").size());

		} else if (lastPageCount == Constants.pageSize - 1) {
			assertEquals(1, response.jsonPath().getList("$").size());
		} else if (lastPageCount == Constants.pageSize) {
			assertEquals(2, response.jsonPath().getList("$").size());
		}

	}

	@Test
	@Order(8)
	public void getBiddingDataWithParam_LoadId() throws Exception {

		long lastPageCount = cnt_LoadId % Constants.pageSize;
		long page = pageNo_LoadId;

		if (lastPageCount >= Constants.pageSize)
			page++;

		Response response = RestAssured.given().param("pageNo", page).param("loadId", "load:123")
				.header("accept", "application/json").header("Content-Type", "application/json").get().then().extract()
				.response();

		assertEquals(200, response.statusCode());

		if (lastPageCount <= Constants.pageSize - 1) {

			assertEquals(lastPageCount + 1, response.jsonPath().getList("$").size());

		} else if (lastPageCount == Constants.pageSize) {
			assertEquals(1, response.jsonPath().getList("$").size());
		}

	}

	@Test
	@Order(9)
	public void getBiddingDataWithParam_transporterId_LoadId() throws Exception {

		long lastPageCount = cnt_TransporterId_LoadId % Constants.pageSize;
		long page = pageNo_transporterId_LoadId;

		if (lastPageCount >= Constants.pageSize)
			page++;

		Response response = RestAssured.given().param("pageNo", page).param("transporterId", "transporterId:123")
				.param("loadId", "load:123").header("accept", "application/json")
				.header("Content-Type", "application/json").get().then().extract().response();

		assertEquals(200, response.statusCode());

		if (lastPageCount <= Constants.pageSize - 1) {

			assertEquals(lastPageCount + 1, response.jsonPath().getList("$").size());

		} else if (lastPageCount == Constants.pageSize) {
			assertEquals(1, response.jsonPath().getList("$").size());
		}

	}

	@Test
	@Order(10)
	public void updateFailed_Unit_null() throws Exception {

		BidPutRequest bidPutRequest = new BidPutRequest((long) 1000, null, null, null, true, null, null);

		String inputJson = mapToJson(bidPutRequest);

		Response response = RestAssured.given().header("", "").body(inputJson).header("accept", "application/json")
				.header("Content-Type", "application/json").put("/" + id1).then().extract().response();

		assertEquals(422, response.statusCode());
		assertTrue(response.asString().contains(Constants.unitValueisNull));

	}

	@Test
	@Order(11)
	public void updateFailed_Transporter_Approval_Shipper_Approval_both_null() throws Exception {
		BidPutRequest bidPutRequest = new BidPutRequest((long) 111, null, BiddingData.Unit.PER_TRUCK, null, null, null, null);

		String inputJson = mapToJson(bidPutRequest);

		Response response = RestAssured.given().header("", "").body(inputJson).header("accept", "application/json")
				.header("Content-Type", "application/json").put("/" + id1).then().extract().response();

		assertEquals(422, response.statusCode());
		assertTrue(response.asString().contains(Constants.TRANSPORTER_SHIPPER_APPROVAL_NULL));

	}

	@Test
	@Order(12)
	public void updateFailed_Shipper_Cannot_Update_TruckID() throws Exception {

		BidPutRequest bidPutRequest = new BidPutRequest((long) 1000, null, BiddingData.Unit.PER_TRUCK,
				Arrays.asList("truckID:abcd"), null, true, null);

		String inputJson = mapToJson(bidPutRequest);

		Response response = RestAssured.given().header("", "").body(inputJson).header("accept", "application/json")
				.header("Content-Type", "application/json").put("/" + id1).then().extract().response();

		assertEquals(422, response.statusCode());
		assertTrue(response.asString().contains(Constants.TRUCK_ID_UPDATE_BY_SHIPPER));

	}

	@Test
	@Order(13)
	public void updateFailed_Transporter_Approval_Shipper_Approval_both_notNull() throws Exception {

		BidPutRequest bidPutRequest = new BidPutRequest((long) 1000, null, BiddingData.Unit.PER_TRUCK,
				Arrays.asList("truckID:abcd"), true, true, null);

		String inputJson = mapToJson(bidPutRequest);

		Response response = RestAssured.given().header("", "").body(inputJson).header("accept", "application/json")
				.header("Content-Type", "application/json").put("/" + id1).then().extract().response();

		assertEquals(422, response.statusCode());
		assertTrue(response.asString().contains(Constants.TRANSPORTER_SHIPPER_APPROVAL_NOT_NULL));

	}

	@Test
	@Order(14)
	public void updateFailed_Account_Not_Found() throws Exception {

		BidPutRequest bidPutRequest = new BidPutRequest((long) 1000, null, BiddingData.Unit.PER_TRUCK,
				Arrays.asList("truckID:abcd"), true, null, null);

		String inputJson = mapToJson(bidPutRequest);

		Response response = RestAssured.given().header("", "").body(inputJson).header("accept", "application/json")
				.header("Content-Type", "application/json").put("/" + "bid:abcd").then().extract().response();

		assertEquals(404, response.statusCode());
		JsonPath jsonPathEvaluator = response.jsonPath();
		assertEquals("BiddingData was not found for parameters {bidId=bid:abcd}", jsonPathEvaluator.get("apierror.message").toString());

	}

	@Test
	@Order(15)
	public void updateDataSuccess_Transporter() throws Exception {

		BidPutRequest bidPutRequest = new BidPutRequest((long) 1000, null, BiddingData.Unit.PER_TRUCK,
				Arrays.asList("truckID:abcdef"), true, null, null);

		String inputJson = mapToJson(bidPutRequest);

		Response response = RestAssured.given().header("", "").body(inputJson).header("accept", "application/json")
				.header("Content-Type", "application/json").put("/" + id1).then().extract().response();

		assertEquals(200, response.statusCode());
		assertEquals(Constants.uSuccess, response.jsonPath().getString("status"));
		assertEquals(1000, Long.parseLong(response.jsonPath().getString("currentBid")));
		assertEquals(String.valueOf(BiddingData.Unit.PER_TRUCK), response.jsonPath().getString("unitValue"));
		assertEquals(String.valueOf(Arrays.asList("truckID:abcdef")), (response.jsonPath().getString("truckId")));
		assertEquals("true", (response.jsonPath().getString("transporterApproval")));
		assertEquals("false", (response.jsonPath().getString("shipperApproval")));
		assertEquals("11/12/2011", response.jsonPath().getString("biddingDate"));

		System.err.println((response.jsonPath().getString("truckId")));

	}

	@Test
	@Order(16)
	public void updateDataSuccess_Shipper() throws Exception {

		BidPutRequest bidPutRequest = new BidPutRequest((long) 1500, null, BiddingData.Unit.PER_TRUCK, null, null, true,
				"22/04/2021");

		String inputJson = mapToJson(bidPutRequest);

		Response response = RestAssured.given().header("", "").body(inputJson).header("accept", "application/json")
				.header("Content-Type", "application/json").put("/" + id1).then().extract().response();

		assertEquals(200, response.statusCode());
		assertEquals(Constants.uSuccess, response.jsonPath().getString("status"));
		assertEquals(1500, Long.parseLong(response.jsonPath().getString("currentBid")));
		assertEquals(String.valueOf(BiddingData.Unit.PER_TRUCK), response.jsonPath().getString("unitValue"));
		assertEquals(String.valueOf(Arrays.asList("truck:123")), (response.jsonPath().getString("truckId")));
		assertEquals("false", (response.jsonPath().getString("transporterApproval")));
		assertEquals("true", (response.jsonPath().getString("shipperApproval")));
		assertEquals("22/04/2021", response.jsonPath().getString("biddingDate"));
	}

	@Test
	@Order(17)
	public void updateDataSuccess_Bid_Accepted_Transporter() throws Exception {

		BidPutRequest bidPutRequest = new BidPutRequest((long)1500, null, BiddingData.Unit.PER_TRUCK, null, true, null, "22/04/2021");

		String inputJson = mapToJson(bidPutRequest);

		Response response = RestAssured.given().header("", "").body(inputJson).header("accept", "application/json")
				.header("Content-Type", "application/json").put("/" + id1).then().extract().response();

		assertEquals(200, response.statusCode());
		assertEquals(Constants.uSuccess, response.jsonPath().getString("status"));
		assertEquals(1500, Long.parseLong(response.jsonPath().getString("currentBid")));
		assertEquals(String.valueOf(BiddingData.Unit.PER_TRUCK), response.jsonPath().getString("unitValue"));
		assertEquals(String.valueOf(Arrays.asList("truck:123")), (response.jsonPath().getString("truckId")));
		assertEquals("true", (response.jsonPath().getString("transporterApproval")));
		assertEquals("false", (response.jsonPath().getString("shipperApproval")));
		assertEquals("22/04/2021", response.jsonPath().getString("biddingDate"));
		assertEquals("load:123", response.jsonPath().getString("loadId"));
		assertEquals("transporterId:123", response.jsonPath().getString("transporterId"));

	}

	@Test
	@Order(18)
	public void updateDataSuccess_Bid_Accepted_Shipper() throws Exception {

		BidPutRequest bidPutRequest = new BidPutRequest((long)20, null, BiddingData.Unit.PER_TON, null, null, true, "11/12/2011");

		String inputJson = mapToJson(bidPutRequest);

		Response response = RestAssured.given().header("", "").body(inputJson).header("accept", "application/json")
				.header("Content-Type", "application/json").put("/" + id2).then().extract().response();

		assertEquals(200, response.statusCode());
		assertEquals(Constants.uSuccess, response.jsonPath().getString("status"));
		assertEquals(20, Long.parseLong(response.jsonPath().getString("currentBid")));
		assertEquals(String.valueOf(BiddingData.Unit.PER_TON), response.jsonPath().getString("unitValue"));
		assertEquals(String.valueOf(Arrays.asList("truck:123")), (response.jsonPath().getString("truckId")));
		assertEquals("false", (response.jsonPath().getString("transporterApproval")));
		assertEquals("true", (response.jsonPath().getString("shipperApproval")));
		assertEquals("11/12/2011", response.jsonPath().getString("biddingDate"));

		assertEquals("load:1234", response.jsonPath().getString("loadId"));
		assertEquals("transporterId:123", response.jsonPath().getString("transporterId"));

	}

	@Test
	@Order(19)
	public void updateDataFailed() throws Exception {

		BidPutRequest bidPutRequest = new BidPutRequest((long) 1000, null, BiddingData.Unit.PER_TRUCK, null, null, null,
				null);

		String inputJson = mapToJson(bidPutRequest);

		Response response = RestAssured.given().header("", "").body(inputJson).header("accept", "application/json")
				.header("Content-Type", "application/json").put("/bid:132536").then().extract().response();

		assertEquals(404, response.statusCode());
		JsonPath jsonPathEvaluator = response.jsonPath();
		assertEquals("BiddingData was not found for parameters {bidId=bid:132536}", jsonPathEvaluator.get("apierror.message").toString());
		
	}

	@Test
	@Order(20)
	public void deleteDataFailed() throws Exception {

		Response response = RestAssured.given().header("", "").delete("/" + "bid:abcd").then().extract().response();

		assertEquals(404, response.statusCode());
		JsonPath jsonPathEvaluator = response.jsonPath();
		assertEquals("BiddingData was not found for parameters {bidId=bid:abcd}", jsonPathEvaluator.get("apierror.message").toString());

	}

	@AfterAll
	public static void deleteData() throws Exception {

		Response response = RestAssured.given().header("", "").delete("/" + id1).then().extract().response();

		Response response1 = RestAssured.given().header("", "").delete("/" + id2).then().extract().response();

		assertEquals(200, response.statusCode());
		assertEquals(Constants.dSuccess, response.jsonPath().getString("status"));

		assertEquals(200, response1.statusCode());
		assertEquals(Constants.dSuccess, response1.jsonPath().getString("status"));

	}

	private static String mapToJson(Object object) throws Exception {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.configure(DeserializationFeature.FAIL_ON_NUMBERS_FOR_ENUMS, false);
		objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
		objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT, true);
		objectMapper.configure(DeserializationFeature.READ_ENUMS_USING_TO_STRING, true);

		return objectMapper.writeValueAsString(object);
	}

}
