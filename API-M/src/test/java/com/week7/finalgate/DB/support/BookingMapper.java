package com.week7.finalgate.DB.support;

import com.week7.finalgate.API.models.BookingResponse;
import com.week7.finalgate.DB.model.BookingRecord;

import java.sql.Timestamp;
import java.time.Instant;

public class BookingMapper {

    private BookingMapper() {
    }

    public static BookingRecord fromResponse(
            BookingResponse response
    ) {

        BookingRecord record =
                new BookingRecord();

        record.setBookingUuid(
                response.getId()
        );

        record.setPnr(
                response.getPnr()
        );

        record.setEmpId(
                response.getEmpId()
        );

        record.setJourneyType(
                response.getJourneyType()
        );

        record.setInventoryId(
                response.getInventoryId()
        );

        record.setBookingState(
                response.getState()
        );

        record.setAmountPaise(
                response.getAmountPaise()
        );

        record.setRefundable(
                response.isRefundable()
        );

        if (response.getHoldExpiresAt() != null) {

            record.setHoldExpiresAt(
                    Timestamp.from(
                            Instant.parse(
                                    response.getHoldExpiresAt()
                            )
                    )
            );

        }

        return record;

    }

}