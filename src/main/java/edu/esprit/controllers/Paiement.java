package edu.esprit.controllers;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.*;
import com.stripe.param.PaymentIntentCreateParams;
import com.stripe.param.PaymentMethodAttachParams;
import com.stripe.param.PaymentMethodCreateParams;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import java.net.URL;
import java.util.*;
import java.util.regex.Pattern;
public class Paiement  implements Initializable {

    @FXML
    private TextField carte;

    @FXML
    private TextField cvc;

    @FXML
    private TextField description;

    @FXML
    private TextField email;

    @FXML
    private TextField month;

    @FXML
    private TextField prix;

    @FXML
    private TextField year;

    @FXML
    private TextField key;
    /**
     * Initializes the controller class.
     */


    public void setPrix(String prix) {
        this.prix.setText(prix);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        this.prix.setText(String.valueOf(80));


    }
    private boolean controleDeSaisi() {

        if (carte.getText().isEmpty() || month.getText().isEmpty() || year.getText().isEmpty()
                || cvc.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Données erronés", "Verifier les données", "Veuillez bien remplir tous les champs !");
            return false;
        } else {

            if (!Pattern.matches("[0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9]", carte.getText())) {
                showAlert(Alert.AlertType.ERROR, "Données erronés", "Verifier les données", "Vérifiez la reference ! ");
                carte.requestFocus();
                carte.selectEnd();
                return false;
            }

            if (!Pattern.matches("[0-9][0-9]", month.getText())) {
                showAlert(Alert.AlertType.ERROR, "Données ", "Verifier les données", "Vérifiez le Mois ! ");
                month.requestFocus();
                month.selectEnd();
                return false;
            }
            if (!Pattern.matches("[0-9][0-9]", year.getText())) {
                showAlert(Alert.AlertType.ERROR, "Données ", "Verifier les données", "Vérifiez l'année ! ");
                year.requestFocus();
                year.selectEnd();
                return false;
            }
            if (!Pattern.matches("[0-9]*", cvc.getText())) {
                showAlert(Alert.AlertType.ERROR, "Données ", "Verifier les données", "Vérifiez le cvc ! ");
                cvc.requestFocus();
                cvc.selectEnd();
                return false;
            }

        }
        // return true;


        return true;
    }

    public static void showAlert(Alert.AlertType type, String title, String header, String text) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(text);
        alert.showAndWait();

    }

    @FXML
    private void valider(ActionEvent event) throws StripeException  {
      /*  try {*/
            if (controleDeSaisi()) {
                if (carte.getText().isEmpty()) {
                    carte.setText("");
                }
                if (month.getText().isEmpty()) {
                    month.setText("");
                }
                if (year.getText().isEmpty()) {
                    year.setText("");
                }
                if (cvc.getText().isEmpty()) {
                    cvc.setText("");
                }
            }

            Stripe.apiKey = "sk_test_51KrVQ5IXjPg5Xb6hm4Wrb0yFLMtMZ13tk4JV4znVJYb3xU4f4SMRCUPSeglrlXEZriWFniholkwYvQE2yQ5eGv5800HHlAQCbs"; // add your API key

            // Configuration de la carte de crédit
           /* Map<String, Object> cardParams = new HashMap<>();
            cardParams.put("number", carte.getText());
            cardParams.put("exp_month", month.getText());
            cardParams.put("exp_year", year.getText());
            cardParams.put("cvc", cvc.getText());
            Map<String, Object> tokenParams = new HashMap<>();
           tokenParams.put("card", cardParams);
           Token token = Token.create(tokenParams);*/
          //  Token token = Token.create(Collections.singletonMap("card", "tok_visa")); // Utilisez le token de test correspondant à votre besoin


            Map<String, Object> params = new HashMap<>();
            params.put("description", description.getText());
            params.put("email", email.getText());
            //params.put("source", token.getId()); // Utilisez l'ID du token comme source de paiement
            params.put("source", "tok_us");
            Customer customer = Customer.create(params);
            PaymentIntentCreateParams chargeparams = PaymentIntentCreateParams.builder()
                    .setCustomer(customer.getId())
                    .setAmount(Long.parseLong(prix.getText()) * 100)
                    .setCurrency("usd")
                    .setConfirm(false)
                    .build();
            PaymentIntent paymentIntent = PaymentIntent.create(chargeparams);
        System.out.println("Statut du PaymentIntent : " + paymentIntent.getStatus());
        //PaymentIntent confirmedPaymentIntent = paymentIntent.confirm();
      //  System.out.println("Statut du PaymentIntent : " + confirmedPaymentIntent.getStatus());

        showAlert(Alert.AlertType.CONFIRMATION, "Données Valide", "Success", "Payment avec succes!");

        } /*catch (StripeException ex) {
            System.out.println("stripe");
            showAlert(Alert.AlertType.ERROR, "Erreur de Paiement", "Erreur Stripe", "Une erreur s'est produite lors du traitement du paiement. Veuillez réessayer plus tard.");
        }*/


    @FXML
    private void validerKey(ActionEvent event) throws StripeException {
        Stripe.apiKey = "sk_test_51KrVQ5IXjPg5Xb6hm4Wrb0yFLMtMZ13tk4JV4znVJYb3xU4f4SMRCUPSeglrlXEZriWFniholkwYvQE2yQ5eGv5800HHlAQCbs";

        String customerId = key.getText();
        Customer customer = Customer.retrieve(customerId);
        Customer.InvoiceSettings invoiceSettings = customer.getInvoiceSettings();
        String defaultPaymentMethodId = invoiceSettings.getDefaultPaymentMethod();

        PaymentIntentCreateParams params = PaymentIntentCreateParams.builder()
                .setCustomer(key.getText())
                .setAmount(Long.parseLong(prix.getText()) * 100)
                .setCurrency("usd")
                .setPaymentMethod(defaultPaymentMethodId)
                .setConfirm(false)
                .build();

        PaymentIntent paymentIntent = PaymentIntent.create(params);


        showAlert(Alert.AlertType.CONFIRMATION, "Données Valide", "Success", "Payment avec succes!");
    }


}







