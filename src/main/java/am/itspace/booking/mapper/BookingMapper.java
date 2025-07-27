package am.itspace.booking.mapper;

import am.itspace.booking.dto.CreateBookingResponse;
import am.itspace.booking.model.Booking;
import am.itspace.booking.model.enums.Status;

import java.util.function.Function;

public final class BookingMapper {

  private BookingMapper() {}

  public static final Function<Booking, CreateBookingResponse> toCreateBookingResponse = booking ->
      CreateBookingResponse.builder()
          .id(booking.getId())
          .checkInDate(booking.getCheckInDate())
          .checkOutDate(booking.getCheckOutDate())
          .numberOfAdults(booking.getNumberOfAdults())
          .numberOfChildren(booking.getNumberOfChildren())
          .guestName(booking.getGuestName())
          .roomNumber(booking.getRoomNumber())
          .room(booking.getRoom())
          .totalPrice(booking.getTotalPrice())
          .status(Status.CONFIRMED)
          .build();
}
