package com.Booking.Booking;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.Booking.Booking.Constants.BookingConstants;
import com.Booking.Booking.Dao.BookingDao;
import com.Booking.Booking.Entities.BookingData;
import com.Booking.Booking.Model.BookingDeleteResponse;
import com.Booking.Booking.Model.BookingPostRequest;
import com.Booking.Booking.Model.BookingPostResponse;
import com.Booking.Booking.Model.BookingPutRequest;
import com.Booking.Booking.Model.BookingPutResponse;
import com.Booking.Booking.Service.BookingServiceImpl;

@SpringBootTest
public class TestBookingService {

	@Autowired
	private BookingServiceImpl bookingService;

	@MockBean
	private BookingDao bookingDao;

	@Test
	public void addDataSuccess() {

		BookingPostRequest bookingPostRequest = new BookingPostRequest(BookingConstants.TRANSPORTER_ID,
				BookingConstants.LOAD_ID, BookingConstants.POST_LOAD_ID, (long) 20, BookingData.Unit.PER_TON,
				Arrays.asList("truck:5ce284b9-d55a-4d3d-82fd-b7ce8bb074af"), null);

		BookingPostResponse BookingPostResponse = new BookingPostResponse(BookingConstants.success, BookingConstants.ID,
				BookingConstants.TRANSPORTER_ID, BookingConstants.LOAD_ID, BookingConstants.POST_LOAD_ID, (long) 20,
				BookingData.Unit.PER_TON, Arrays.asList("truck:5ce284b9-d55a-4d3d-82fd-b7ce8bb074af"), false, false,
				null, null);

		List<BookingData> listbookingData = createBookingData();

		when(bookingDao.save(listbookingData.get(0))).thenReturn(listbookingData.get(0));

		assertEquals(BookingPostResponse.getStatus(), bookingService.addBooking(bookingPostRequest).getStatus());
		assertEquals(BookingPostResponse.getTransporterId(),
				bookingService.addBooking(bookingPostRequest).getTransporterId());
		assertEquals(BookingPostResponse.getLoadId(), bookingService.addBooking(bookingPostRequest).getLoadId());
		assertEquals(BookingPostResponse.getRate(), bookingService.addBooking(bookingPostRequest).getRate());
		assertEquals(BookingPostResponse.getUnitValue(), bookingService.addBooking(bookingPostRequest).getUnitValue());
		assertEquals(BookingPostResponse.getTruckId(), bookingService.addBooking(bookingPostRequest).getTruckId());
		assertEquals(BookingPostResponse.getCompleted(), bookingService.addBooking(bookingPostRequest).getCompleted());
		assertEquals(BookingPostResponse.getCancel(), bookingService.addBooking(bookingPostRequest).getCancel());
		assertEquals(BookingPostResponse.getCompletedDate(),
				bookingService.addBooking(bookingPostRequest).getCompletedDate());

	}

	@Test
	public void addDataFailed_invalidLoadId_null() {

		BookingPostRequest bookingPostRequest = new BookingPostRequest(BookingConstants.TRANSPORTER_ID, null,
				BookingConstants.POST_LOAD_ID, (long) 20, BookingData.Unit.PER_TON,
				Arrays.asList("truck:5ce284b9-d55a-4d3d-82fd-b7ce8bb074af"), null);

		List<BookingData> listbookingData = createBookingData();

		when(bookingDao.save(listbookingData.get(11))).thenReturn(listbookingData.get(11));

		BookingPostResponse response = new BookingPostResponse(BookingConstants.pLoadIdIsNull, null, null, null, null,
				null, null, null, null, null, null, null);

		assertEquals(response, bookingService.addBooking(bookingPostRequest));

	}

//	@Test
//	public void addDataFailed_invalidRate_null() {
//
//		BookingPostRequest bookingPostRequest = new BookingPostRequest(BookingConstants.TRANSPORTER_ID,
//				BookingConstants.LOAD_ID, BookingConstants.POST_LOAD_ID, null, BookingData.Unit.PER_TON,
//				Arrays.asList("truck:5ce284b9-d55a-4d3d-82fd-b7ce8bb074af"), null);
//
//		List<BookingData> listbookingData = createBookingData();
//
//		when(bookingDao.save(listbookingData.get(2))).thenReturn(listbookingData.get(2));
//
//		BookingPostResponse response = new BookingPostResponse(BookingConstants.pPostUnitRateIsNull, null, null, null,
//				null, null, null, null, null, null, null, null);
//
//		assertEquals(response, bookingService.addBooking(bookingPostRequest));
//	}

