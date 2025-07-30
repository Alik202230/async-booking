package am.itspace.booking.service.impl;

import am.itspace.booking.cache.BookingCache;
import am.itspace.booking.service.NotificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class NotificationServiceImpl implements NotificationService {

  @Override
  public void sendSuccessNotification(String uuid, BookingCache cache) {
    try {
      Thread.sleep(15000);
      cache.putInCache(uuid, "SUCCESS");
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
