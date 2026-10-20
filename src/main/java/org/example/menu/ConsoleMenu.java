package org.example.menu;

import org.example.config.AppConfig;
import org.example.model.Order;
import org.example.model.OrderItem;
import org.example.model.PaymentResult;
import org.example.payment.PaymentMethod;
import org.example.payment.PaymentMethodFactory;
import org.example.payment.PaymentProcessor;

import java.util.Scanner;

public class ConsoleMenu {
    private final Scanner scanner = new Scanner(System.in);
    private final PaymentProcessor paymentProcessor = new PaymentProcessor();

    private Order currentOrder;

    public void start(){
        AppConfig config = AppConfig.getInstance();
        System.out.println("Welcome to " + config.getApplicationName());

        boolean running = true;
        while(running){
            printMenu();

            int option = readInt("Choose an option:");

            switch (option){
                case 1 -> createOrder();
                case 2 -> addItem();
                case 3 -> viewOrder();
                case 4 -> payOrder();
                case 0 -> {
                    System.out.println("Goodbye!");
                    running = false;
                }
                default -> System.out.println("Invalid option, please choose 0-4.");
            }
        }
    }

    private void createOrder(){
        System.out.println("Customer name:");
        String customerName = scanner.nextLine();

        try {
            currentOrder = Order.builder()
                    .customerName(customerName)
                    .build();
            System.out.println("Order created for " + currentOrder.getCustomerName());
        } catch (IllegalArgumentException e) {
            System.out.println("Could not create order: " + e.getMessage());
        }
    }

    private void addItem(){
        if (currentOrder == null) {
            System.out.println("Create an order first.");
            return;
        }

        System.out.println("Item name:");
        String itemName = scanner.nextLine();

        double price = readDouble("Price:");
        int quantity = readInt("Quantity:");

        try {
            currentOrder.addItem(new OrderItem(itemName, price, quantity));
            System.out.println("Item added to order");
        } catch (IllegalArgumentException | IllegalStateException e) {
            System.out.println("Could not add item: " + e.getMessage());
        }
    }

    private void viewOrder(){
        if (currentOrder == null) {
            System.out.println("Create an order first.");
            return;
        }


        System.out.println("Customer: " + currentOrder.getCustomerName());
        System.out.println("Status: " +  currentOrder.getStatus());
        System.out.println("Items:");

        for (OrderItem item : currentOrder.getItems()){
            System.out.println("- " + item);
        }

        System.out.println("Total: " + currentOrder.calculateTotal());
    }

    private void payOrder(){
        if (currentOrder == null) {
            System.out.println("Create an order first.");
            return;
        }

        System.out.println("""
                Select payment method:
                1. Credit Card
                2. PayPal
                3. Gift Card
                4. Crypto
                """);
        int option = readInt("Payment method:");

        PaymentMethod paymentMethod;
        try {
            paymentMethod = switch(option){
                case 1 -> createCreditCardPayment();
                case 2 -> createPaypalPayment();
                case 3 -> createGiftCardPayment();
                case 4 -> createCryptoPayment();
                default -> throw new IllegalArgumentException("Invalid payment method");
            };
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return;
        }

        PaymentResult result = paymentProcessor.process(currentOrder, paymentMethod);
        System.out.println(result.getMessage());
    }

    private PaymentMethod createCryptoPayment(){
        System.out.println("Wallet address:");
        String walletAddress = scanner.nextLine();

        return PaymentMethodFactory.createCryptoPayment(walletAddress);
    }

    private PaymentMethod createCreditCardPayment(){
        System.out.println("Card number:");
        String cardNumber =  scanner.nextLine();

        System.out.println("Card holder name:");
        String cardHolderName =  scanner.nextLine();

        return PaymentMethodFactory.createCreditCardPayment(cardNumber,cardHolderName);
    }

    private  PaymentMethod createPaypalPayment(){
        System.out.println("PayPal email:");
        String email = scanner.nextLine();

        return PaymentMethodFactory.createPaypalPayment(email);
    }

    private PaymentMethod createGiftCardPayment(){
        System.out.println("Gift card code:");
        String code = scanner.nextLine();

        double balance = readDouble("Gift card balance:");

        return PaymentMethodFactory.createGiftCardPayment(code, balance);
    }
    // asks again until the user types a whole number
    private int readInt(String prompt){
        while (true) {
            System.out.println(prompt);
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a whole number.");
            }
        }
    }
    // asks again until the user types a number
    private double readDouble(String prompt){
        while (true) {
            System.out.println(prompt);
            try {
                return Double.parseDouble(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a number, for example 12.5");
            }
        }
    }


    private void printMenu(){
        System.out.println("""
                1. Create order
                2. Add item to order
                3. View order
                4. Pay order
                0. Exit
                """);
    }
}