	@Test
	public void addDataFailed_invalidUnitValue_null() {
		BookingPostRequest bookingPostRequest = new BookingPostRequest(BookingConstants.TRANSPORTER_ID,
				BookingConstants.LOAD_ID, BookingConstants.POST_LOAD_ID, (long) 20, null,
				Arrays.asList("truck:5ce284b9-d55a-4d3d-82fd-b7ce8bb074af"), null);

		List<BookingData> listbookingData = createBookingData();

		when(bookingDao.save(listbookingData.get(7))).thenReturn(listbookingData.get(7));

		BookingPostResponse response = new BookingPostResponse(BookingConstants.pUnitIsNull, null, null, null, null,
				null, null, null, null, null, null, null);

		assertEquals(response, bookingService.addBooking(bookingPostRequest));
	}

	@Test
	public void addDataFailed_invalidTransporterId_null() {
		BookingPostRequest bookingPostRequest = new BookingPostRequest(null, BookingConstants.LOAD_ID,
				BookingConstants.POST_LOAD_ID, (long) 20, BookingData.Unit.PER_TON,
				Arrays.asList("truck:5ce284b9-d55a-4d3d-82fd-b7ce8bb074af"), null);

		List<BookingData> listbookingData = createBookingData();

		when(bookingDao.save(listbookingData.get(8))).thenReturn(listbookingData.get(8));

		BookingPostResponse response = new BookingPostResponse(BookingConstants.pTransporterIdIsNull, null, null, null,
				null, null, null, null, null, null, null, null);

		assertEquals(response, bookingService.addBooking(bookingPostRequest));
	}

	@Test
	public void addDataFailed_invalidPostLoadId_null() {
		BookingPostRequest bookingPostRequest = new BookingPostRequest(BookingConstants.TRANSPORTER_ID,
				BookingConstants.LOAD_ID, null, (long) 20, BookingData.Unit.PER_TON,
				Arrays.asList("truck:5ce284b9-d55a-4d3d-82fd-b7ce8bb074af"), null);

		List<BookingData> listbookingData = createBookingData();

		when(bookingDao.save(listbookingData.get(9))).thenReturn(listbookingData.get(9));

		BookingPostResponse response = new BookingPostResponse(BookingConstants.pPostLoadIdIsNull, null, null, null,
				null, null, null, null, null, null, null, null);

		assertEquals(response, bookingService.addBooking(bookingPostRequest));
	}

	@Test
	public void addDataFailed_invalidTruckId_null() {
		BookingPostRequest bookingPostRequest = new BookingPostRequest(BookingConstants.TRANSPORTER_ID,
				BookingConstants.LOAD_ID, BookingConstants.POST_LOAD_ID, (long) 20, BookingData.Unit.PER_TON, null,
				null);

		List<BookingData> listbookingData = createBookingData();

		when(bookingDao.save(listbookingData.get(10))).thenReturn(listbookingData.get(10));

		BookingPostResponse response = new BookingPostResponse(BookingConstants.pTruckIdIsNull, null, null, null, null,
				null, null, null, null, null, null, null);

		assertEquals(response, bookingService.addBooking(bookingPostRequest));
	}

	@Test
	public void getbookingDataWithIdSuccess() {

		List<BookingData> listbookingData = createBookingData();

		when(bookingDao.findByBookingId(BookingConstants.ID)).thenReturn(listbookingData.get(0));

		assertEquals(listbookingData.get(0), bookingService.getDataById(BookingConstants.ID));

	}

