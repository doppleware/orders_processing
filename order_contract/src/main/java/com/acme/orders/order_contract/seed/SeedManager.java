package com.acme.orders.order_contract.seed;

import org.postgresql.ds.PGSimpleDataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class SeedManager {


    public SeedManager() {
        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setUrl("jdbc:postgresql://a:5432/postgres");
        dataSource.setUser("a");
        dataSource.setPassword("a!");
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        seedDatabase(jdbcTemplate,100,100);
    }

    public void seedDatabase(JdbcTemplate jdbcTemplate, int customerCount, int orderCount) {
        System.out.println("Seeding database with complex relationships...");

        Map<Long, List<Long>> customerOrders = new HashMap<>();
        Map<Long, List<Long>> orderPayments = new HashMap<>();
        Map<Long, List<Long>> orderShippings = new HashMap<>();

        // Insert Customers
        StringBuilder customerSql = new StringBuilder("INSERT INTO customer (name, email, phone_number) VALUES ");
        List<Long> customerIds = new ArrayList<>();
        for (int i = 0; i < customerCount; i++) {
            customerSql.append(String.format("('%s', '%s', '%s')%s",
                    fakeName(), fakeEmail(), fakePhone(),
                    (i == customerCount - 1) ? ";" : ","));
            customerIds.add((long) (i + 1));
        }
        jdbcTemplate.execute(customerSql.toString());
        System.out.println("Inserted Customers");

        // Insert Payments (1 payment can be used for multiple orders)
        StringBuilder paymentSql = new StringBuilder("INSERT INTO payment (method, transaction_id, amount) VALUES ");
        List<Long> paymentIds = new ArrayList<>();
        for (int i = 0; i < customerCount * 2; i++) { // More payments to be reused
            paymentSql.append(String.format("('%s', '%s', %.2f)%s",
                    fakePaymentMethod(), UUID.randomUUID(), fakeAmount(),
                    (i == customerCount * 2 - 1) ? ";" : ","));
            paymentIds.add((long) (i + 1));
        }
        jdbcTemplate.execute(paymentSql.toString());
        System.out.println("Inserted Payments");

        // Insert Shipping Records (1 order can have multiple shipping records)
        StringBuilder shippingSql = new StringBuilder("INSERT INTO shipping (address, city, postal_code, country) VALUES ");
        List<Long> shippingIds = new ArrayList<>();
        for (int i = 0; i < customerCount * 3; i++) { // More shipping records to be reused
            shippingSql.append(String.format("('%s', '%s', '%s', '%s')%s",
                    fakeAddress(), fakeCity(), fakeZip(), fakeCountry(),
                    (i == customerCount * 3 - 1) ? ";" : ","));
            shippingIds.add((long) (i + 1));
        }
        jdbcTemplate.execute(shippingSql.toString());
        System.out.println("Inserted Shipping Records");

        // Insert Orders (One customer can have multiple orders)
        StringBuilder orderSql = new StringBuilder("INSERT INTO order_contract (customer_id, shipping_id, payment_id, status, created_at, modified_at) VALUES ");
        List<Long> orderIds = new ArrayList<>();
        for (int i = 0; i < orderCount; i++) {
            Long customerId = customerIds.get(randomIndex(customerIds));
            Long paymentId = paymentIds.get(randomIndex(paymentIds)); // Reusing payment
            Long shippingId = shippingIds.get(randomIndex(shippingIds)); // Reusing shipping

            customerOrders.computeIfAbsent(customerId, k -> new ArrayList<>()).add((long) (i + 1));
            orderPayments.computeIfAbsent((long) (i + 1), k -> new ArrayList<>()).add(paymentId);
            orderShippings.computeIfAbsent((long) (i + 1), k -> new ArrayList<>()).add(shippingId);

            orderSql.append(String.format("(%d, %d, %d, '%s', NOW(), NOW())%s",
                    customerId, shippingId, paymentId, fakeStatus(),
                    (i == orderCount - 1) ? ";" : ","));
            orderIds.add((long) (i + 1));
        }
        jdbcTemplate.execute(orderSql.toString());
        System.out.println("Inserted Orders");

        // Insert Extra Payments Per Order (Some orders have multiple payments)
        StringBuilder extraPaymentSql = new StringBuilder("INSERT INTO order_contract (customer_id, shipping_id, payment_id, status, created_at, modified_at) VALUES ");
        for (Long orderId : orderIds) {
            if (ThreadLocalRandom.current().nextBoolean()) { // 50% chance of extra payment
                Long paymentId = randomId(paymentIds);
                orderPayments.get(orderId).add(paymentId);
                extraPaymentSql.append(String.format("(%d, %d, %d, '%s', NOW(), NOW())%s",
                        randomId(customerIds), randomId(shippingIds), paymentId, fakeStatus(),
                        (orderId.equals(orderIds.get(orderIds.size() - 1))) ? ";" : ","));
            }
        }

        jdbcTemplate.execute(extraPaymentSql.toString());
        System.out.println("Inserted Extra Payments");

        // Insert Order Items (Multiple items per order)
        StringBuilder itemSql = new StringBuilder("INSERT INTO order_item (product_name, quantity, price, order_contract_id) VALUES ");
        for (Long orderId : orderIds) {
            int itemsPerOrder = ThreadLocalRandom.current().nextInt(2, 6); // 2 to 5 items per order
            for (int j = 0; j < itemsPerOrder; j++) {
                itemSql.append(String.format("('%s', %d, %.2f, %d)%s",
                        fakeProduct(), randomQuantity(), fakeAmount(), orderId,
                        (orderId.equals(orderIds.get(orderIds.size() - 1)) && j == itemsPerOrder - 1) ? ";" : ","));
            }
        }
        jdbcTemplate.execute(itemSql.toString());
        System.out.println("Inserted Order Items");

        System.out.println("Database seeding complete with complex relationships!");
    }

    // Utility methods for fake data
    private String fakeName() {
        return "User_" + UUID.randomUUID().toString().substring(0, 5);
    }

    private String fakeEmail() {
        return "user" + ThreadLocalRandom.current().nextInt(1000, 9999) + "@example.com";
    }

    private String fakePhone() {
        return "+1-555-" + ThreadLocalRandom.current().nextInt(1000, 9999);
    }

    private String fakePaymentMethod() {
        return ThreadLocalRandom.current().nextBoolean() ? "Credit Card" : "PayPal";
    }

    private double fakeAmount() {
        return ThreadLocalRandom.current().nextDouble(10, 500);
    }

    private String fakeAddress() {
        return "Street " + ThreadLocalRandom.current().nextInt(1, 100);
    }

    private String fakeCity() {
        return "City_" + ThreadLocalRandom.current().nextInt(1, 100);
    }

    private String fakeZip() {
        return String.valueOf(ThreadLocalRandom.current().nextInt(10000, 99999));
    }

    private String fakeCountry() {
        return ThreadLocalRandom.current().nextBoolean() ? "USA" : "Canada";
    }

    private String fakeStatus() {
        return ThreadLocalRandom.current().nextBoolean() ? "Shipped" : "Pending";
    }

    private String fakeProduct() {
        return "Product_" + ThreadLocalRandom.current().nextInt(1, 100);
    }

    private int randomQuantity() {
        return ThreadLocalRandom.current().nextInt(1, 5);
    }

    private int randomIndex(List<Long> list) {
        return ThreadLocalRandom.current().nextInt(0, list.size());
    }

    private long randomId(List<Long> list) {
        return list.get(ThreadLocalRandom.current().nextInt(list.size()));
    }
}