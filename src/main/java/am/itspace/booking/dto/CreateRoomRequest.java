package am.itspace.booking.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateRoomRequest {

  @NotBlank(message = "Room number cannot be blank")
  @Column(unique = true, nullable = false)
  private String roomNumber;

  @NotNull(message = "Base daily rate cannot be null or empty")
  @Min(value = 0, message = "Base daily rate must not be negative")
  private BigDecimal baseDailyRate;

  @NotBlank(message = "Price per adult cannot be null or empty")
  @Min(value = 0, message = "Price per adult should not be negative")
  private BigDecimal pricePerAdult;

  @NotBlank(message = "Price per child cannot be null or empty")
  @Min(value = 0, message = "Price per child should not be negative")
  private BigDecimal pricePerChild;

  @NotBlank(message = "Max room capacity cannot be null or empty")
  @Min(value = 0, message = "Max room capacity should not be negative")
  private Integer maxRoomCapacity;
  private Integer totalRating;
  private BigDecimal averageRating;
}
