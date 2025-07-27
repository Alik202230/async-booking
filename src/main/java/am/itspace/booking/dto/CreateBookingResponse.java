package am.itspace.booking.dto;

import am.itspace.booking.model.Room;
import am.itspace.booking.model.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateBookingResponse {

  private Long id;
  private LocalDate checkInDate;
  private LocalDate checkOutDate;
  private Integer numberOfAdults;
  private Integer numberOfChildren;
  private String guestName;
  private String roomNumber;
  private Room room;
  private BigDecimal totalPrice;
  private Status status;
  private Long numberOfNights;
}
