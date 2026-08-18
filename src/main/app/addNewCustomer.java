package app;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.regex.Pattern;

/**
 * addNewCustomer.java
 * Adds a new customer to customer.txt with validations.
 */
public class addNewCustomer {
    private String filePath = "customer.txt";
    private static final String EMAIL_REGEX = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$";

    public addNewCustomer() {
    }

    public addNewCustomer(String filePath) {
        this.filePath = filePath;
    }

    public boolean registerCustomer(int id, String name, String email, String phoneNumber) {
        return registerCustomer(id, name, email, phoneNumber, "Regular", 0);
    }

    public boolean registerCustomer(int id, String name, String email, String phoneNumber, String customerType, int orderCount) {
        // Email regex check
        if (email == null || !Pattern.matches(EMAIL_REGEX, email)) {
            throw new IllegalArgumentException("Invalid email format.");
        }

        File file = new File(filePath);
        if (!file.exists()) {
            file = new File("src/main/customer.txt");
        }

        if (file.exists()) {
            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = br.readLine()) != null) {
                    line = line.trim();
                    if (line.isEmpty()) continue;
                    String[] parts = line.split(",");
                    if (parts.length >= 4) {
                        try {
                            int existingId = Integer.parseInt(parts[0].trim());
                            if (existingId == id) {
                                throw new IllegalArgumentException("Customer ID already exists.");
                            }
                        } catch (NumberFormatException e) {
                            // ignore
                        }
                        String existingEmail = parts[2].trim();
                        if (existingEmail.equalsIgnoreCase(email.trim())) {
                            throw new IllegalArgumentException("Email address already registered.");
                        }
                        String existingPhone = parts[3].trim();
                        if (existingPhone.equals(phoneNumber.trim())) {
                            throw new IllegalArgumentException("Phone number already registered.");
                        }
                    }
                }
            } catch (IOException e) {
                throw new IllegalArgumentException("Error checking customer data: " + e.getMessage());
            }
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file, true))) {
            String record = id + "," + name + "," + email + "," + phoneNumber + "," + customerType + "," + orderCount;
            bw.write(record);
            bw.newLine();
            return true;
        } catch (IOException e) {
            throw new IllegalArgumentException("Failed to register customer: " + e.getMessage());
        }
    }
}
