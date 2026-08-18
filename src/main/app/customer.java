package app;

/**
 * customer.java
 * Holds customer information including customer ID, name, email address,
 * phone number, customer type, and order count.
 */
public class customer {
    private int id;
    private String name;
    private String email;
    private String phoneNumber;
    private String customerType;
    private int orderCount;

    // Default Constructor
    public customer() {
    }

    // Constructor with essential parameters
    public customer(int id, String name, String email, String phoneNumber) {
        this(id, name, email, phoneNumber, "Regular", 0);
    }

    // Full Constructor
    public customer(int id, String name, String email, String phoneNumber, String customerType, int orderCount) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.customerType = customerType;
        this.orderCount = orderCount;
    }

    // Retrieve Customer Information
    public customer retrieveCustomer(int id, String name, String email, String phoneNumber, String customerType, int orderCount) {
        return new customer(id, name, email, phoneNumber, customerType, orderCount);
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public String getCustomerType() { return customerType; }
    public void setCustomerType(String customerType) { this.customerType = customerType; }

    public int getOrderCount() { return orderCount; }
    public void setOrderCount(int orderCount) { this.orderCount = orderCount; }

    @Override
    public String toString() {
        return "Customer [ID=" + id + ", Name=" + name + ", Email=" + email + ", Phone=" + phoneNumber 
                + ", Type=" + customerType + ", OrderCount=" + orderCount + "]";
    }
}
