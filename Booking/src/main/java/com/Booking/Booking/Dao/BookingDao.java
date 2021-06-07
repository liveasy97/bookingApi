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
	
}
