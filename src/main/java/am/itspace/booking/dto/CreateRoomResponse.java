package am.itspace.booking.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateRoomResponse {

  private Long id;
  private String roomNumber;
  private BigDecimal baseDailyRate;
  private BigDecimal pricePerAdult;
  private BigDecimal pricePerChild;
  private Integer maxRoomCapacity;
}
