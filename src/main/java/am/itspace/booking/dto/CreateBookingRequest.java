package am.itspace.booking.dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateBookingRequest {

  @NotNull(message = "Check-in date cannot be null")
  @FutureOrPresent(message = "Check-in date must be today or in the future")
  private LocalDate checkInDate;

  @NotNull(message = "Check-out date cannot be null")
  private LocalDate checkOutDate;

  @Min(value = 1, message = "Number of adult must be at least 1")
  private Integer numberOfAdults;

  @Min(value = 0, message = "Number of children must not be negative")
  private Integer numberOfChildren;

  @NotBlank(message = "Guest name cannot be blank")
  private String guestName;
  private String roomNumber;

  @NotNull(message = "Booking should be associated with a room")
  private Long roomId;

  @NotNull(message = "Total price cannot be null")
  @Min(value = 0, message = "Total price must not be negative")
  private BigDecimal totalPrice;
  private Long numberOfNights;
}
