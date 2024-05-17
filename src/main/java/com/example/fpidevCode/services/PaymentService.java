package com.example.fpidevCode.services;
import com.example.fpidevCode.entities.Livraison;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import javafx.scene.control.Alert;

public class PaymentService {
    public void processPayment(long price, Livraison livraison) {
        try {
            // Set your secret key here
            Stripe.apiKey = "sk_test_51OpI4IIs5mCI5pYLsb9wSRMDaf7wyXJYt5qvdsTAVB7ZaEm9bbUbh7hfl1wGZ7uZQU3gXKgLu2mYUmeM3B7NCxa500D3kc1QWp";

            // Create a PaymentIntent with other payment details
            PaymentIntentCreateParams params = PaymentIntentCreateParams.builder()
                    .setAmount(price *100) // Amount in cents (e.g., $10.00)
                    .setCurrency("usd")
                    .build();
            PaymentIntent intent = PaymentIntent.create(params);



            // If the payment was successful, display a success message
            System.out.println("Payment successful. PaymentIntent ID: " + intent.getId());
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Payment Successful");
            alert.setHeaderText(null);
            alert.setContentText("Payment for the delivery of " + livraison.getClientName() + " was successful.");
            alert.showAndWait();
        } catch (StripeException e) {
            // If there was an error processing the payment, display the error message
            System.out.println("Payment failed. Error: " + e.getMessage());
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Payment Failed");
            alert.setHeaderText(null);
            alert.setContentText("Payment for the delivery of " + livraison.getClientName() + " failed. Please try again.");
            alert.showAndWait();
        }
    }
}

