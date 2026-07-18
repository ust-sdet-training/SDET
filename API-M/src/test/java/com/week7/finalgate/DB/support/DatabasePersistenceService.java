package com.week7.finalgate.DB.support;

import com.week7.finalgate.API.models.BookingResponse;
import com.week7.finalgate.DB.model.BookingRecord;
import com.week7.finalgate.DB.repository.BookingRepository;
import com.week7.finalgate.DB.repository.BookingSeatRepository;

public class DatabasePersistenceService {

    private final BookingRepository bookingRepository =
            new BookingRepository();

    private final BookingSeatRepository seatRepository =
            new BookingSeatRepository();

    public void persist(BookingResponse response) {

        BookingRecord record =
                BookingMapper.fromResponse(response);

        bookingRepository.save(record);

        seatRepository.deleteSeats(response.getId());

        if (response.getSeatIds() != null &&
                !response.getSeatIds().isEmpty()) {

            seatRepository.saveSeats(
                    response.getId(),
                    response.getSeatIds()
            );
        }
    }
}
