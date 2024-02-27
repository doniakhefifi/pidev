package controller;

import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import entities.Livreur;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import services.LivreurService;

public class LivreurCrud {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button supprimerButton;

    @FXML
    private Button modifierButton;
    @FXML
    private TableColumn<?, ?> dateCo;

    @FXML
    private TableColumn<?, ?> nomCo;

    @FXML
    private TableColumn<?, ?> numCo;

    @FXML
    private TableColumn<?, ?> salaireCo;

    @FXML
    private TableView<Livreur> table;

    @FXML
    private TableColumn<?, ?> typeCo;
    private Livreur selectedLivreur;




    void afficherTable(){
        nomCo.setCellValueFactory(new PropertyValueFactory<>("livreurName"));
        numCo.setCellValueFactory(new PropertyValueFactory<>("contactNumber"));
        dateCo.setCellValueFactory(new PropertyValueFactory<>("date"));
        typeCo.setCellValueFactory(new PropertyValueFactory<>("typeVehicule"));
        salaireCo.setCellValueFactory(new PropertyValueFactory<>("salaire"));

        LivreurService livreurService = new LivreurService();
        try {

            ObservableList<Livreur> livreurList = FXCollections.observableArrayList(livreurService.readAll());
            table.setItems(livreurList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void addLivreur(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/LivreurCrudForm.fxml"));
        try {
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = (Stage) table.getScene().getWindow();
            stage.setTitle("Livreur");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void modifierLivreur(ActionEvent event) {
        if (selectedLivreur != null) {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/LivreurCrudForm.fxml"));
            try {
                Parent root = fxmlLoader.load();
                LivreurCrudForm livreurCrudForm = fxmlLoader.getController();
                livreurCrudForm.setLivreur(selectedLivreur);

                Scene scene = new Scene(root);
                Stage stage = (Stage) table.getScene().getWindow();
                stage.setTitle("Livreur");
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @FXML
    void supprimerLivreur(ActionEvent event) {
        if(selectedLivreur != null)
        {
            LivreurService livreurService = new LivreurService();
            try {
                livreurService.delete(selectedLivreur.getLivreurId());
                afficherTable();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @FXML
    void initialize() {
        assert table != null : "fx:id=\"table\" was not injected: check your FXML file 'LivreurCrud.fxml'.";
        afficherTable();

        table.setOnMouseClicked((event)->handleRowClick());
        supprimerButton.setDisable(true);
        modifierButton.setDisable(true);
    }

    private void handleRowClick() {
        selectedLivreur = table.getSelectionModel().getSelectedItem();
        supprimerButton.setDisable(false);
        modifierButton.setDisable(false);

    }

    public void openLivraison(javafx.scene.input.MouseEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/LivraisonCrud.fxml"));
        try {
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = (Stage) table.getScene().getWindow();
            stage.setTitle("Livreur");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
