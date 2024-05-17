package com.example.fpidevCode.controllers;

import com.example.fpidevCode.entities.evenement;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import com.example.fpidevCode.services.event_service;

import java.io.IOException;

public class EventAdd {
    private final event_service es=new event_service();

    @FXML
    private Label showE;

    @FXML
    private TextField nameE;

    @FXML
    private TextField themeE;

    @Deprecated
    void showT(MouseEvent event) {
        {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/tickets_show.fxml"));
            try {
                Parent root = loader.load();
                TicketsShow controller = loader.getController();
                showE.getScene().setRoot(root);
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
                showE.getScene().setRoot(root);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    @FXML
    void showA(MouseEvent event) {
        {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/event_show.fxml"));
            try {
                Parent root = loader.load();
                EventShow controller = loader.getController();
                showE.getScene().setRoot(root);
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    @Deprecated
    void updateE(MouseEvent event) {
        {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/modifier_event.fxml"));
            try {
                Parent root = loader.load();
                ModifierEvent controller = loader.getController();
                showE.getScene().setRoot(root);
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }
    @FXML
    void addE(ActionEvent event)  {
        {
            try {

                String name = nameE.getText();
                String theme = themeE.getText();



                // Vérifier si les champs obligatoires ne sont pas vides
                if (name.isEmpty() || theme.isEmpty()) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Erreur");
                    alert.setContentText("Veuillez remplir tous les champs.");
                    alert.showAndWait();
                    return; // Sortir de la méthode si les champs obligatoires sont vides
                }



                // Si toutes les validations sont passées, ajouter le produit
                es.add(new evenement(name, theme));

                // Afficher une alerte d'information en cas de succès
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Succès");
                alert.setContentText("Le produit a été ajouté avec succès.");
                alert.showAndWait();
            } catch (IllegalArgumentException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setContentText(e.getMessage());
                alert.showAndWait();
            }
        }    }

}


