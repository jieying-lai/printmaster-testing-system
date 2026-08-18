package app;

import java.util.Arrays;
import java.util.List;

/**
 * printOrder.java
 * Holds print order details and enforces Business Rules 6, 7, 8, 9.
 */
public class printOrder {
    private customer customerDetails;
    private String printType;
    private String paperSize;
    private String printingSide;
    private int numOfPages;
    private int numOfCopies;
    private String bindingOptions;
    private boolean lamination;
    private boolean expressPrinting;

    private double basePrintingCharge;
    private double additionalServiceCharges;
    private double discounts;
    private double totalPrintingCharge;
    private String orderStatus;
    private String paymentStatus;

    private static final List<String> VALID_PAPER_SIZES = Arrays.asList("A3", "A4", "A5");
    private static final List<String> VALID_PRINT_TYPES = Arrays.asList("Black & White", "Colour");
    private static final List<String> VALID_PRINTING_SIDES = Arrays.asList("Single-sided", "Double-sided");
    private static final List<String> VALID_BINDINGS = Arrays.asList("None", "Staple", "Comb", "Spiral");

    public printOrder() {
        this.orderStatus = "Pending";
        this.paymentStatus = "Pending";
    }

    public printOrder(customer customerDetails, String printType, String paperSize, String printingSide,
                      int numOfPages, int numOfCopies, String bindingOptions, boolean lamination, boolean expressPrinting) {
        this.customerDetails = customerDetails;
        this.printType = printType;
        this.paperSize = paperSize;
        this.printingSide = printingSide;
        this.numOfPages = numOfPages;
        this.numOfCopies = numOfCopies;
        this.bindingOptions = (bindingOptions == null || bindingOptions.trim().isEmpty()) ? "None" : bindingOptions;
        this.lamination = lamination;
        this.expressPrinting = expressPrinting;
        this.orderStatus = "Pending";
        this.paymentStatus = "Pending";
    }

    public printOrder createPrintOrder(customer customerDetails, String printType, String paperSize, String printingSide,
                                        int numOfPages, int numOfCopies, String bindingOptions, boolean lamination, boolean expressPrinting) {
        printOrder order = new printOrder(customerDetails, printType, paperSize, printingSide, numOfPages, numOfCopies, bindingOptions, lamination, expressPrinting);
        order.validateOrder();
        return order;
    }

    public boolean validateOrder() {
        return validateOrder(this.customerDetails, this.printType, this.paperSize, this.printingSide,
                              this.numOfPages, this.numOfCopies, this.bindingOptions, this.lamination, this.expressPrinting);
    }

    public boolean validateOrder(customer customerDetails, String printType, String paperSize, String printingSide,
                                 int numOfPages, int numOfCopies, String bindingOptions, boolean lamination, boolean expressPrinting) {
        if (numOfPages < 1) {
            throw new IllegalArgumentException("Invalid page count: minimum 1 page required.");
        }
        if (numOfPages > 500) {
            throw new IllegalArgumentException("Invalid page count: maximum 500 pages allowed (Business Rule 7).");
        }

        if (numOfCopies < 1) {
            throw new IllegalArgumentException("Invalid copy count: minimum 1 copy required.");
        }
        if (numOfCopies > 1000) {
            throw new IllegalArgumentException("Invalid copy count: maximum 1,000 copies allowed (Business Rule 8).");
        }

        if (paperSize == null || !VALID_PAPER_SIZES.contains(paperSize)) {
            throw new IllegalArgumentException("Invalid paper size: must be A3, A4, or A5.");
        }

        if (printType == null || !VALID_PRINT_TYPES.contains(printType)) {
            throw new IllegalArgumentException("Invalid print type: must be Black & White or Colour.");
        }

        if (printingSide == null || !VALID_PRINTING_SIDES.contains(printingSide)) {
            throw new IllegalArgumentException("Invalid printing side: must be Single-sided or Double-sided.");
        }

        String binding = (bindingOptions == null) ? "None" : bindingOptions.trim();
        if (!VALID_BINDINGS.contains(binding)) {
            throw new IllegalArgumentException("Invalid binding option: only ONE of {None, Staple, Comb, Spiral} allowed (Business Rule 9).");
        }

        return true;
    }

    public customer getCustomerDetails() { return customerDetails; }
    public void setCustomerDetails(customer customerDetails) { this.customerDetails = customerDetails; }

    public String getPrintType() { return printType; }
    public void setPrintType(String printType) { this.printType = printType; }

    public String getPaperSize() { return paperSize; }
    public void setPaperSize(String paperSize) { this.paperSize = paperSize; }

    public String getPrintingSide() { return printingSide; }
    public void setPrintingSide(String printingSide) { this.printingSide = printingSide; }

    public int getNumOfPages() { return numOfPages; }
    public void setNumOfPages(int numOfPages) { this.numOfPages = numOfPages; }

    public int getNumOfCopies() { return numOfCopies; }
    public void setNumOfCopies(int numOfCopies) { this.numOfCopies = numOfCopies; }

    public String getBindingOptions() { return bindingOptions; }
    public void setBindingOptions(String bindingOptions) { this.bindingOptions = bindingOptions; }

    public boolean isLamination() { return lamination; }
    public void setLamination(boolean lamination) { this.lamination = lamination; }

    public boolean isExpressPrinting() { return expressPrinting; }
    public void setExpressPrinting(boolean expressPrinting) { this.expressPrinting = expressPrinting; }

    public double getBasePrintingCharge() { return basePrintingCharge; }
    public void setBasePrintingCharge(double basePrintingCharge) { this.basePrintingCharge = basePrintingCharge; }

    public double getAdditionalServiceCharges() { return additionalServiceCharges; }
    public void setAdditionalServiceCharges(double additionalServiceCharges) { this.additionalServiceCharges = additionalServiceCharges; }

    public double getDiscounts() { return discounts; }
    public void setDiscounts(double discounts) { this.discounts = discounts; }

    public double getTotalPrintingCharge() { return totalPrintingCharge; }
    public void setTotalPrintingCharge(double totalPrintingCharge) { this.totalPrintingCharge = totalPrintingCharge; }

    public String getOrderStatus() { return orderStatus; }
    public void setOrderStatus(String orderStatus) { this.orderStatus = orderStatus; }

    public String getPaymentStatus() { return paymentStatus; }
    public void setPaymentStatus(String paymentStatus) { this.paymentStatus = paymentStatus; }
}
