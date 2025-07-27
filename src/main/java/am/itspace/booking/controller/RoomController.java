package am.itspace.booking.controller;

import am.itspace.booking.dto.CreateRoomRequest;
import am.itspace.booking.dto.CreateRoomResponse;
import am.itspace.booking.service.RoomService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/rooms")
public class RoomController {

  private final RoomService roomService;

  @PostMapping("/create")
  public ResponseEntity<CreateRoomResponse> createBooking(@RequestBody @Valid CreateRoomRequest request) {
    CreateRoomResponse response = this.roomService.createRoom(request);
    return ResponseEntity.ok().body(response);
  }
}
