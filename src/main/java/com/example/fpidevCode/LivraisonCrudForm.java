package com.example.fpidevCode;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;
import com.example.fpidevCode.entities.Livraison;
import com.example.fpidevCode.entities.Livreur;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import com.example.fpidevCode.services.LivraisonService;
import com.example.fpidevCode.services.LivreurService;

public class LivraisonCrudForm {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField addressField;

    @FXML
    private TextField commandeField;

    @FXML
    private DatePicker dateField;

    @FXML
    private ComboBox<String> livreurCombo;

    @FXML
    private TextField nomField;

    @FXML
    private ComboBox<String> statusCombo;
    private Livraison selectedLivraison;



    @FXML
    void openLivreur(MouseEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/LivreurCrud.fxml"));
        try {
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = (Stage) statusCombo.getScene().getWindow();
            stage.setTitle("Livreur");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    void goBack(){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/fpidev/LivraisonCrud.fxml"));
        try {
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = (Stage) statusCombo.getScene().getWindow();
            stage.setTitle("Livraison");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    void cancel(ActionEvent event) {
        goBack();
    }

    @FXML
    void submit(ActionEvent event) {
        LivraisonService livraisonService = new LivraisonService();
        LivreurService livreurService = new LivreurService();

        String nomDuClient = nomField.getText();
        String address = addressField.getText();
        String date = String.valueOf(dateField.getValue());
        String status = statusCombo.getValue();
        String commande = commandeField.getText();
        String livreur = livreurCombo.getValue();

        if (isValidInput(nomDuClient, address, date, status, commande, livreur)) {
            try {
                Livreur livreurSearch = livreurService.searchByName(livreur);

                if (selectedLivraison == null) {
                    Livraison livraison = new Livraison(nomDuClient, address, date, status, commande, livreurSearch.getLivreurId());
                    livraisonService.add(livraison);
                } else {
                    Livraison livraison = new Livraison(selectedLivraison.getLivraisonId(), nomDuClient, address, date, status, commande, livreurSearch.getLivreurId());
                    livraisonService.update(livraison);
                }

                goBack();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else {
            showAlert("Error", "Missing input", "Please fill in all the fields.");
        }
    }

    private boolean isValidInput(String... inputs) {
        for (String input : inputs) {
            if (input == null || input.trim().isEmpty()) {
                return false;
            }
        }
        return true;
    }

    private void showAlert(String title, String headerText, String contentText) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.showAndWait();
    }

    @FXML
    void initialize() {
        assert addressField != null : "fx:id=\"addressField\" was not injected: check your FXML file 'LivraisonCrudForm.fxml'.";
        assert commandeField != null : "fx:id=\"commandeField\" was not injected: check your FXML file 'LivraisonCrudForm.fxml'.";
        assert dateField != null : "fx:id=\"dateField\" was not injected: check your FXML file 'LivraisonCrudForm.fxml'.";
        assert livreurCombo != null : "fx:id=\"LivraisonCombo\" was not injected: check your FXML file 'LivraisonCrudForm.fxml'.";
        assert nomField != null : "fx:id=\"nomField\" was not injected: check your FXML file 'LivraisonCrudForm.fxml'.";
        assert statusCombo != null : "fx:id=\"statusCombo\" was not injected: check your FXML file 'LivraisonCrudForm.fxml'.";

        LivreurService livreurService = new LivreurService();
        try {
            List<Livreur> allLivreurs = livreurService.readAll();
            for (Livreur livreur : allLivreurs) {
                livreurCombo.getItems().add(livreur.getLivreurName());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void setLivraison(Livraison selected) {
        selectedLivraison = selected;

        nomField.setText(selectedLivraison.getClientName());
        addressField.setText(selectedLivraison.getDeliveryAddress());
        commandeField.setText(String.valueOf(selectedLivraison.getCommande()));
        statusCombo.setValue(selectedLivraison.getStatus());
        dateField.setValue(LocalDate.parse(selectedLivraison.getDeliveryDate()));

        LivreurService livreurService = new LivreurService();
        try {
            Livreur livreur = livreurService.searchById(selectedLivraison.getLivreurId());
            livreurCombo.setValue(livreur.getLivreurName());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
