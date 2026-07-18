CREATE TABLE bookings (

    id VARCHAR(100) PRIMARY KEY,

    journey_type VARCHAR(20),

    inventory_id VARCHAR(100),

    seat_ids VARCHAR(255),

    state VARCHAR(20),

    refundable BOOLEAN,

    amount_paise INT,

    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP

);