	@Test
	public void getbookingDataWithIdFailed() {

		String wrongbookingId = "booking:xyz";

		BookingData EmptyList = null;

		when(bookingDao.findByBookingId(wrongbookingId)).thenReturn(EmptyList);

		assertEquals(null, bookingService.getDataById(wrongbookingId));

	}

	@Test
	public void updateSuccess_Cancel_True() {

		List<BookingData> listbookingData = createBookingData();

		when(bookingDao.findByBookingId(BookingConstants.ID)).thenReturn(listbookingData.get(0));

		BookingPutRequest bookingPutRequest = new BookingPutRequest((long) 1000, BookingData.Unit.PER_TRUCK,
				Arrays.asList("truck:abcdef"), true, null, null, null);

		BookingPutResponse response = new BookingPutResponse(BookingConstants.UPDATE_SUCCESS, BookingConstants.ID,
				BookingConstants.TRANSPORTER_ID, BookingConstants.LOAD_ID, BookingConstants.POST_LOAD_ID, (long) 1000,
				BookingData.Unit.PER_TRUCK, Arrays.asList("truck:abcdef"), true, false, null, null);

		assertEquals(response, bookingService.updateBooking(BookingConstants.ID, bookingPutRequest));

	}

	@Test
	public void updateSuccess_Completed_True() {

		List<BookingData> listbookingData = createBookingData();

		when(bookingDao.findByBookingId(BookingConstants.ID)).thenReturn(listbookingData.get(0));

		BookingPutRequest bookingPutRequest = new BookingPutRequest((long) 1000, BookingData.Unit.PER_TRUCK,
				Arrays.asList("truck:abcdef"), null, true, null, null);

		BookingPutResponse response = new BookingPutResponse(BookingConstants.UPDATE_SUCCESS, BookingConstants.ID,
				BookingConstants.TRANSPORTER_ID, BookingConstants.LOAD_ID, BookingConstants.POST_LOAD_ID, (long) 1000,
				BookingData.Unit.PER_TRUCK, Arrays.asList("truck:abcdef"), false, true, null, null);

		assertEquals(response, bookingService.updateBooking(BookingConstants.ID, bookingPutRequest));

	}

	@Test
	public void updateSuccess_Cancel_Completed_null() {

		List<BookingData> listbookingData = createBookingData();

		when(bookingDao.findByBookingId(BookingConstants.ID)).thenReturn(listbookingData.get(0));

		BookingPutRequest bookingPutRequest = new BookingPutRequest((long) 1000, BookingData.Unit.PER_TRUCK,
				Arrays.asList("truck:abcdef"), null, null, null, null);

		BookingPutResponse response = new BookingPutResponse(BookingConstants.UPDATE_SUCCESS, BookingConstants.ID,
				BookingConstants.TRANSPORTER_ID, BookingConstants.LOAD_ID, BookingConstants.POST_LOAD_ID, (long) 1000,
				BookingData.Unit.PER_TRUCK, Arrays.asList("truck:abcdef"), false, false, null, null);

		System.err.println("res-------" + response + "\n");
		System.err.println(bookingService.updateBooking(BookingConstants.ID, bookingPutRequest));

		assertEquals(response, bookingService.updateBooking(BookingConstants.ID, bookingPutRequest));

	}

	@Test
	public void updateSuccess_Cancel_True_Completed_False() {

		List<BookingData> listbookingData = createBookingData();

		when(bookingDao.findByBookingId(BookingConstants.ID)).thenReturn((listbookingData.get(0)));

		BookingPutRequest bookingPutRequest = new BookingPutRequest((long) 1000, BookingData.Unit.PER_TRUCK,
				Arrays.asList("truck:abcdef"), true, false, null, null);

		BookingPutResponse response = new BookingPutResponse(BookingConstants.UPDATE_SUCCESS, BookingConstants.ID,
				BookingConstants.TRANSPORTER_ID, BookingConstants.LOAD_ID, BookingConstants.POST_LOAD_ID, (long) 1000,
				BookingData.Unit.PER_TRUCK, Arrays.asList("truck:abcdef"), true, false, null, null);

		assertEquals(response, bookingService.updateBooking(BookingConstants.ID, bookingPutRequest));

	}

