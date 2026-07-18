package com.api.factory;

import com.api.builder.BookingBuilder;
import com.api.model.Booking;
import com.api.repository.BookingRepository;

public class BookingFactory {

    private final BookingRepository repository;

    public BookingFactory(
            BookingRepository repository) {

        this.repository = repository;
    }

    public long persisted() {

        Booking booking =
                BookingBuilder.newBooking()
                        .build();

        return repository.save(booking);
    }

    public long persisted(BookingBuilder builder) {

        return repository.save(
                builder.build()
        );
    }
}