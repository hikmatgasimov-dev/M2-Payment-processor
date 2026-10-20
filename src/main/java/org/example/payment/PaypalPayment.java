package org.example.payment;

import org.example.model.PaymentResult;

public class PaypalPayment extends PaymentMethod {
    private final String email;

    public PaypalPayment(String email) {
        super("PayPal");
        this.email = email;
    }

    @Override
    public PaymentResult processPayment(double amount) {
        if (email == null || email.isBlank()) {
            return new PaymentResult(false, "Email must not be empty.");
        }


        int at = email.indexOf("@");
        int dot = email.lastIndexOf(".");
        if (email.contains(" ") || at < 1 || dot < at + 2 || dot == email.length() - 1) {
            return new PaymentResult(false, "Email is not valid.");
        }

        return new PaymentResult(true, "Paid " + amount + " using PayPal account " + email);
    }
}
