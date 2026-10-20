package org.example.model;

public class FixedAmountDiscount  extends  Discount{

    private final double amount;
    public FixedAmountDiscount(String code,double amount){

        super(code);
        if(amount<0){
            throw new IllegalArgumentException("Disocunt amount must not be negative");
        }
        this.amount=amount;
    }
    @Override
    public  double apply(double originalAmount){
        // 'apply' formula: Math.max(0,originalAmount - amount)

        return Math.max(0,originalAmount-amount);
    }
}
