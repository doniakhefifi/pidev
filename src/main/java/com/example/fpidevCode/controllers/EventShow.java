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
import java.sql.*;
import java.util.List;

public class EventShow {

    @FXML
    private ListView<evenement> ListView;
    @FXML
    private Label UpdateE;


    @FXML
    private Label addE;
    @FXML
    private Label showT;

    private event_service es = new event_service();
    @FXML
    private Button BtnHome;

    @FXML
    void addE(MouseEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Event_Add.fxml"));
        try {
            Parent root = loader.load();
            EventAdd controller = loader.getController();
            addE.getScene().setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    void comboptrie(ActionEvent event) {
        evenement ev = new evenement();
        event_service es = new event_service();
        List<evenement> ae = es.triNom();
       ListView.getItems().setAll(ae);
    }

    @FXML
    void updateE(MouseEvent event) {


        FXMLLoader loader = new FXMLLoader(getClass().getResource("/modifier_event.fxml"));
        try {
            Parent root = loader.load();
            ModifierEvent controller = loader.getController();
            UpdateE.getScene().setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    void showT(MouseEvent event) {
        {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/tickets_show.fxml"));
            try {
                Parent root = loader.load();
                TicketsShow controller = loader.getController();
                showT.getScene().setRoot(root);
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
                addE.getScene().setRoot(root);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private Connection conn;
    private java.awt.Label IdSupp;

    {
        conn = DBConnection.getConnection();
    }

    @FXML
    public void initialize() {
        // Créer une liste d'objets Produit
        ObservableList<evenement> items = FXCollections.observableArrayList();
        try {

            String req = "SELECT * FROM evenement";
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                evenement e = new evenement();
                e.setEvent_id(rs.getInt(1));
                e.setEvent_name(rs.getString("event_name"));
                e.setEvent_theme(rs.getString("event_theme"));
                // e.setEvent_date(rs.getInt("prix_produit"));
                items.add(e);
            }

            // Lier la liste à la ListView
            ListView.setItems(items);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        label.textProperty().addListener((obs, oldText, newText) -> {
            List<evenement> ae = es.recherche(newText);
            ListView.getItems().setAll(ae);

        });

    }

    @FXML
    private Button deleteE;

    @FXML
    private TextField label;
    private  static int s;

    @FXML
    void Onclick(MouseEvent event) {
        s = ListView.getSelectionModel().getSelectedItem().getEvent_id();
        System.out.println(s);
    }

    @FXML

    public void deleteEv(ActionEvent actionEvent) throws SQLException {
            //label.setText(String.valueOf(selectedItem));
            String sql = "DELETE  FROM evenement  WHERE event_id = ?";
            //int labelVallue = Integer.parseInt(label.getText());
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, s);
            preparedStatement.executeUpdate();
            System.out.println("Category Deleted successfully!");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setContentText("Product Deleted successfully!");
            alert.showAndWait();
            initialize();
            /*try {
                // Create a confirmation dialog
                Dialog<ButtonType> dialog = new Dialog<>();
                dialog.setTitle("Confirmation");
                dialog.setContentText("Are you sure you want to delete this product?");
                dialog.getDialogPane().getButtonTypes().addAll(ButtonType.YES, ButtonType.NO);

                // Show the dialog and wait for the admin's response
                Optional<ButtonType> result = dialog.showAndWait();

                // If the admin chooses "Yes", delete the product
                if (result.isPresent() && result.get() == ButtonType.YES) {

                    try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {

                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }*/

    }

    @FXML
    public void GoHome(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/fpidev/LivreurCrud.fxml"));
        this.label.getScene().setRoot(loader.load());
    }
}




