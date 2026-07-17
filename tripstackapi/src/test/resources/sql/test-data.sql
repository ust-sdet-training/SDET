INSERT INTO users (emp_id, email, display_name, role)
VALUES
('1001', 'admin@tripstack.test', 'Admin User', 'admin'),
('1002', 'viewer@tripstack.test', 'Viewer User', 'viewer'),
('1017', 'quinn@tripstack.test', 'Quinn', 'traveller');


INSERT INTO bookings
(
    id,
    pnr,
    emp_id,
    inventory_id,
    journey_type,
    status,
    amount_paise,
    refundable
)
VALUES
(
    'BK-1017-0001',
    'TS-1017-0001',
    '1017',
    'FL-BOMGOI-001',
    'flight',
    'CONFIRMED',
    845000,
    TRUE
);


INSERT INTO payments
(
    booking_id,
    amount_paise,
    payment_status
)
VALUES
(
    'BK-1017-0001',
    845000,
    'SUCCESS'
);