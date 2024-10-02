package com.acme.orders.order_processing.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutionException;
import com.acme.orders.order_contract.common.HashLogic;

@Component
public class HashProcessorBean {

    @Autowired
    private Executor taskExecutor;

    private final SecureRandom random = new SecureRandom();
    private final List<byte[]> largeDataStructure = new ArrayList<>();

    public String processHash(String input) {
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            try {
                return computeHashWithSalt(input);
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            }
        }, taskExecutor);

        try {
            return future.get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    public String computeHashWithSalt(String input) throws NoSuchAlgorithmException {
        // Perform a computationally intensive operation
        var randomizer = HashLogic.hashStrongRandom(random);
        performMatrixMultiplication();

        // Shared MessageDigest instance (not thread-safe)
        MessageDigest digest = MessageDigest.getInstance("SHA-256");

        // Generate a random salt (critical section)
        byte[] salt = new byte[16];
        synchronized (random) {
            random.nextBytes(salt);
        }

        // Synchronized block to modify large data structure
        synchronized (largeDataStructure) {
            largeDataStructure.add(salt);
            if (largeDataStructure.size() > 100) {
                largeDataStructure.clear();
            }
        }

        // Synchronized block to enforce serial access to the shared resource
        synchronized (digest) {
            // Combine input string with salt
            byte[] inputBytes = input.getBytes();
            byte[] saltedInput = new byte[inputBytes.length + salt.length];
            System.arraycopy(inputBytes, 0, saltedInput, 0, inputBytes.length);
            System.arraycopy(salt, 0, saltedInput, inputBytes.length, salt.length);

            // Convert input string to bytes
            byte[] hash = saltedInput;

            // Hash the input multiple times to increase computational load
            for (int i = 0; i < 10000; i++) {
                digest.update(hash);
                hash = digest.digest();
            }

            // Encode the final hash to a base64 string
            return Base64.getEncoder().encodeToString(hash);
        }
    }

    private void performMatrixMultiplication() {
        // Create two large matrices for multiplication
        int size = 200; // Adjust size for increased load
        double[][] matrixA = new double[size][size];
        double[][] matrixB = new double[size][size];
        double[][] result = new double[size][size];

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

        // Print a part of the result to ensure computation
        System.out.println("Matrix multiplication result element [0][0]: " + result[0][0]);
    }
}

@Configuration
class TaskExecutorConfig {

    @Bean(name = "taskExecutor")
    public Executor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(4);
        executor.setMaxPoolSize(4);
        executor.setQueueCapacity(10);
        executor.setThreadNamePrefix("HashProcessor-");
        executor.initialize();
        return executor;
    }
}