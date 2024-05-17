package com.example.fpidevCode.controllers;

import com.example.fpidevCode.entities.evenement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import com.example.fpidevCode.services.event_service;
import com.example.fpidevCode.utils.DBConnection;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;

public class ModifierEvent {
    private Connection conn;

    @FXML
    private ComboBox<String> idE;
    @FXML
    private TextField nameE;
    @FXML
    private Label show;
    @FXML
    private TextField themeE;
    @FXML
    private Label EventA;

    @Deprecated
    void showT(MouseEvent event) {
        {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/tickets_show.fxml"));
            try {
                Parent root = loader.load();
                TicketsShow controller = loader.getController();
                show.getScene().setRoot(root);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Deprecated
    void buyT(MouseEvent event) {
        {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/tickets_add.fxml"));
            try {
                Parent root = loader.load();
                TicketsAdd controller = loader.getController();
                show.getScene().setRoot(root);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    void showE(MouseEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/event_show.fxml"));
        try {
            Parent root = loader.load();
            EventShow controller = loader.getController();
            show.getScene().setRoot(root);
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    @FXML
    void eventA(MouseEvent event) {
        {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Event_Add.fxml"));
            try {
                Parent root = loader.load();
                EventAdd controller = loader.getController();
                EventA.getScene().setRoot(root);
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }




    @FXML
    private Button upe;
    private final event_service es= new event_service();
    private evenement e =new evenement();
    {
        conn= DBConnection.getConnection();
    }
    @FXML
    void update(ActionEvent event) {
        {

            try {
                // Récupérer les valeurs des champs de texte
                int id = Integer.parseInt(String.valueOf(idE.getValue()));
                String nom = nameE.getText();
                String theme = themeE.getText();

                // Créer un nouvel compte avec les valeurs récupérées
                 e = new evenement(id,nom, theme);

                // Appeler la méthode de mise à jour du compte

                es.update(e);

                // Afficher une alerte en fonction du résultat de la mise à jour
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Success");
                alert.setContentText("Compte updated");
                alert.showAndWait();
            } catch (NumberFormatException e) {
                // Gérer l'exception si la conversion échoue
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Veuillez entrer des valeurs numériques valides.");
                alert.showAndWait();
            }


        }
    }
    @FXML
    void combo(ActionEvent event) {
        String selectedValue = String.valueOf(idE.getValue());
        if (selectedValue != null) {
            int idd = Integer.parseInt(selectedValue);

            evenement e=es.getOneById(idd);
            nameE.setText(e.getEvent_name());
            themeE.setText(e.getEvent_theme());

}}
    @FXML
    void initialize() {
        List<evenement> e = es.affichage();
        ObservableList<String> id = FXCollections.observableArrayList();
        for (evenement eve: e) {
            id.add(Integer.toString(eve.getEvent_id()));
        }
        idE.setItems(id);
}}
