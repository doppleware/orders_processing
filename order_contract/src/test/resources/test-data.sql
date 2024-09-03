-- src/test/resources/test-data.sql
DELETE FROM order_contract;

INSERT INTO order_contract (id, customer_id, contract_details)
VALUES (1, 100, 'Test Contract');

-- Add more test data as needed