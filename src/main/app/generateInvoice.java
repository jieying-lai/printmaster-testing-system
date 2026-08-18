package app;

import java.text.DecimalFormat;

/**
 * generateInvoice.java
 * Generates formatted invoice details.
 */
public class generateInvoice {
    private DecimalFormat df = new DecimalFormat("0.00");

    public String generateInvoice(printOrder order) {
        if (order == null) {
            return "Error: No print order provided.";
        }

        StringBuilder sb = new StringBuilder();
        sb.append("===========================================\n");
        sb.append("             PRINTMASTER INVOICE           \n");
        sb.append("===========================================\n");

        customer c = order.getCustomerDetails();
        if (c != null) {
            sb.append("Customer ID   : ").append(c.getId()).append("\n");
            sb.append("Customer Name : ").append(c.getName()).append("\n");
            sb.append("Email         : ").append(c.getEmail()).append("\n");
            sb.append("Phone Number  : ").append(c.getPhoneNumber()).append("\n");
            sb.append("Customer Type : ").append(c.getCustomerType()).append("\n");
        } else {
            sb.append("Customer Info : N/A\n");
        }
        sb.append("-------------------------------------------\n");
        sb.append("ORDER DETAILS:\n");
        sb.append("Paper Size    : ").append(order.getPaperSize()).append("\n");
        sb.append("Print Type    : ").append(order.getPrintType()).append("\n");
        sb.append("Printing Side : ").append(order.getPrintingSide()).append("\n");
        sb.append("Pages         : ").append(order.getNumOfPages()).append("\n");
        sb.append("Copies        : ").append(order.getNumOfCopies()).append("\n");
        sb.append("Binding       : ").append(order.getBindingOptions()).append("\n");
        sb.append("Lamination    : ").append(order.isLamination() ? "Yes" : "No").append("\n");
        sb.append("Express Print : ").append(order.isExpressPrinting() ? "Yes" : "No").append("\n");
        sb.append("-------------------------------------------\n");
        sb.append("CHARGES BREAKDOWN:\n");
        sb.append("Base Printing Charge  : RM ").append(df.format(order.getBasePrintingCharge())).append("\n");
        sb.append("Optional Service Fee  : RM ").append(df.format(order.getAdditionalServiceCharges())).append("\n");
        sb.append("Discount Amount       : RM ").append(df.format(order.getDiscounts())).append("\n");
        sb.append("-------------------------------------------\n");
        sb.append("TOTAL AMOUNT PAYABLE  : RM ").append(df.format(order.getTotalPrintingCharge())).append("\n");
        sb.append("===========================================\n");

        return sb.toString();
    }
}
