package am.itspace.booking.exception;

public class RoomNotFoundException extends RuntimeException{

  public RoomNotFoundException(String message) {
    super(message);
  }

}
