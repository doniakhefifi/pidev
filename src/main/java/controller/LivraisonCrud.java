package controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import entities.Livraison;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import services.LivraisonService;

public class LivraisonCrud {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableColumn<?, ?> addressCo;

    @FXML
    private TableColumn<?, ?> commandeCo;

    @FXML
    private TableColumn<?, ?> dateCo;

    @FXML
    private Button modifierButton;

    @FXML
    private TableColumn<?, ?> nomCo;

    @FXML
    private TableColumn<?, ?> statusCo;

    @FXML
    private Button supprimerButton;

    @FXML
    private TableView<Livraison> table;
    private Livraison selectedLivraison;


    @FXML
    void openLivreur(MouseEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/LivreurCrud.fxml"));
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
    void afficherTable(){
        nomCo.setCellValueFactory(new PropertyValueFactory<>("clientName"));
        addressCo.setCellValueFactory(new PropertyValueFactory<>("deliveryAddress"));
        dateCo.setCellValueFactory(new PropertyValueFactory<>("deliveryDate"));
        statusCo.setCellValueFactory(new PropertyValueFactory<>("status"));
        commandeCo.setCellValueFactory(new PropertyValueFactory<>("commande"));

        LivraisonService LivraisonService = new LivraisonService();
        try {

            ObservableList<Livraison> LivraisonList = FXCollections.observableArrayList(LivraisonService.readAll());
            table.setItems(LivraisonList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void addLivraison(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/LivraisonCrudForm.fxml"));
        try {
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = (Stage) table.getScene().getWindow();
            stage.setTitle("Livraison");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void modifierLivraison(ActionEvent event) {
        if(selectedLivraison != null)
        {

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/LivraisonCrudForm.fxml"));
            try {
                Scene scene = new Scene(fxmlLoader.load());
                LivraisonCrudForm LivraisonCrudForm = fxmlLoader.getController();

                LivraisonCrudForm.setLivraison(selectedLivraison);
                Stage stage = (Stage) table.getScene().getWindow();
                stage.setTitle("Livraison");
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @FXML
    void supprimerLivraison(ActionEvent event) {
        if(selectedLivraison != null)
        {
            LivraisonService LivraisonService = new LivraisonService();
            try {
                LivraisonService.delete(selectedLivraison.getLivraisonId());
                afficherTable();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @FXML
    void initialize() {
        assert addressCo != null : "fx:id=\"addressCo\" was not injected: check your FXML file 'LivraisonCrud.fxml'.";
        assert commandeCo != null : "fx:id=\"commandeCo\" was not injected: check your FXML file 'LivraisonCrud.fxml'.";
        assert dateCo != null : "fx:id=\"dateCo\" was not injected: check your FXML file 'LivraisonCrud.fxml'.";
        assert modifierButton != null : "fx:id=\"modifierButton\" was not injected: check your FXML file 'LivraisonCrud.fxml'.";
        assert nomCo != null : "fx:id=\"nomCo\" was not injected: check your FXML file 'LivraisonCrud.fxml'.";
        assert statusCo != null : "fx:id=\"statusCo\" was not injected: check your FXML file 'LivraisonCrud.fxml'.";
        assert supprimerButton != null : "fx:id=\"supprimerButton\" was not injected: check your FXML file 'LivraisonCrud.fxml'.";
        assert table != null : "fx:id=\"table\" was not injected: check your FXML file 'LivraisonCrud.fxml'.";
        afficherTable();

        table.setOnMouseClicked((event)->handleRowClick());
        supprimerButton.setDisable(true);
        modifierButton.setDisable(true);
    }

    private void handleRowClick() {
        selectedLivraison = table.getSelectionModel().getSelectedItem();
        supprimerButton.setDisable(false);
        modifierButton.setDisable(false);

    }
}
