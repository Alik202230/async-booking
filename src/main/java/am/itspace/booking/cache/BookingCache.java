package am.itspace.booking.cache;

import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class BookingCache {

  private final Map<String, String> processingBooking = new ConcurrentHashMap<>();

  public void putInCache(String uuid, String inProcess) {
    processingBooking.put(uuid, inProcess);
  }

  public String getStatusFromCache(String uuid) {
    return processingBooking.getOrDefault(uuid, "NOT_FOUND");
  }
}