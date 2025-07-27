package am.itspace.booking.repository;

import am.itspace.booking.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoomRepository extends JpaRepository<Room, Long> {

  Optional<Room> findByRoomNumber(String roomNumber);

}
