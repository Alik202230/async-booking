package am.itspace.booking.service;

import am.itspace.booking.cache.BookingCache;


public interface NotificationService {

  void sendSuccessNotification(String uuid, BookingCache cache);

  void sendFailureNotification(String message);

}
