package com.Booking.Booking.Dao;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.Booking.Booking.Entities.BookingData;

@Repository
public interface BookingDao extends JpaRepository<BookingData,String>{

	List<BookingData> findByLoadIdAndTransporterId(String loadId, String transporterId);
	BookingData findByBookingId(String id);
	List<BookingData> findByCancelOrCompleted(Boolean cancel, Boolean completed, Pageable p);
	List<BookingData> findByCompleted(Boolean completed, Pageable p);
	List<BookingData> findByCancel(Boolean cancel, Pageable p);
	List<BookingData> findByTransporterId(String transporterId,Pageable p);
	List<BookingData> findByPostLoadId(String postLoadId,Pageable p);
	List<BookingData> findByTransporterIdAndCancelAndCompleted(String transporterId,Boolean cancel,Boolean completed,Pageable p);
	List<BookingData> findByPostLoadIdAndCancelAndCompleted(String postLoadId,Boolean cancel,Boolean completed,Pageable p);
	List<BookingData> findByCancelAndCompleted(Boolean cancel, Boolean completed, Pageable p);
 	
}
