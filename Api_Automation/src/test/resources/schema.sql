CREATE TABLE bookings
(
    id VARCHAR(100) PRIMARY KEY,
    pnr VARCHAR(50),
    state VARCHAR(50),
    inventoryId VARCHAR(100),
    amountPaise INT
);