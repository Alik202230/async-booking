package am.itspace.booking.service;

import am.itspace.booking.dto.CreateBookingRequest;
import am.itspace.booking.dto.CreateBookingResponse;

import java.util.concurrent.CompletableFuture;

public interface BookingService {

  CompletableFuture<CreateBookingResponse> createBookingAsync(CreateBookingRequest request);
  String getBookingStatus(Long bookingId);
  void cancelBooking(Long bookingId);
}
