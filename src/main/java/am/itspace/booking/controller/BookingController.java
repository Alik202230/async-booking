package am.itspace.booking.controller;

import am.itspace.booking.dto.CreateBookingRequest;
import am.itspace.booking.service.BookingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/bookings")
public class BookingController {

  private final BookingService bookingService;

  @PostMapping("/create")
  public ResponseEntity<String> booking(@RequestBody @Valid CreateBookingRequest request) {
    String uuid = UUID.randomUUID().toString();
    this.bookingService.createBookingAsync(request, uuid);
    return ResponseEntity.ok().body("TASK IS IN_PROCESS WITH ID: " + uuid);
  }

  @GetMapping("/status/{uuid}")
  public ResponseEntity<String> getBookingStatus(@PathVariable String uuid) {
    String status = this.bookingService.getBookingStatus(uuid);
    return ResponseEntity.ok().body(status);
  }

  @DeleteMapping("/cancel/{bookingId}")
  public ResponseEntity<Void> cancelBooking(@PathVariable Long bookingId) {
    this.bookingService.cancelBooking(bookingId);
    return ResponseEntity.ok().build();
  }

}
