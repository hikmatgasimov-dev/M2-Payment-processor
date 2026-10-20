package org.example.payment;

import org.example.model.PaymentResult;

public class CreditCardPayment extends PaymentMethod {
    private final String cardNumber;
    private final String cardHolderName;

    public CreditCardPayment(String cardNumber, String cardHolderName) {
        super("CreditCard");
        this.cardNumber = cardNumber;
        this.cardHolderName = cardHolderName;
    }

    @Override
    public PaymentResult processPayment(double amount) {
        if (cardHolderName == null || cardHolderName.isBlank()) {

            return new PaymentResult(false, "Card holder must not be empty");
        }
        if (cardNumber == null) {
            return new PaymentResult(false, "Card number must not be empty");
        }
        // spaces are allowed in the input
        String digits = cardNumber.replace(" ", "");
        if (digits.length() != 16) {

            return new PaymentResult(false, "Card number must have 16 digits");
        }
        for(int i=0;i<digits.length();i++){

            if(!Character.isDigit(digits.charAt(i))){
                return new PaymentResult(false, "Card number must contain digits");
            }
        }

        return new PaymentResult(true, "Paid " + amount + " using credit card ending with " + digits.substring(12));
    }
}
