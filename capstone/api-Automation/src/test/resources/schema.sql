CREATE TABLE bookings
(
    id VARCHAR(50) PRIMARY KEY,

    journey_type VARCHAR(50),

    inventory_id VARCHAR(100),

    state VARCHAR(50),

    refundable BOOLEAN,

    pnr VARCHAR(50)

);