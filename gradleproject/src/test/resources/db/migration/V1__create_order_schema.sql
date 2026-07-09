CREATE TABLE order_statuses (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE
);

INSERT INTO order_statuses (name) VALUES
    ('NEW'),
    ('PAID'),
    ('SHIPPED'),
    ('REFUNDED');

CREATE TABLE retail_orders (
    id BIGSERIAL PRIMARY KEY,
    status VARCHAR(50) NOT NULL,
    total_paise BIGINT NOT NULL DEFAULT 0,
    ordered_on DATE NOT NULL DEFAULT CURRENT_DATE,
    refunded BOOLEAN NOT NULL DEFAULT FALSE
);

CREATE TABLE retail_order_items (
    id BIGSERIAL PRIMARY KEY,
    order_id BIGINT NOT NULL REFERENCES retail_orders(id),
    sku VARCHAR(100) NOT NULL,
    quantity INTEGER NOT NULL DEFAULT 1
);
