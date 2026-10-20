package org.example.payment;

import org.example.model.PaymentResult;

public class GiftCardPayment extends PaymentMethod{
    private final String code;
    private double balance;

    public GiftCardPayment(String code, double balance) {

        super("GiftCard");
        this.code=code;
        this.balance=balance;

    }

    @Override
    public PaymentResult processPayment(double amount){
        if(code==null||code.isBlank()){
           return  new PaymentResult(false,"Gift card must not be empty");
        }

        if(amount>balance){
            return new PaymentResult(false,"Not enough balance on the gift card.Avaliable" +String.format("%.2f",balance));
        }
        balance-=amount;

        return new PaymentResult(true, "Paid " + String.format("%.2f", amount) + " using gift card. Balance left: " + String.format("%.2f", balance));
    }
}
