package am.itspace.booking.service.impl;

import am.itspace.booking.dto.CreateRoomRequest;
import am.itspace.booking.dto.CreateRoomResponse;
import am.itspace.booking.mapper.RoomMapper;
import am.itspace.booking.model.Room;
import am.itspace.booking.repository.RoomRepository;
import am.itspace.booking.service.RoomService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {

  private final RoomRepository roomRepository;

  @Override
  public CreateRoomResponse createRoom(CreateRoomRequest request)  {
    log.info("Creating room {}", request);
    Room room = Room.builder()
        .roomNumber(request.getRoomNumber())
        .baseDailyRate(request.getBaseDailyRate())
        .pricePerAdult(request.getPricePerAdult())
        .pricePerChild(request.getPricePerChild())
        .maxRoomCapacity(request.getMaxRoomCapacity())
        .build();

    Room savedRoom = this.roomRepository.save(room);


    log.info("Room {} created", savedRoom);

    return RoomMapper.toCreateRoomResponse.apply(savedRoom);
  }
}
