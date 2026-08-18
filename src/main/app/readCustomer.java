package app;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * readCustomer.java
 * Reads customer information from customer.txt.
 */
public class readCustomer {
    private String filePath = "customer.txt";

    public readCustomer() {
    }

    public readCustomer(String filePath) {
        this.filePath = filePath;
    }

    public customer getCustomerById(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("Customer ID non-existent or invalid format.");
        }

        File file = new File(filePath);
        if (!file.exists()) {
            file = new File("src/main/customer.txt");
        }
        if (!file.exists()) {
            file = new File("customer.txt");
        }

        if (!file.exists()) {
            throw new IllegalArgumentException("customer.txt data file not found.");
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;
                String[] parts = line.split(",");
                if (parts.length >= 4) {
                    try {
                        int cid = Integer.parseInt(parts[0].trim());
                        if (cid == id) {
                            String name = parts[1].trim();
                            String email = parts[2].trim();
                            String phone = parts[3].trim();
                            String type = (parts.length >= 5) ? parts[4].trim() : "Regular";
                            int orderCount = (parts.length >= 6) ? Integer.parseInt(parts[5].trim()) : 0;
                            return new customer(cid, name, email, phone, type, orderCount);
                        }
                    } catch (NumberFormatException e) {
                        // ignore
                    }
                }
            }
        } catch (IOException e) {
            throw new IllegalArgumentException("Error reading customer file: " + e.getMessage());
        }

        throw new IllegalArgumentException("Customer ID non-existent or invalid format.");
    }
}
