package am.itspace.booking.mapper;

import am.itspace.booking.dto.CreateRoomResponse;
import am.itspace.booking.model.Room;

import java.util.function.Function;

public final class RoomMapper {

  private RoomMapper() {}

  public static final Function<Room, CreateRoomResponse> toCreateRoomResponse = room ->
      CreateRoomResponse.builder()
          .id(room.getId())
          .roomNumber(room.getRoomNumber())
          .baseDailyRate(room.getBaseDailyRate())
          .maxRoomCapacity(room.getMaxRoomCapacity())
          .pricePerAdult(room.getPricePerAdult())
          .pricePerChild(room.getPricePerChild())
          .build();

}
