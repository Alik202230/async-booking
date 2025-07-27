package am.itspace.booking.service;

import am.itspace.booking.dto.CreateRoomRequest;
import am.itspace.booking.dto.CreateRoomResponse;

public interface RoomService {

  CreateRoomResponse createRoom(CreateRoomRequest request) throws PreferenceNotFoundException;

}