	@Test
	public void updateSuccess_Cancel_False_Completed_True() {

		List<BookingData> listbookingData = createBookingData();

		when(bookingDao.findByBookingId(BookingConstants.ID)).thenReturn((listbookingData.get(0)));

		BookingPutRequest bookingPutRequest = new BookingPutRequest((long) 1000, BookingData.Unit.PER_TRUCK,
				Arrays.asList("truck:abcdef"), false, true, null, null);

		BookingPutResponse response = new BookingPutResponse(BookingConstants.UPDATE_SUCCESS, BookingConstants.ID,
				BookingConstants.TRANSPORTER_ID, BookingConstants.LOAD_ID, BookingConstants.POST_LOAD_ID, (long) 1000,
				BookingData.Unit.PER_TRUCK, Arrays.asList("truck:abcdef"), false, true, null, null);

		assertEquals(response, bookingService.updateBooking(BookingConstants.ID, bookingPutRequest));

	}

	@Test
	public void updateFailed_Cancel_Completed_True() {

		List<BookingData> listbookingData = createBookingData();

		when(bookingDao.findByBookingId(BookingConstants.ID)).thenReturn((listbookingData.get(0)));

		BookingPutRequest bookingPutRequest = new BookingPutRequest((long) 1000, BookingData.Unit.PER_TRUCK,
				Arrays.asList("truck:abcdef"), true, true, null, null);

		BookingPutResponse response = new BookingPutResponse(BookingConstants.uCancelAndCompleteTrue, null, null, null,
				null, null, null, null, null, null, null, null);

		assertEquals(response, bookingService.updateBooking(BookingConstants.ID, bookingPutRequest));

	}

	@Test
	public void updateDataFailed_AccountNotFound() {

		String wrongbookingId = "booking:62cc8557-52cd-4742-a11e-276cc7abcde";

		BookingData emptyList = null;
		when(bookingDao.findByBookingId(wrongbookingId)).thenReturn(emptyList);

		BookingPutRequest bookingPutRequest = new BookingPutRequest((long) 1000, BookingData.Unit.PER_TRUCK,
				Arrays.asList("truck:abcdef"), null, true, null, null);

		BookingPutResponse response = new BookingPutResponse(BookingConstants.uDataNotFound, null, null, null, null,
				null, null, null, null, null, null, null);

		assertEquals(response, bookingService.updateBooking(wrongbookingId, bookingPutRequest));

	}

	@Test
	public void getBookingDataPagableSuccess_Cancel_True_Completed_False_TransporterId() {

		Pageable currentPage;
		Integer pageNo;

		List<BookingData> listbookingData = createBookingData();

		pageNo = 0;
		currentPage = PageRequest.of(0, (int) BookingConstants.pageSize);

		when(bookingDao.findByTransporterIdAndCancelAndCompleted(BookingConstants.TRANSPORTER_ID, true, false,
				currentPage)).thenReturn(listbookingData.subList(2, 4));

		assertEquals(listbookingData.subList(2, 4),
				bookingService.getDataById(pageNo, true, false, BookingConstants.TRANSPORTER_ID, null));

	}

	@Test
	public void getBookingDataPagableSuccess_Cancel_False_Completed_True_TransporterId() {

		Pageable currentPage;
		Integer pageNo;

		List<BookingData> listbookingData = createBookingData();

		pageNo = 0;
		currentPage = PageRequest.of(0, (int) BookingConstants.pageSize);

		when(bookingDao.findByTransporterIdAndCancelAndCompleted(BookingConstants.TRANSPORTER_ID, false, true,
				currentPage)).thenReturn(listbookingData.subList(2, 4));

		assertEquals(listbookingData.subList(2, 4),
				bookingService.getDataById(pageNo, false, true, BookingConstants.TRANSPORTER_ID, null));

	}

