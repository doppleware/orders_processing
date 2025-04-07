import http from 'k6/http';
import { check, sleep } from 'k6';
import { Faker } from "k6/x/faker";

const faker = new Faker();

export const options = {
    vus: 10, // Number of virtual users
    duration: '30s', // Test duration
};

export default function () {
    const name = faker.person.firstName() + ' ' + faker.person.lastName(); // Generate random full name
    const email = faker.person.email(); // Generate random email

    const url = 'http://localhost:8089/api/orders-contracts/assess';
    const payload = JSON.stringify({
        customer: {
            name: name,
            email: email,
            phoneNumber: faker.person.phone(), // Generate random phone number
        },
        items: [
            {
                productName: faker.product.productName(), // Generate random product name
                quantity: 3, // Random quantity
                price: faker.payment.price(2,200), // Random price
            },
            {
                productName: faker.product.productName(),
                quantity: 4,
                price: faker.payment.price(2,200),
            }
        ],
        shipping: {
            address: faker.address.streetName(), // Random street address
            city: faker.address.city(), // Random city
            postalCode: faker.address.zip(), // Random postal code
            country: faker.address.country(), // Random country
        },
        payment: {
            method: 'Credit Card',
            cardNumber: faker.payment.creditCard().creditCardNumber, // Random credit card number
            expirationDate: faker.payment.creditCard().creditCardExp, // Random expiration date
            cvv: faker.payment.creditCard().creditCardCvv, // Random CVV
        },
        status: 'Pending',
        orderName: 'test'
    });

    const params = {
        headers: {
            'Content-Type': 'application/json',
        },
    };

    const res = http.post(url, payload, params);

    check(res, {
        'status is 200': (r) => r.status === 200,
    });

}