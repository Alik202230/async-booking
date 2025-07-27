package am.itspace.booking.model;

import am.itspace.booking.model.enums.Status;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PostLoad;
import jakarta.persistence.PostPersist;
import jakarta.persistence.PostUpdate;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Setter
@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Booking {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private LocalDate checkInDate;
  private LocalDate checkOutDate;
  private Integer numberOfAdults;
  private Integer numberOfChildren;
  private String guestName;

  @Transient
  private String roomNumber;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "room_id", nullable = false)
  private Room room;
  private BigDecimal totalPrice;

  @Enumerated(EnumType.STRING)
  private Status status;

  @Transient
  private Long numberOfNights;

  @PostLoad
  @PostPersist
  @PostUpdate
  private void calculateNumberOfNights() {
    if (checkInDate != null && checkOutDate != null) {
      this.numberOfNights = ChronoUnit.DAYS.between(checkInDate, checkOutDate);
    }
  }

  public String getRoomNumber() {
    return (this.room != null) ? this.room.getRoomNumber() : null;
  }

}
