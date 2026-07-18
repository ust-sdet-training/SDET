CREATE TABLE bookings
(
    id BIGINT AUTO_INCREMENT PRIMARY KEY,

    pnr VARCHAR(100),
    state VARCHAR(50),
    inventory_id VARCHAR(100),
    emp_id VARCHAR(50)
);