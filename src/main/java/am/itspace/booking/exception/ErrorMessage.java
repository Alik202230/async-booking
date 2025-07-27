package am.itspace.booking.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
public class ErrorMessage {

  private String message;
  private int code;
  private HttpStatus httpStatus;
  private LocalDateTime timestamp;

  public ErrorMessage(String message, int code, HttpStatus httpStatus, LocalDateTime timestamp) {
    this.message = message;
    this.code = code;
    this.httpStatus = httpStatus;
    this.timestamp = timestamp;
  }

}
