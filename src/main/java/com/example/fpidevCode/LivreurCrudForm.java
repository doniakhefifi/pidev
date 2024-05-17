package com.example.fpidevCode;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

import com.example.fpidevCode.entities.Livreur;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import com.example.fpidevCode.services.LivreurService;

public class LivreurCrudForm {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private DatePicker dateField;

    @FXML
    private TextField nomField;

    @FXML
    private TextField numField;

    @FXML
    private TextField salaireField;

    @FXML
    private ComboBox<String> typeCombo;

    private Livreur selectedLivreur;


    @FXML
    void openLivraison(MouseEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/fpidev/LivraisonCrudForm.fxml"));
        try {
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = (Stage) typeCombo.getScene().getWindow();
            stage.setTitle("Livreur");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    void goBack(){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/fpidev/LivreurCrud.fxml"));
        try {
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = (Stage) typeCombo.getScene().getWindow();
            stage.setTitle("Livreur");
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
        LivreurService livreurService = new LivreurService();

        String nom = nomField.getText();
        String contactNum = numField.getText();
        String date = String.valueOf(dateField.getValue());
        String type = typeCombo.getValue();
        String salaireText = salaireField.getText();

        if (isValidInput(nom, contactNum, date, type, salaireText)) {
            try {
                float salaire = Float.parseFloat(salaireText);

                if (selectedLivreur == null) {
                    Livreur livreur = new Livreur(nom, contactNum, date, type, salaire);
                    livreurService.add(livreur);
                } else {
                    Livreur livreur = new Livreur(selectedLivreur.getLivreurId(), nom, contactNum, date, type, salaire);
                    livreurService.update(livreur);
                }

                goBack();
            } catch (SQLException | NumberFormatException e) {
                showAlert("Error", "Invalid input for Salary", "Please enter a valid numeric value for Salary.");
            }
        } else {
            showAlert("Error", "Invalid Input", "Please fill in all the fields.");
        }
    }

    private boolean isValidInput(String nom, String contactNum, String date, String type, String salaireText) {
        return !nom.isEmpty() && !contactNum.isEmpty() && date != null && type != null && !salaireText.isEmpty();
    }

    private void showAlert(String title, String headerText, String contentText) {
        Alert alert = new Alert(Alert.AlertType.ERROR   );
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.showAndWait();
    }

    public void setLivreur(Livreur selected) {
        selectedLivreur = selected;

        nomField.setText(selectedLivreur.getLivreurName());
        numField.setText(selectedLivreur.getContactNumber());
        salaireField.setText(String.valueOf(selectedLivreur.getSalaire()));
        typeCombo.setValue(selectedLivreur.getTypeVehicule());
        dateField.setValue(LocalDate.parse(selectedLivreur.getDate()));
    }
}