	@Test
	public void getBookingDataPagableFailed_Cancel_null_Completed_True_TransporterId() {

		Pageable currentPage;
		Integer pageNo;

		List<BookingData> listbookingData = createBookingData();

		pageNo = 0;
		currentPage = PageRequest.of(0, (int) BookingConstants.pageSize);

		when((bookingDao.findByTransporterIdAndCancelAndCompleted(BookingConstants.TRANSPORTER_ID, null, true,
				currentPage))).thenReturn(Collections.<BookingData>emptyList());

		assertEquals(Collections.<BookingData>emptyList(),
				bookingService.getDataById(pageNo, null, true, BookingConstants.TRANSPORTER_ID, null));

	}

	@Test
	public void getBookingDataPagableFailed_Cancel_True_Completed_null_TransporterId() {

		Pageable currentPage;
		Integer pageNo;

		pageNo = 0;
		currentPage = PageRequest.of(0, (int) BookingConstants.pageSize);

		when(bookingDao.findByTransporterIdAndCancelAndCompleted(BookingConstants.TRANSPORTER_ID, true, null,
				currentPage)).thenReturn(Collections.<BookingData>emptyList());

		assertEquals(Collections.<BookingData>emptyList(),
				bookingService.getDataById(pageNo, true, null, BookingConstants.TRANSPORTER_ID, null));

	}

	@Test
	public void getBookingDataPagableFailed_Cancel_True_Completed_True_TransporterId() {

		Pageable currentPage;
		Integer pageNo;

		pageNo = 0;
		currentPage = PageRequest.of(0, (int) BookingConstants.pageSize);

		when((bookingDao.findByTransporterIdAndCancelAndCompleted(BookingConstants.TRANSPORTER_ID, true, true,
				currentPage))).thenReturn(Collections.<BookingData>emptyList());

		assertEquals(Collections.<BookingData>emptyList(),
				bookingService.getDataById(pageNo, true, true, BookingConstants.TRANSPORTER_ID, null));

	}

	@Test
	public void getBookingDataPagableFailed_TransporterId_PostLoadId() {

		Pageable currentPage;
		Integer pageNo;

		pageNo = 0;
		currentPage = PageRequest.of(0, (int) BookingConstants.pageSize);

		// when(Optional.ofNullable(bookingDao.findByTransporterIdAndCancelAndCompleted(BookingConstants.TRANSPORTER_ID,true,false,
		// currentPage))).thenReturn(Optional.empty());

		assertEquals(Collections.<BookingData>emptyList(), bookingService.getDataById(pageNo, true, false,
				BookingConstants.TRANSPORTER_ID, BookingConstants.POST_LOAD_ID));

	}

	@Test
	public void getBookingDataPagableFailed_Cancel_True_Completed_True_PostLoadId() {

		Pageable currentPage;
		Integer pageNo;

		pageNo = 0;
		currentPage = PageRequest.of(0, (int) BookingConstants.pageSize);

		when((bookingDao.findByPostLoadIdAndCancelAndCompleted(BookingConstants.POST_LOAD_ID, true, true, currentPage)))
				.thenReturn(Collections.<BookingData>emptyList());

		assertEquals(Collections.<BookingData>emptyList(),
				bookingService.getDataById(pageNo, true, true, null, BookingConstants.POST_LOAD_ID));

	}

	@Test
	public void getBookingDataPagableSuccess_Cancel_True_Completed_False_PostLoadId() {

		Pageable currentPage;
		Integer pageNo;
		List<BookingData> listbookingData = createBookingData();
		pageNo = 0;
		currentPage = PageRequest.of(0, (int) BookingConstants.pageSize);

		when(bookingDao.findByPostLoadIdAndCancelAndCompleted(BookingConstants.POST_LOAD_ID, true, false, currentPage))
				.thenReturn(listbookingData.subList(3, 5));

		assertEquals(listbookingData.subList(3, 5),
				bookingService.getDataById(pageNo, true, false, null, BookingConstants.POST_LOAD_ID));

	}

