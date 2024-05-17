package com.example.fpidevCode.controllers;

import com.example.fpidevCode.entities.tickets;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import com.example.fpidevCode.utils.DBConnection;

import java.io.IOException;
import java.sql.*;
import java.util.Optional;

public class TicketsShow {


    @FXML
    private ListView<tickets> listV;
    private Connection conn;
    private java.awt.Label IdSupp;
    @FXML
    private Label show;

    @FXML
    private TextField deleteT;

    @FXML
    private ImageView imqr;
    @FXML
    private Label EventA;

    @FXML
    private Button deleteTI;
    @FXML
    void eventA(MouseEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Event_Add.fxml"));
        try {
            Parent root = loader.load();
            EventAdd controller = loader.getController();
            EventA.getScene().setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
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



    {
        conn= DBConnection.getConnection();
    }
    @FXML
    public void initialize() {
        // Créer une liste d'objets Produit
        ObservableList<tickets> items = FXCollections.observableArrayList();
        try {

            String req = "SELECT * FROM tickets";
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                tickets t = new tickets();
                t.setTickets_id(rs.getInt(1));
                t.setTickets_type(rs.getString("tickets_type"));
                t.setTickets_price(rs.getInt("tickets_price"));
                 t.setQuantite(rs.getInt("quantite"));
                 t.setEvent_id(rs.getInt("event_id"));
                 t.setQrcode(rs.getString("qrcode"));
                items.add(t);
            }

            // Lier la liste à la ListView
            listV.setItems(items);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        listV.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                // Display the QR code of the selected ticket
                System.out.println(newValue.getQrcode());
                displayQRCode(newValue.getQrcode());
            }
        });
    }
    @FXML
    void getqr(MouseEvent event) {

    }

    private void displayQRCode(String qrCodePath) {
        try {
            Image image = new Image("file:" + qrCodePath);
            if (image.isError()) {
                System.out.println("Error loading image: " + image.getUrl());
            } else {
                imqr.setImage(image);
            }
        } catch (Exception e) {
            System.out.println("Error loading image: " + e.getMessage());
            e.printStackTrace();
        }
    }


    @FXML
    void DeleteT(ActionEvent event) throws SQLException

    {
        int selectedItem = listV.getSelectionModel().getSelectedItem().getTickets_id();
        deleteT.setText(String.valueOf(selectedItem));
        String sql = "DELETE FROM tickets WHERE tickets_id = ?";
        try {
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
                    int deleteTVallue = Integer.parseInt(deleteT.getText());
                    preparedStatement.setInt(1, deleteTVallue);
                    preparedStatement.executeUpdate();
                    System.out.println("Product Deleted successfully!");
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Succès");
                    alert.setContentText("Product Deleted successfully!");
                    alert.showAndWait();
                    initialize();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
