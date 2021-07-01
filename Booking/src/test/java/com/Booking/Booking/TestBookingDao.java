package com.Booking.Booking;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.Booking.Booking.Constants.BookingConstants;
import com.Booking.Booking.Dao.BookingDao;
import com.Booking.Booking.Entities.BookingData;

@DataJpaTest
public class TestBookingDao {

	@Autowired
	private TestEntityManager entityManager;

	@Autowired
	private BookingDao bookingDao;

	@Test
	public void testFindByLoadIdAndTransporterId() {

		List<BookingData> listBookingData = createBookingData();

		BookingData savedInDb = entityManager.persist(listBookingData.get(0));
		BookingData savedInDb1 = entityManager.persist(listBookingData.get(1));
		BookingData savedInDb2 = entityManager.persist(listBookingData.get(2));
		BookingData savedInDb3 = entityManager.persist(listBookingData.get(3));
		BookingData savedInDb4 = entityManager.persist(listBookingData.get(4));
		BookingData savedInDb5 = entityManager.persist(listBookingData.get(5));

		Iterable<BookingData> allBids = bookingDao.findByLoadIdAndTransporterId(BookingConstants.LOAD_ID,
				BookingConstants.TRANSPORTER_ID);

		List<BookingData> list = new ArrayList<>();

		for (BookingData t : allBids) {
			list.add(t);
		}
		assertThat(list.size()).isEqualTo(1);

	}

	@Test
	public void testFindById() {

		List<BookingData> listBookingData = createBookingData();

		BookingData savedInDb = entityManager.persist(listBookingData.get(0));
		BookingData getFromDb = bookingDao.findByBookingId(BookingConstants.ID);

		assertThat(getFromDb).isEqualTo(savedInDb);

	}

	@Test
	public void findByTransporterIdAndCancelAndCompletedWithPagination() {

		Pageable currentPage;
		List<BookingData> listBookingData = createBookingData();

		BookingData savedInDb = entityManager.persist(listBookingData.get(0));
		BookingData savedInDb1 = entityManager.persist(listBookingData.get(1));
		BookingData savedInDb2 = entityManager.persist(listBookingData.get(2));
		BookingData savedInDb3 = entityManager.persist(listBookingData.get(3));
		BookingData savedInDb4 = entityManager.persist(listBookingData.get(4));
		BookingData savedInDb5 = entityManager.persist(listBookingData.get(5));

		currentPage = PageRequest.of(0, (int) BookingConstants.pageSize);

		Iterable<BookingData> allBids = bookingDao
				.findByTransporterIdAndCancelAndCompleted(BookingConstants.TRANSPORTER_ID, false, true, currentPage);
		List<BookingData> list = new ArrayList<>();

		for (BookingData t : allBids) {
			list.add(t);
		}
		assertThat(list.size()).isEqualTo(1);

	}

	@Test
	public void findByPostLoadIdAndCancelAndCompletedWithPagination() {

		Pageable currentPage;
		List<BookingData> listBookingData = createBookingData();

		BookingData savedInDb = entityManager.persist(listBookingData.get(0));
		BookingData savedInDb1 = entityManager.persist(listBookingData.get(1));
		BookingData savedInDb2 = entityManager.persist(listBookingData.get(2));
		BookingData savedInDb3 = entityManager.persist(listBookingData.get(3));
		BookingData savedInDb4 = entityManager.persist(listBookingData.get(4));
		BookingData savedInDb5 = entityManager.persist(listBookingData.get(5));

		currentPage = PageRequest.of(0, (int) BookingConstants.pageSize);

		Iterable<BookingData> allBids = bookingDao.findByPostLoadIdAndCancelAndCompleted(BookingConstants.POST_LOAD_ID,
				true, false, currentPage);
		List<BookingData> list = new ArrayList<>();

		for (BookingData t : allBids) {
			list.add(t);
		}
		assertThat(list.size()).isEqualTo(2);

	}

	@Test
	public void findByCancelAndCompletedWithPagination() {

		Pageable currentPage;
		List<BookingData> listBookingData = createBookingData();

		BookingData savedInDb = entityManager.persist(listBookingData.get(0));
		BookingData savedInDb1 = entityManager.persist(listBookingData.get(1));
		BookingData savedInDb2 = entityManager.persist(listBookingData.get(2));
		BookingData savedInDb3 = entityManager.persist(listBookingData.get(3));
		BookingData savedInDb4 = entityManager.persist(listBookingData.get(4));
		BookingData savedInDb5 = entityManager.persist(listBookingData.get(5));

		currentPage = PageRequest.of(0, (int) BookingConstants.pageSize);

		Iterable<BookingData> allBids = bookingDao.findByCancelAndCompleted(false, true, currentPage);
		List<BookingData> list = new ArrayList<>();

		for (BookingData t : allBids) {
			list.add(t);
		}
		assertThat(list.size()).isEqualTo(3);

	}

	public List<BookingData> createBookingData() {
		List<BookingData> bookingList = Arrays.asList(

				new BookingData(BookingConstants.ID, BookingConstants.TRANSPORTER_ID, BookingConstants.LOAD_ID,
						BookingConstants.POST_LOAD_ID, (long) 20, BookingData.Unit.PER_TON,
						Arrays.asList("truck:5ce284b9-d55a-4d3d-82fd-b7ce8bb074af"), false, false, null, null),

				new BookingData("id:1", BookingConstants.TRANSPORTER_ID, "load:123", BookingConstants.POST_LOAD_ID,
						(long) 20, BookingData.Unit.PER_TON,
						Arrays.asList("truck:5ce284b9-d55a-4d3d-82fd-b7ce8bb074af"), false, true, null, null),

				new BookingData("id:2", BookingConstants.TRANSPORTER_ID, "load:1234", BookingConstants.POST_LOAD_ID,
						(long) 20, BookingData.Unit.PER_TON,
						Arrays.asList("truck:5ce284b9-d55a-4d3d-82fd-b7ce8bb074af"), true, false, null, null),

				new BookingData("id:3", "transporter:123", BookingConstants.LOAD_ID, BookingConstants.POST_LOAD_ID,
						(long) 20, BookingData.Unit.PER_TON,
						Arrays.asList("truck:5ce284b9-d55a-4d3d-82fd-b7ce8bb074af"), true, false, null, null),

				new BookingData("id:4", "transporter:1234", "load:123", BookingConstants.POST_LOAD_ID, (long) 20,
						BookingData.Unit.PER_TON, Arrays.asList("truck:5ce284b9-d55a-4d3d-82fd-b7ce8bb074af"), false,
						true, null, null),

				new BookingData("id:5", "transporter:647", BookingConstants.LOAD_ID, BookingConstants.POST_LOAD_ID,
						(long) 20, BookingData.Unit.PER_TON,
						Arrays.asList("truck:5ce284b9-d55a-4d3d-82fd-b7ce8bb074af"), false, true, null, null)

		);

		return bookingList;
	}

}