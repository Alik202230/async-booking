package am.itspace.booking.cache;

import am.itspace.booking.exception.BookingNotFoundException;
import am.itspace.booking.model.Booking;
import am.itspace.booking.repository.BookingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
public class BookingCache {

  private final Map<Long, String> processingBooking = new ConcurrentHashMap<>();
  private final BookingRepository bookingRepository;

  public void initializeBookingCache(Long bookingId) {
    Booking booking = bookingRepository.findById(bookingId)
        .orElseThrow(() -> new BookingNotFoundException("Booking not found"));
    if (processingBooking.containsKey(bookingId)) {
      processingBooking.put(booking.getId(), booking.getStatus().name());
    }
  }
}