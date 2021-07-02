package com.Booking.Booking.Service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.Booking.Booking.ApiErrors.BusinessException;
import com.Booking.Booking.ApiErrors.EntityNotFoundException;
import com.Booking.Booking.Constants.BookingConstants;
import com.Booking.Booking.Dao.BookingDao;
import com.Booking.Booking.Entities.BookingData;
import com.Booking.Booking.Model.BookingDeleteResponse;
import com.Booking.Booking.Model.BookingPostRequest;
import com.Booking.Booking.Model.BookingPostResponse;
import com.Booking.Booking.Model.BookingPutRequest;
import com.Booking.Booking.Model.BookingPutResponse;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class BookingServiceImpl implements BookingService {

	@Autowired
	private BookingDao bookingDao;

	private BookingConstants constants;

	@Override
	public BookingPostResponse addBooking(BookingPostRequest request) {
		// TODO Auto-generated method stub
		BookingData bookingData = new BookingData();
		BookingPostResponse response = new BookingPostResponse();
//
//		if (request.getLoadId() == null) {
//			response.setStatus(constants.pLoadIdIsNull);
//			return response;
//		} else if (request.getTransporterId() == null) {
//			response.setStatus(constants.pTransporterIdIsNull);
//			return response;
//		} else if (request.getTruckId() == null || request.getTruckId().size() == 0) {
//			response.setStatus(constants.pTruckIdIsNull);
//			return response;
//		} else if (request.getPostLoadId() == null) {
//			response.setStatus(constants.pPostLoadIdIsNull);
//			return response;
//		}

		bookingData.setBookingId("booking:" + UUID.randomUUID());
		bookingData.setLoadId(request.getLoadId());
		bookingData.setTransporterId(request.getTransporterId());
		bookingData.setPostLoadId(request.getPostLoadId());
		bookingData.setTruckId(request.getTruckId());

		if (request.getRate() != null) {
			if (request.getUnitValue() == null) {
				response.setStatus(constants.pUnitIsNull);
				return response;
			}

			if (String.valueOf(request.getUnitValue()).equals("PER_TON")) {
				bookingData.setUnitValue(BookingData.Unit.PER_TON);
			} else if (String.valueOf(request.getUnitValue()).equals("PER_TRUCK")) {
				bookingData.setUnitValue(BookingData.Unit.PER_TRUCK);
			} else {
				response.setStatus(constants.pUnknownUnit);
				return response;
			}
		} else {
			if (request.getUnitValue() != null) {
				response.setStatus(constants.pPostUnitRateIsNull);
				return response;
			}
		}

		if (request.getBookingDate() != null) {
			bookingData.setBookingDate(request.getBookingDate());
		}

		bookingData.setRate(request.getRate());
		bookingData.setCancel(false);
		bookingData.setCompleted(false);

//		if (bookingDao.findByLoadIdAndTransporterId(request.getLoadId(), request.getTransporterId()).size() != 0) {
//			response.setStatus(constants.pDataExists);
//			return response;
//		}

		try {
			bookingDao.save(bookingData);
		} catch (Exception ex) {
			log.error("------------------" + String.valueOf(ex));
			log.info("----------------error");
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
		return response;
	}

	@Override
	public BookingPutResponse updateBooking(String bookingId, BookingPutRequest request) {
		// TODO Auto-generated method stub

		BookingPutResponse response = new BookingPutResponse();

		BookingData data = bookingDao.findByBookingId(bookingId);

//		if (Objects.isNull(data)) {
//			response.setStatus(constants.uDataNotFound);
//			return response;
//		}

		if (data == null)
			throw new EntityNotFoundException(BookingData.class, "bookingId", bookingId.toString());

		if (request.getTruckId() != null && request.getTruckId().size() == 0) {

			throw new BusinessException(BookingConstants.uTruckIdIsNull);
//			response.setStatus(constants.uTruckIdIsNull);
//			return response;
		}

		if (request.getCompleted() != null && request.getCancel() != null && request.getCancel() == true
				&& request.getCompleted() == true) {

			throw new BusinessException(BookingConstants.uCancelAndCompleteTrue);
			// return response;
		}

		if (request.getTruckId() != null) {
			data.setTruckId(request.getTruckId());
		}

		if (request.getRate() != null) {
			if (request.getUnitValue() == null) {

				throw new BusinessException(BookingConstants.uUnitIsNull);
//				response.setStatus(constants.uUnitIsNull);
//				return response;
			}
			if (String.valueOf(request.getUnitValue()).equals("PER_TON")) {
				data.setUnitValue(BookingData.Unit.PER_TON);
			} else if (String.valueOf(request.getUnitValue()).equals("PER_TRUCK")) {
				data.setUnitValue(BookingData.Unit.PER_TRUCK);
			} else {

				throw new BusinessException(BookingConstants.uUnknownUnit);
//				response.setStatus(constants.uUnknownUnit);
//				return response;
			}
		} else {
			if (request.getUnitValue() != null) {

				throw new BusinessException(BookingConstants.uUpdateUnitRateIsNull);

//				response.setStatus(constants.uUpdateUnitRateIsNull);
//				return response;
			}
		}

		if (request.getRate() != null) {
			data.setRate(request.getRate());
		}

		if (request.getCompleted() != null) {
			if (request.getCompleted() == true) {
				data.setCompleted(true);
				data.setCancel(false);
			} else {
				data.setCompleted(false);
			}
		}

		if (request.getCancel() != null) {
			if (request.getCancel() == true) {
				if ((data.getCompleted() == true)
						|| (request.getCompleted() != null && request.getCompleted() == true)) {

					throw new BusinessException(BookingConstants.uCanelIsTrueWhenCompleteIsTrue);
//					response.setStatus(constants.uCanelIsTrueWhenCompleteIsTrue);
//					return response;
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
			throw new BusinessException(BookingConstants.uCompletedDateWhenCompletedIsNotTrue);
//			response.setStatus(constants.uCompletedDateWhenCompletedIsNotTrue);
//			return response;
		} else if (request.getCompletedDate() != null) {
			data.setCompletedDate(request.getCompletedDate());
		}

		try {
			bookingDao.save(data);
		} catch (Exception ex) {
			log.error("------------------" + String.valueOf(ex));
			log.info("----------------error");

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
		return response;
	}

	@Override
	public BookingData getDataById(String Id) {
		// TODO Auto-generated method stub
		BookingData bookingData = bookingDao.findByBookingId(Id);
		if (bookingData == null)
			throw new EntityNotFoundException(BookingData.class, "bookingId", Id.toString());
		return bookingData;
	}

	@Override
	public List<BookingData> getDataById(Integer pageNo, Boolean cancel, Boolean completed, String transporterId,
			String postLoadId) {
		// TODO Auto-generated method stub
		if (pageNo == null) {
			pageNo = 0;
		}
		Pageable p = PageRequest.of(pageNo, (int) BookingConstants.pageSize);
		List<BookingData> temp = null;

//		if ((cancel == null || completed == null) && (transporterId != null || postLoadId != null)) 
//			throw new EntityNotFoundException(BookingData.class, "completed",String.valueOf(completed),"cancel",String.valueOf(cancel));

//		if ((cancel == null || completed == null) && (transporterId != null || postLoadId != null)) {
//			List<BookingData> emptyList = Collections.<BookingData>emptyList();
//			return emptyList;
//		}

		if (cancel != null && completed != null && cancel == true && completed == true) {
			throw new EntityNotFoundException(BookingData.class, "completed", String.valueOf(completed), "cancel",
					String.valueOf(cancel));

		}

		if (transporterId != null && postLoadId != null) {
			throw new EntityNotFoundException(BookingData.class, "transporterId", String.valueOf(transporterId),
					"postLoadId", String.valueOf(postLoadId));

		}

		if (transporterId != null) {
			return bookingDao.findByTransporterIdAndCancelAndCompleted(transporterId, cancel, completed, p);
		}

		if (postLoadId != null) {
			return bookingDao.findByPostLoadIdAndCancelAndCompleted(postLoadId, cancel, completed, p);
		}

		if (cancel != null && completed != null) {
			return bookingDao.findByCancelAndCompleted(cancel, completed, p);
		}

		return bookingDao.findAll();

	}

	@Override
	public BookingDeleteResponse deleteBooking(String bookingId) {
		// TODO Auto-generated method stub
		BookingDeleteResponse response = new BookingDeleteResponse();

		BookingData temp = bookingDao.findByBookingId(bookingId);

		if (temp == null)
			throw new EntityNotFoundException(BookingData.class, "bookingId", bookingId.toString());

		bookingDao.deleteById(bookingId);
		response.setStatus(constants.DELETE_SUCCESS);
		return response;

	}

}
