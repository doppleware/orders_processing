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

    const getContractUrl = 'http://localhost:8089/api/orders-contracts/1';

    const params = {
        headers: {
            'Content-Type': 'application/json',
        },
    };

    const res = http.get(getContractUrl, params);

    check(res, {
        'status is 200': (r) => r.status === 200,
    });

    sleep(1);
}