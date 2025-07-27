package am.itspace.booking.exception;

public class BookingNotFoundException extends RuntimeException{
  public BookingNotFoundException(String message) {
    super(message);
  }
}
