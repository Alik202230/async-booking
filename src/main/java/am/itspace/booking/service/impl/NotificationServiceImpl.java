package am.itspace.booking.service.impl;

import am.itspace.booking.exception.BookingNotFoundException;
import am.itspace.booking.model.Booking;
import am.itspace.booking.model.enums.Status;
import am.itspace.booking.repository.BookingRepository;
import am.itspace.booking.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

  private final BookingRepository bookingRepository;

  @Override
  public void sendSuccessNotification(Long bookingId) {
    Booking booking = this.bookingRepository.findById(bookingId)
        .orElseThrow(() -> new BookingNotFoundException("There is no booking with id " + bookingId));
    try {
      log.info("Booking with id {} is in progress status {}", bookingId, booking.getStatus());
      Thread.sleep(15000);
      booking.setStatus(Status.CONFIRMED);
      this.bookingRepository.save(booking);
      log.info("Booking with id {} is created successfully", bookingId);
    } catch (InterruptedException e) {
      log.error("Error sending success notification", e);
    }
  }

  @Override
  public void sendFailureNotification(String message) {
    try {
      log.info("Sending failure notification for booking {}", message);
      Thread.sleep(300);
      log.info("Booking creation failed {}", message);
    } catch (InterruptedException e) {
      log.error("Error sending failure notification", e);
    }
  }
}
