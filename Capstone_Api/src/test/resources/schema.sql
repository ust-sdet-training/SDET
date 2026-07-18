CREATE TABLE IF NOT EXISTS bookings
(
    id INT AUTO_INCREMENT PRIMARY KEY,

    booking_id VARCHAR(50),

    pnr VARCHAR(50),

    passenger_name VARCHAR(100),

    status VARCHAR(30)
);