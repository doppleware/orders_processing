package com.acme.orders.order_contract.common;
import java.util.concurrent.locks.ReentrantLock;

import java.security.SecureRandom;

public class HashLogic {
    private static final ReentrantLock lock = new ReentrantLock();

    public static double  hashStrongRandom(SecureRandom random) {
        // Create two large matrices for multiplication
        int size = 200; // Adjust size for increased load
        double[][] matrixA = new double[size][size];
        double[][] matrixB = new double[size][size];
        double[][] result = new double[size][size];

        lockRandomizer();
        // Initialize matrices with random values
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                matrixA[i][j] = random.nextDouble();
                matrixB[i][j] = random.nextDouble();
            }
        }

        // Perform matrix multiplication
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                result[i][j] = 0;
                for (int k = 0; k < size; k++) {
                    result[i][j] += matrixA[i][k] * matrixB[k][j];
                }
            }
        }


        return result[random.nextInt(size)][random.nextInt(size)];

        // Print a part of the result to ensure computation
    }

    private static void lockRandomizer() {
        lock.lock();
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        finally {
            lock.unlock();

        }
    }
}
