package com.Booking.Booking.Service;


import java.util.List;
import java.util.Objects;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.Booking.Booking.Constants.BookingConstants;
import com.Booking.Booking.Dao.BookingDao;
import com.Booking.Booking.Entities.BookingData;
import com.Booking.Booking.Model.BookingPostRequest;
import com.Booking.Booking.Model.BookingPostResponse;
import com.Booking.Booking.Model.BookingPutRequest;
import com.Booking.Booking.Model.BookingPutResponse;


@Service
public class BookingServiceImpl implements BookingService{

	@Autowired
	private BookingDao bookingDao;
	
	private BookingConstants constants;
	
	
	@Override
	public BookingPostResponse addBooking(BookingPostRequest request) {
		// TODO Auto-generated method stub
		BookingData bookingData = new BookingData();
		BookingPostResponse response = new BookingPostResponse();
		
		if(request.getLoadId()==null) {
			response.setStatus(constants.pLoadIdIsNull);
			return response;
		}else if(request.getTransporterId()==null) {
			response.setStatus(constants.pTransporterIdIsNull);
			return response;
		}else if(request.getTruckId()==null||request.getTruckId().size()==0) {
			response.setStatus(constants.pTruckIdIsNull);
			return response;
		}
		
		bookingData.setBookingId("booking:"+UUID.randomUUID());
		bookingData.setLoadId(request.getLoadId());
		bookingData.setTransporterId(request.getTransporterId());
		bookingData.setTruckId(request.getTruckId());
		
		if(request.getRate()!=null) {
			if(request.getUnitValue()==null) {
				response.setStatus(constants.pUnitIsNull);
				return response;
			}
			if(request.getUnitValue().equals("PER_TON")) {
				bookingData.setUnitValue(BookingData.Unit.PER_TON);
			}else if(request.getUnitValue().equals("PER_TRUCK")) {
				bookingData.setUnitValue(BookingData.Unit.PER_TRUCK);
			}else {
				response.setStatus(constants.pUnknownUnit);
				return response;
			}
		}else {
			if(request.getUnitValue()!=null) {
				response.setStatus(constants.pPostUnitRateIsNull);
				return response;
			}
		}
		
		bookingData.setRate(request.getRate());
		bookingData.setCancel(false);
		bookingData.setCompleted(false);
		
		if(bookingDao.findByLoadIdAndTransporterId(request.getLoadId(),request.getTransporterId()).size()!=0) {
			response.setStatus(constants.pDataExists);
			return response;
		}
		
		bookingDao.save(bookingData);
		response.setStatus(constants.success);
		return response;
	}

    

	@Override
	public BookingPutResponse updateBooking(String bookingId, BookingPutRequest request) {
		// TODO Auto-generated method stub
		
		BookingPutResponse response = new BookingPutResponse();
		
		BookingData data  = bookingDao.findByBookingId(bookingId);
		
		if(Objects.isNull(data)) {
		response.setStatus(constants.uDataNotFound);
		return response;
		}
		
		if(request.getTruckId()!=null&&request.getTruckId().size()==0) {
			response.setStatus(constants.uTruckIdIsNull);
			return response;
		}
		
		if(request.getCompleted()!=null&&request.getCancel()!=null&&request.getCancel()==true&&request.getCompleted()==true) {
			response.setStatus(constants.uCancelAndCompleteTrue);
			return response;
		}
		
		if(request.getTruckId()!=null) {
			data.setTruckId(request.getTruckId());
		}
		
		if(request.getRate()!=null) {
			if(request.getUnitValue()==null) {
				response.setStatus(constants.uUnitIsNull);
				return response;
			}
			if(request.getUnitValue().equals("PER_TON")) {
				data.setUnitValue(BookingData.Unit.PER_TON);
			}else if(request.getUnitValue().equals("PER_TRUCK")) {
				data.setUnitValue(BookingData.Unit.PER_TRUCK);
			}else {
				response.setStatus(constants.uUnknownUnit);
				return response;
			}
		}else {
			if(request.getUnitValue()!=null) {
				response.setStatus(constants.uUpdateUnitRateIsNull);
				return response;
			}
		}
		
		if(request.getRate()!=null) {
			data.setRate(request.getRate());
		}
		
		if(request.getCompleted()!=null) {
			if(request.getCompleted()==true) {
				data.setCompleted(true);
				data.setCancel(false);
			}else {
				data.setCompleted(false);
			}
		}
		
		if(request.getCancel()!=null) {
			if(request.getCancel()==true) {
				if(data.getCompleted()==true||request.getCompleted()==true) {
					response.setStatus(constants.uCanelIsTrueWhenCompleteIsTrue);
					return response;
				}
				data.setCancel(true);
			}else {
				data.setCancel(false);
			}
		}
		
		bookingDao.save(data);
		response.setStatus(constants.success);
		return response;
	}


	@Override
	public BookingData getDataById(String Id) {
		// TODO Auto-generated method stub
		return bookingDao.findByBookingId(Id);
	}


	@Override
	public List<BookingData> getDataById(Integer pageNo, Boolean cancel, Boolean completed) {
		// TODO Auto-generated method stub
		if(pageNo==null) {
			pageNo=0;
		}
		Pageable p = PageRequest.of(pageNo,2);
		List<BookingData> temp = null;
		if(cancel==null&&completed!=null) {
			temp = bookingDao.findByCompleted(completed,p);
			if(temp.size()!=0)
				return temp;
		}else if(cancel!=null&&completed==null) {
			temp = bookingDao.findByCancel(cancel,p);
			if(temp.size()!=0)
				return temp;
		}else if(cancel!=null&&completed!=null) {
			temp = bookingDao.findByCancelOrCompleted(cancel,completed,p);
			if(temp.size()!=0)
				return temp;
		}
		
		return bookingDao.findAll();
		
	}


	@Override
	public void deleteBooking(String bookingId) {
		// TODO Auto-generated method stub
		BookingData temp = bookingDao.findByBookingId(bookingId);
		if(!Objects.isNull(temp))
		 bookingDao.deleteById(bookingId);
	}
	
	
	

}
