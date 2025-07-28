package am.itspace.booking.service;

public interface NotificationService {

  void sendSuccessNotification(Long bookingId);

  void sendFailureNotification(String message);

}
