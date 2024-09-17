CREATE TABLE customer (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255),
    email VARCHAR(255) UNIQUE NOT NULL,
    phone_number VARCHAR(50)
);

CREATE TABLE payment (
    id SERIAL PRIMARY KEY,
    method VARCHAR(50),
    transaction_id VARCHAR(255),
    amount DECIMAL(10,2)
);

CREATE TABLE shipping (
    id SERIAL PRIMARY KEY,
    address VARCHAR(255),
    city VARCHAR(255),
    postal_code VARCHAR(20),
    country VARCHAR(50)
);
CREATE TABLE order_contract (
    id SERIAL PRIMARY KEY,
    customer_id BIGINT,
    shipping_id BIGINT,
    payment_id BIGINT,
    status VARCHAR(50),
    FOREIGN KEY (customer_id) REFERENCES customer(id),
    FOREIGN KEY (shipping_id) REFERENCES shipping(id),
    FOREIGN KEY (payment_id) REFERENCES payment(id)
);

CREATE TABLE order_item (
    id SERIAL PRIMARY KEY,
    product_name VARCHAR(255),
    quantity INT,
    price DECIMAL(10,2),
    order_contract_id BIGINT,
    FOREIGN KEY (order_contract_id) REFERENCES order_contract(id)
);


