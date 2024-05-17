package com.example.fpidevCode;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.example.fpidevCode.entities.Livreur;
import com.example.fpidevCode.entities.User;
import com.example.fpidevCode.interfaces.ServiceUser;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import com.example.fpidevCode.services.LivreurService;

public class LivreurCrud {

    @FXML
    private Button supprimerButton;

    @FXML
    private Button modifierButton;
    @FXML
    private TableColumn<Livreur, String> dateCo;

    @FXML
    private TableColumn<Livreur, String> nomCo;

    @FXML
    private TableColumn<Livreur, Integer> numCo;

    @FXML
    private TableColumn<Livreur, Float> salaireCo;

    @FXML
    private TableView<Livreur> table;

    @FXML
    private TableColumn<Livreur, String> typeCo;
    private Livreur selectedLivreur;
    @FXML
    private Button BtnHome;
    private static User loggedInUser;
    public static void setLoggedInUser(User user){loggedInUser = user;}

    private ServiceUser serviceUser = new ServiceUser();


    void afficherTable(){
        nomCo.setCellValueFactory(new PropertyValueFactory<>("livreurName"));
        numCo.setCellValueFactory(new PropertyValueFactory<>("contactNumber"));
        dateCo.setCellValueFactory(new PropertyValueFactory<>("date"));
        typeCo.setCellValueFactory(new PropertyValueFactory<>("typeVehicule"));
        salaireCo.setCellValueFactory(new PropertyValueFactory<>("salaire"));

        LivreurService livreurService = new LivreurService();
        try {
            System.out.println(livreurService.readAll());
            ObservableList<Livreur> livreurList = FXCollections.observableArrayList(livreurService.readAll());
            table.setItems(livreurList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void addLivreur(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/fpidev/LivreurCrudForm.fxml"));
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
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/fpidev/LivreurCrudForm.fxml"));
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

    @FXML
    public void GoHome(ActionEvent actionEvent) {
        loggedInUser = null;
        serviceUser.changeScreen(actionEvent, "/com/example/fpidev/login.fxml", "LOGIN");
    }

    @FXML
    public void Golivraison(MouseEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/fpidev/LivraisonCrud.fxml"));
        this.BtnHome.getScene().setRoot(loader.load());
    }

    @FXML
    public void openLivreur(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/fpidev/LivreurCrud.fxml"));
        this.BtnHome.getScene().setRoot(loader.load());
    }

    @FXML
    public void GoReservation(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/event_show.fxml"));
        this.BtnHome.getScene().setRoot(loader.load());
    }
}
