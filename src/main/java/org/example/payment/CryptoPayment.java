package org.example.payment;

import org.example.model.PaymentResult;

public class CryptoPayment extends PaymentMethod {
    private final String walletAddress;

    public CryptoPayment(String walletAddress) {
        super("Crypto");
        this.walletAddress = walletAddress;
    }

    @Override
    public PaymentResult processPayment(double amount) {
        if (walletAddress == null || walletAddress.isBlank()) {
            return new PaymentResult(false, "Wallet address must not be empty.");
        }
        if (!walletAddress.startsWith("0x") || walletAddress.length() != 42) {
            return new PaymentResult(false, "Wallet address must start with 0x and have 42 characters.");
        }

        return new PaymentResult(true, "Paid " + amount + " using crypto wallet " + walletAddress.substring(0, 6) + "...");
    }
}
