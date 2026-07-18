INSERT IGNORE INTO bookings
(booking_id, pnr, source_city, destination_city, journey_date, total_amount, booking_status)
VALUES
(1, 'TS-1026-0001', 'LKO', 'BLR', '2026-08-12', 2500.00, 'CONFIRMED');

INSERT IGNORE INTO passengers
(passenger_id, booking_id, first_name, last_name, age, gender)
VALUES
(1, 1, 'Sai', 'Teja', 24, 'Male');

INSERT IGNORE INTO payments
(payment_id, booking_id, payment_method, payment_status, amount)
VALUES
(1, 1, 'UPI', 'SUCCESS', 2500.00);