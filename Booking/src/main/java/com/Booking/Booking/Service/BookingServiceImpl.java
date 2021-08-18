package com.Booking.Booking.Service;



import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.Socket;
import java.net.URL;
import java.util.List;
import java.util.UUID;

import org.hibernate.exception.DataException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;

import com.Booking.Booking.Constants.BookingConstants;
import com.Booking.Booking.Dao.BookingDao;
import com.Booking.Booking.Entities.BookingData;
import com.Booking.Booking.Exception.BusinessException;
import com.Booking.Booking.Exception.EntityNotFoundException;
import com.Booking.Booking.Model.BookingDeleteResponse;
import com.Booking.Booking.Model.BookingPostRequest;
import com.Booking.Booking.Model.BookingPostResponse;
import com.Booking.Booking.Model.BookingPutRequest;
import com.Booking.Booking.Model.BookingPutResponse;

import io.github.cdimascio.dotenv.Dotenv;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class BookingServiceImpl implements BookingService {
	@Autowired
	private BookingDao bookingDao;

	private BookingConstants constants;

	@Override
	public BookingPostResponse addBooking(BookingPostRequest request){

		BookingData bookingData = new BookingData();
		BookingPostResponse response = new BookingPostResponse();

		bookingData.setBookingId("booking:" + UUID.randomUUID());
		bookingData.setLoadId(request.getLoadId());
		bookingData.setTransporterId(request.getTransporterId());
		bookingData.setPostLoadId(request.getPostLoadId());
		bookingData.setTruckId(request.getTruckId());

		if (request.getRate() != null) {
			if (request.getUnitValue() == null) {
				log.error(constants.pUnitIsNull);
				throw new BusinessException(constants.pUnitIsNull);
			}

			if (String.valueOf(request.getUnitValue()).equals("PER_TON")) {
				bookingData.setUnitValue(BookingData.Unit.PER_TON);
			} else if (String.valueOf(request.getUnitValue()).equals("PER_TRUCK")) {
				bookingData.setUnitValue(BookingData.Unit.PER_TRUCK);
			} else {
				log.error(BookingConstants.uUnknownUnit);
				throw new BusinessException(BookingConstants.uUnknownUnit);

			}
		} else {
			if (request.getUnitValue() != null) {
				log.error(constants.pPostUnitRateIsNull);
				throw new BusinessException(constants.pPostUnitRateIsNull);

			}
		}

		if (request.getBookingDate() != null) {
			bookingData.setBookingDate(request.getBookingDate());
		}

		bookingData.setRate(request.getRate());
		bookingData.setCancel(false);
		bookingData.setCompleted(false);

		try {
			bookingDao.save(bookingData);
			log.info("Booking Data is saved");
		} catch (Exception ex) {
			log.error("Booking Data is not saved -----" + String.valueOf(ex));
			throw ex;
		}

		response.setStatus(constants.success);
		response.setBookingId(bookingData.getBookingId());
		response.setCancel(bookingData.getCancel());
		response.setCompleted(bookingData.getCompleted());
		response.setLoadId(bookingData.getLoadId());
		response.setPostLoadId(bookingData.getPostLoadId());
		response.setRate(bookingData.getRate());
		response.setTransporterId(bookingData.getTransporterId());
		response.setTruckId(bookingData.getTruckId());
		response.setUnitValue(bookingData.getUnitValue());
		response.setBookingDate(bookingData.getBookingDate());

		try {
			log.info("Post Service Response returned");

			return response;
		} catch (Exception ex) {
			log.error("Post Service Response not returned -----" + String.valueOf(ex));
			throw ex;

		}
	}

	
	//cancel = false, compleate true
	@Override
	public BookingPutResponse updateBooking(String bookingId, BookingPutRequest request) {
		
		
		BookingPutResponse response = new BookingPutResponse();

		BookingData data = bookingDao.findByBookingId(bookingId);

		if (data == null) {
			EntityNotFoundException ex = new EntityNotFoundException(BookingData.class, "bookingId",
					bookingId.toString());

			log.error(String.valueOf(ex));
			throw ex;
		}

		if (request.getTruckId() != null && request.getTruckId().size() == 0) {
			log.error(BookingConstants.uTruckIdIsNull);
			throw new BusinessException(BookingConstants.uTruckIdIsNull);

		}

		if (request.getCompleted() != null && request.getCancel() != null && request.getCancel() == true
				&& request.getCompleted() == true) {
			log.error(BookingConstants.uCancelAndCompleteTrue);
			throw new BusinessException(BookingConstants.uCancelAndCompleteTrue);

		}

		if (request.getTruckId() != null) {
			data.setTruckId(request.getTruckId());
		}

		if (request.getRate() != null) {
			if (request.getUnitValue() == null) {
				log.error(BookingConstants.uUnitIsNull);
				throw new BusinessException(BookingConstants.uUnitIsNull);

			}
			if (String.valueOf(request.getUnitValue()).equals("PER_TON")) {
				data.setUnitValue(BookingData.Unit.PER_TON);
			} else if (String.valueOf(request.getUnitValue()).equals("PER_TRUCK")) {
				data.setUnitValue(BookingData.Unit.PER_TRUCK);
			} else {
				log.error(BookingConstants.uUnknownUnit);
				throw new BusinessException(BookingConstants.uUnknownUnit);

			}
		} else {
			if (request.getUnitValue() != null) {
				log.error(BookingConstants.uUpdateUnitRateIsNull);
				throw new BusinessException(BookingConstants.uUpdateUnitRateIsNull);

			}
		}

		if (request.getRate() != null) {
			data.setRate(request.getRate());
		}

		if (request.getCompleted() != null) {
			if (request.getCompleted() == true) {
				data.setCompleted(true);
				data.setCancel(false);
				////cancel = false complete true
			} else if (data.getCompleted() == true && request.getCompleted() == false) {
				log.error(BookingConstants.uAlreadyCompleted);
				throw new BusinessException(BookingConstants.uAlreadyCompleted);

			}
		}

		if (request.getCancel() != null) {
			if (request.getCancel() == true) {
				if ((data.getCompleted() == true)
						|| (request.getCompleted() != null && request.getCompleted() == true)) {
					log.error(BookingConstants.uCanelIsTrueWhenCompleteIsTrue);
					throw new BusinessException(BookingConstants.uCanelIsTrueWhenCompleteIsTrue);

				}
				data.setCancel(true);
			} else {
				data.setCancel(false);
			}
		}

		if (request.getBookingDate() != null) {
			data.setBookingDate(request.getBookingDate());
		}

		if (request.getCompletedDate() != null && (data.getCompleted() == null || data.getCompleted() == false)) {
			log.error(BookingConstants.uCompletedDateWhenCompletedIsNotTrue);
			throw new BusinessException(BookingConstants.uCompletedDateWhenCompletedIsNotTrue);

		} else if (request.getCompletedDate() != null) {
			data.setCompletedDate(request.getCompletedDate());
		}

		try {
			bookingDao.save(data);
			log.info("Booking Data is updated");
		} catch (Exception ex) {
			log.error("Booking Data is not updated -----" + String.valueOf(ex));
			throw ex;

		}

		response.setStatus(constants.UPDATE_SUCCESS);
		response.setBookingId(data.getBookingId());
		response.setCancel(data.getCancel());
		response.setCompleted(data.getCompleted());
		response.setLoadId(data.getLoadId());
		response.setPostLoadId(data.getPostLoadId());
		response.setRate(data.getRate());
		response.setTransporterId(data.getTransporterId());
		response.setTruckId(data.getTruckId());
		response.setUnitValue(data.getUnitValue());
		response.setBookingDate(data.getBookingDate());
		response.setCompletedDate(data.getCompletedDate());

		try {
			log.info("Put Service Response returned");
			return response;
		} catch (Exception ex) {
			log.error("Put Service Response not returned -----" + String.valueOf(ex));
			throw ex;

		}

	}

	@Override
	public BookingData getDataById(String Id) {

		BookingData bookingData = bookingDao.findByBookingId(Id);
		if (bookingData == null) {
			EntityNotFoundException ex = new EntityNotFoundException(BookingData.class, "bookingId", Id.toString());
			log.error(String.valueOf(ex));
			throw ex;
		}

		try {
			log.info("Booking Data returned");
			return bookingData;
		} catch (Exception ex) {
			log.error("Booking Data not returned -----" + String.valueOf(ex));
			throw ex;

		}

	}

	
	@Override
	public List<BookingData> getDataById(Integer pageNo, Boolean cancel, Boolean completed, String transporterId,
			String postLoadId) {

		if (pageNo == null) {
			pageNo = 0;
		}
		Pageable page = PageRequest.of(pageNo, BookingConstants.pageSize,Sort.Direction.DESC,"timestamp");
		//		List<BookingData> temp = null;

		if ((cancel == null || completed == null) && (transporterId != null || postLoadId != null)) {
			EntityNotFoundException ex = new EntityNotFoundException(BookingData.class, "completed",
					String.valueOf(completed), "cancel", String.valueOf(cancel));
			log.error(String.valueOf(ex));
			throw ex;
		}
		if (cancel != null && completed != null && cancel == true && completed == true) {

			EntityNotFoundException ex = new EntityNotFoundException(BookingData.class, "completed",
					String.valueOf(completed), "cancel", String.valueOf(cancel));
			log.error(String.valueOf(ex));
			throw ex;

		}

		if (transporterId != null && postLoadId != null) {
			EntityNotFoundException ex = new EntityNotFoundException(BookingData.class, "transporterId",
					String.valueOf(transporterId), "postLoadId", String.valueOf(postLoadId));
			log.error(String.valueOf(ex));
			throw ex;

		}

		if (transporterId != null) {
			try {
				log.info("Booking Data with params returned");
				return bookingDao.findByTransporterIdAndCancelAndCompleted(transporterId, cancel, completed, page);
			} catch (Exception ex) {
				log.error("Booking Data with params not returned -----" + String.valueOf(ex));
				throw ex;

			}

		}

		if (postLoadId != null) {
			try {
				log.info("Booking Data with params returned");
				return bookingDao.findByPostLoadIdAndCancelAndCompleted(postLoadId, cancel, completed, page);
			} catch (Exception ex) {
				log.error("Booking Data with params not returned -----" + String.valueOf(ex));
				throw ex;

			}
		}

		if (cancel != null && completed != null) {
			try {
				log.info("Booking Data with params returned");
				return bookingDao.findByCancelAndCompleted(cancel, completed, page);
			} catch (Exception ex) {
				log.error("Booking Data with params not returned -----" + String.valueOf(ex));
				throw ex;

			}
		}

		try {
			log.info("Booking Data with params returned");
			return bookingDao.getAll(page);
		} catch (Exception ex) {
			log.error("Booking Data with params not returned -----" + String.valueOf(ex));
			throw ex;

		}

	}

	@Override
	public BookingDeleteResponse deleteBooking(String bookingId) {

		BookingDeleteResponse response = new BookingDeleteResponse();

		BookingData temp = bookingDao.findByBookingId(bookingId);

		if (temp == null) {
			EntityNotFoundException ex = new EntityNotFoundException(BookingData.class, "bookingId",
					bookingId.toString());
			log.error(String.valueOf(ex));
			throw ex;
		}

		try {
			bookingDao.deleteById(bookingId);
			log.info("Deleted");
		} catch (Exception ex) {
			log.error(String.valueOf(ex));
			throw ex;

		}

		response.setStatus(constants.DELETE_SUCCESS);

		try {
			log.info("Deleted Service Response returned");
			return response;
		} catch (Exception ex) {
			log.error("Deleted Service Response not returned -----" + String.valueOf(ex));
			throw ex;

		}

	}
	
	@Async
	@Retryable(maxAttempts = 24*60/15, value = { ConnectException.class, Exception.class, RuntimeException.class },
	backoff = @Backoff(15*60*1000))
	public void updating_load_status_by_loadid(String loadid, String inputJson) throws ConnectException, Exception
	{
		
		URL file_url = new URL("https://practice1221.s3.ap-south-1.amazonaws.com/test.txt");
        BufferedReader in = new BufferedReader(new InputStreamReader(file_url.openStream()));

        String ip, port;
        ip = in.readLine();
        port = in.readLine();
        in.close();
        
		try {
			log.info("started update load status");
			Socket clientSocket = new Socket(ip, Integer.parseInt(port));
			clientSocket.close();
			RestAssured.baseURI = "http://"+ip+":"+port+ "/load";  
			Response responseupdate = RestAssured.given().header("", "").body(inputJson)
					.header("accept", "application/json")
					.header("Content-Type", "application/json")
					.put("/" + loadid).then().extract().response();
			log.info("update load status successful");
		}
		catch (ConnectException e) {
			log.error("ConnectException: update load status failed");
			throw e;
		} catch (Exception e) {
			log.error("Exception: update load status failed");
			throw e;
		}
	}
}