	@Test
	public void getBookingDataPagableSuccess_Cancel_False_Completed_True_PostLoadId() {

		Pageable currentPage;
		Integer pageNo;
		List<BookingData> listbookingData = createBookingData();
		pageNo = 0;
		currentPage = PageRequest.of(0, (int) BookingConstants.pageSize);

		when(bookingDao.findByPostLoadIdAndCancelAndCompleted(BookingConstants.POST_LOAD_ID, false, true, currentPage))
				.thenReturn(listbookingData.subList(3, 5));

		assertEquals(listbookingData.subList(3, 5),
				bookingService.getDataById(pageNo, false, true, null, BookingConstants.POST_LOAD_ID));

	}

	@Test
	public void getBookingDataPagableFailed_Cancel_null_Completed_True_PostLoadId() {

		Pageable currentPage;
		Integer pageNo;

		pageNo = 0;
		currentPage = PageRequest.of(0, (int) BookingConstants.pageSize);

		when((bookingDao.findByPostLoadIdAndCancelAndCompleted(BookingConstants.POST_LOAD_ID, null, true, currentPage)))
				.thenReturn(Collections.<BookingData>emptyList());

		assertEquals(Collections.<BookingData>emptyList(),
				bookingService.getDataById(pageNo, null, true, null, BookingConstants.POST_LOAD_ID));

	}

	@Test
	public void getBookingDataPagableFailed_Cancel_True_Completed_null_PostLoadId() {

		Pageable currentPage;
		Integer pageNo;

		pageNo = 0;
		currentPage = PageRequest.of(0, (int) BookingConstants.pageSize);

		when(bookingDao.findByPostLoadIdAndCancelAndCompleted(BookingConstants.POST_LOAD_ID, true, null, currentPage))
				.thenReturn(Collections.<BookingData>emptyList());

		assertEquals(Collections.<BookingData>emptyList(),
				bookingService.getDataById(pageNo, true, null, null, BookingConstants.POST_LOAD_ID));

	}

	@Test
	public void deleteDataSuccess() {

		List<BookingData> listbookingData = createBookingData();

		when(bookingDao.findByBookingId(BookingConstants.ID)).thenReturn((listbookingData.get(0)));

		BookingDeleteResponse response = new BookingDeleteResponse(BookingConstants.DELETE_SUCCESS);

		assertEquals(response, bookingService.deleteBooking(BookingConstants.ID));

	}

	@Test
	public void deleteDataFailed_AccountNotFound() {

		String wrongbookingId = "booking:xyz";

		BookingData EmptyList = null;

		when(bookingDao.findByBookingId(wrongbookingId)).thenReturn(EmptyList);

		BookingDeleteResponse response = new BookingDeleteResponse(BookingConstants.ACCOUNT_NOT_FOUND);

		assertEquals(response, bookingService.deleteBooking(wrongbookingId));

	}

