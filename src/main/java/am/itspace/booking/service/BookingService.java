package am.itspace.booking.service;

import am.itspace.booking.dto.CreateBookingRequest;

import java.util.concurrent.CompletableFuture;

public interface BookingService {

  CompletableFuture<Void> createBookingAsync(CreateBookingRequest request, String uuid);
  String getBookingStatus(String uuid);
  void cancelBooking(Long bookingId);
}
