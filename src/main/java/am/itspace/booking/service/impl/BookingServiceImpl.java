package am.itspace.booking.service.impl;

import am.itspace.booking.cache.BookingCache;
import am.itspace.booking.dto.CreateBookingRequest;
import am.itspace.booking.exception.BookingNotFoundException;
import am.itspace.booking.exception.CheckOutDateException;
import am.itspace.booking.exception.RoomCapacityException;
import am.itspace.booking.exception.RoomNotFoundException;
import am.itspace.booking.mapper.BookingMapper;
import am.itspace.booking.model.Booking;
import am.itspace.booking.model.Room;
import am.itspace.booking.model.enums.Status;
import am.itspace.booking.repository.BookingRepository;
import am.itspace.booking.repository.RoomRepository;
import am.itspace.booking.service.BookingService;
import am.itspace.booking.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private static final BigDecimal CHILD_DISCOUNT_PERCENTAGE = BigDecimal.valueOf(0.10);
    private final RoomRepository roomRepository;
    private final BookingRepository bookingRepository;
    private final BookingCache bookingCache;
    private final NotificationService notificationService;

    @Override
    @Async("bookingExecutor")
    public CompletableFuture<Void> createBookingAsync(CreateBookingRequest request, String uuid) {
        return CompletableFuture.supplyAsync(() -> {
                    try {
                        if (!request.getCheckInDate().isBefore(request.getCheckOutDate())) {
                            log.warn("Check-in date {} is before check-out date {}", request.getCheckInDate(), request.getCheckOutDate());
                            throw new CheckOutDateException("Check-in date must be before check-out date");
                        }

                        if (request.getCheckInDate().equals(request.getCheckOutDate())) {
                            log.warn("Check-in date {} is the same as check-out date {}", request.getCheckInDate(), request.getCheckOutDate());
                            throw new CheckOutDateException("Check-in date must not be the same as check-out date");
                        }

                        Room room = this.roomRepository.findByRoomNumber(request.getRoomNumber())
                                .orElseThrow(() -> new RoomNotFoundException("Room not found"));

                        int totalGuests = request.getNumberOfAdults() + request.getNumberOfChildren();
                        if (totalGuests > room.getMaxRoomCapacity()) {
                            log.warn("Total guests {} exceeds max capacity of the room {}", totalGuests, room.getMaxRoomCapacity());
                            throw new RoomCapacityException("Total guests must not exceed max capacity of the room");
                        }

                        Long numberOfNights = ChronoUnit.DAYS.between(request.getCheckInDate(), request.getCheckOutDate());

                        BigDecimal totalPrice = computeTotalPriceForBooking(request, room, numberOfNights);

                        Booking booking = Booking.builder()
                                .checkInDate(request.getCheckInDate())
                                .checkOutDate(request.getCheckOutDate())
                                .numberOfAdults(request.getNumberOfAdults())
                                .numberOfChildren(request.getNumberOfChildren())
                                .guestName(request.getGuestName())
                                .room(room)
                                .totalPrice(totalPrice)
                                .numberOfNights(numberOfNights)
                                .status(Status.PENDING)
                                .build();

                        Booking savedBooking = this.bookingRepository.save(booking);
                        bookingCache.putInCache(uuid, "IN_PROCESS");

                        return BookingMapper.toCreateBookingResponse.apply(savedBooking);
                    } catch (Exception e) {
                        log.error("Error booking processing", e);
                        throw e;
                    }
                })
                .thenRun(() -> this.notificationService.sendSuccessNotification(uuid, bookingCache))
                .exceptionally(throwable -> {
                    this.notificationService.sendFailureNotification(throwable.getMessage());
                    throw new CompletionException(throwable);
                });
    }

    @Override
    public String getBookingStatus(String uuid) {
        return bookingCache.getStatusFromCache(uuid);
    }


    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void cancelBooking(Long bookingId) {
        this.bookingRepository.findById(bookingId)
                .ifPresentOrElse(room -> {
                            this.bookingRepository.delete(room);
                            room.setStatus(Status.CANCELLED);
                        },
                        () -> {
                            log.error("Booking with id {} not found", bookingId);
                            throw new BookingNotFoundException("There is no booking with id " + bookingId);
                        });
    }

    private BigDecimal computeTotalPriceForBooking(CreateBookingRequest request, Room room, Long numberOfNights) {
        BigDecimal dailyAdultCost = room.getBaseDailyRate();
        if (request.getNumberOfAdults() > 1) {
            dailyAdultCost = dailyAdultCost.add(
                    room.getPricePerAdult().multiply(BigDecimal.valueOf(request.getNumberOfAdults() - 1))
            );
        }

        BigDecimal totalDailyRate = calculateTotalDailyRate(request, room, dailyAdultCost);
        BigDecimal totalCalculatedPrice = totalDailyRate.multiply(BigDecimal.valueOf(numberOfNights));
        return totalCalculatedPrice.setScale(2, RoundingMode.HALF_UP);
    }

    private BigDecimal calculateTotalDailyRate(CreateBookingRequest request, Room room, BigDecimal dailyAdultCost) {
        BigDecimal dailyChildCost = BigDecimal.ZERO;
        if (request.getNumberOfChildren() > 0) {
            BigDecimal baseChildCost = room.getPricePerChild().multiply(BigDecimal.valueOf(request.getNumberOfChildren()));
            BigDecimal childDiscountAmount = baseChildCost.multiply(CHILD_DISCOUNT_PERCENTAGE);
            dailyChildCost = baseChildCost.subtract(childDiscountAmount);
            dailyChildCost = dailyChildCost.setScale(2, RoundingMode.HALF_UP);
        }
        return dailyAdultCost.add(dailyChildCost);
    }
}
