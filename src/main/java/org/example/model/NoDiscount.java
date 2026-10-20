package org.example.model;

public class NoDiscount extends Discount {
    public NoDiscount() {
        super("NONE");
    }


    @Override
    public double apply(double originalAmount) {
        // TODO
        //price stays same ,no discount
        return originalAmount;
    }
}