	public List<BookingData> createBookingData() {
		List<BookingData> bookingList = Arrays.asList(
//				new BookingData( BookingConstants.ID, BookingConstants.TRANSPORTER_ID, 
//						BookingConstants.LOAD_ID,BookingConstants.POST_LOAD_ID,
//						(long) 20,"PER_TON",Arrays.asList("truck:5ce284b9-d55a-4d3d-82fd-b7ce8bb074af"),false,false,null,null)
		// BookingData.Unit.PER_TON, Arrays.asList("truck:123"), false, true, null)
//				new BookingData("id1", BookingConstants.TRANSPORTER_ID, BookingConstants.LOAD_ID, (long) 20, BookingData.Unit.PER_TON,
//						Arrays.asList("truck:123"), false, true, null),
//				new BookingData("id2", "transporterId:0de885e0-5f43-4c68-8dde-b0f9ff81cb61", BookingConstants.LOAD_ID,
//						(long) 40, BookingData.Unit.PER_TON, Arrays.asList("truck:123"), false, true, null),
//				new BookingData("id3", "transporterId:0de885e0-5f43-4c68-8dde-b0f9ff81cb63", BookingConstants.LOAD_ID, null,
//						BookingData.Unit.PER_TON, Arrays.asList("truck:123", "truck:456"), false, true, null),
//				new BookingData("id4", "transporterId:12", BookingConstants.LOAD_ID, (long) 20, BookingData.Unit.PER_TON,
//						Arrays.asList("truck:123"), false, true, null),
//				new BookingData("id5", "transporterId:12345", BookingConstants.LOAD_ID, (long) 20, BookingData.Unit.PER_TON,
//						Arrays.asList("truck:123"), false, true, null)

				new BookingData(BookingConstants.ID, BookingConstants.TRANSPORTER_ID, BookingConstants.LOAD_ID,
						BookingConstants.POST_LOAD_ID, (long) 20, BookingData.Unit.PER_TON,
						Arrays.asList("truck:5ce284b9-d55a-4d3d-82fd-b7ce8bb074af"), false, false, null, null),

				new BookingData("id:1", BookingConstants.TRANSPORTER_ID, "load:123", BookingConstants.POST_LOAD_ID,
						(long) 20, BookingData.Unit.PER_TON,
						Arrays.asList("truck:5ce284b9-d55a-4d3d-82fd-b7ce8bb074af"), false, true, null, null),

				new BookingData("id:2", BookingConstants.TRANSPORTER_ID, BookingConstants.LOAD_ID,
						BookingConstants.POST_LOAD_ID, (long) 20, BookingData.Unit.PER_TON,
						Arrays.asList("truck:5ce284b9-d55a-4d3d-82fd-b7ce8bb074af"), true, false, null, null),

				new BookingData("id:3", "transporter:123", BookingConstants.LOAD_ID, BookingConstants.POST_LOAD_ID,
						(long) 20, BookingData.Unit.PER_TON,
						Arrays.asList("truck:5ce284b9-d55a-4d3d-82fd-b7ce8bb074af"), true, false, null, null),

				new BookingData("id:4", "transporter:1234", "load:123", BookingConstants.POST_LOAD_ID, (long) 20,
						BookingData.Unit.PER_TON, Arrays.asList("truck:5ce284b9-d55a-4d3d-82fd-b7ce8bb074af"), false,
						true, null, null),

				new BookingData("id:5", "transporter:647", BookingConstants.LOAD_ID, BookingConstants.POST_LOAD_ID,
						(long) 20, BookingData.Unit.PER_TON,
						Arrays.asList("truck:5ce284b9-d55a-4d3d-82fd-b7ce8bb074af"), false, true, null, null),

				new BookingData("id:6", BookingConstants.TRANSPORTER_ID, "load:abc", BookingConstants.POST_LOAD_ID,
						(long) 20, BookingData.Unit.PER_TON, Arrays.asList("truck:5ce284b9-d55a-4d"), true, false, null,
						null),

				new BookingData("id:7", BookingConstants.TRANSPORTER_ID, "load:abc", BookingConstants.POST_LOAD_ID,
						(long) 20, null, Arrays.asList("truck:5ce284b9-d55a-4d"), true, false, null, null),

				new BookingData("id:8", null, "load:abc", BookingConstants.POST_LOAD_ID, (long) 20,
						BookingData.Unit.PER_TON, Arrays.asList("truck:5ce284b9-d55a-4d"), true, false, null, null),

				new BookingData("id:9", BookingConstants.TRANSPORTER_ID, "load:abc", null, (long) 20,
						BookingData.Unit.PER_TON, Arrays.asList("truck:5ce284b9-d55a-4d"), true, false, null, null),

				new BookingData("id:10", BookingConstants.TRANSPORTER_ID, "load:abc", BookingConstants.POST_LOAD_ID,
						(long) 20, BookingData.Unit.PER_TON, null, true, false, null, null),

				new BookingData("id:11", BookingConstants.TRANSPORTER_ID, null, BookingConstants.POST_LOAD_ID,
						(long) 20, BookingData.Unit.PER_TON, Arrays.asList("truck:5ce284b9-d55a-4d"), true, false, null,
						null));

		return bookingList;
	}

}