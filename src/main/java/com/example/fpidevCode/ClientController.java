package com.example.fpidevCode;

import com.example.fpidevCode.entities.User;
import com.example.fpidevCode.interfaces.ServiceUser;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class ClientController {
    @FXML
    private Label email;
    @FXML
    private Button logout;
    @FXML
    private Label name;
    @FXML
    private Label prenon;
    @FXML
    private Button btnLivraioson;
    @FXML
    private Button BtnProfile;
    @FXML
    private Pane profilePane;
    @FXML
    private AnchorPane ContentPane;
    private static User loggedInUser;
    @FXML
    private Button btntickets;
    @FXML
    private Pane ticketsPane;

    public static void setLoggedInUser(User user){loggedInUser = user;}

    public static User getLoggedUSer(){return loggedInUser;}

    private ServiceUser serviceUser = new ServiceUser();
    @FXML
    private void deconnecterButtonOnClick(ActionEvent event){
        loggedInUser = null;
        serviceUser.changeScreen(event, "/com/example/fpidev/login.fxml", "LOGIN");
    }
    @FXML
    void initialize() {
        email.setText(loggedInUser.getEmail());
        name.setText(loggedInUser.getNom());
        prenon.setText(loggedInUser.getPrenom());
    }

    @FXML
    public void GoLivraisonFront(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/fpidev/home.fxml"));
        this.ContentPane.getChildren().add(loader.load());
        this.ContentPane.setVisible(true);
        this.profilePane.setVisible(false);
        this.ticketsPane.setVisible(false);
    }

    @FXML
    public void GoProfile(ActionEvent actionEvent) {
        this.profilePane.setVisible(true);
        this.ticketsPane.setVisible(false);
        this.ContentPane.setVisible(false);

    }

    @FXML
    public void Gotickets(ActionEvent actionEvent) throws IOException {
        this.ticketsPane.setVisible(true);
        this.ContentPane.setVisible(false);
        this.profilePane.setVisible(false);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/tickets_add.fxml"));
        this.ticketsPane.getChildren().add(loader.load());
    }
}
