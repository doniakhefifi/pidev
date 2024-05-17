package com.example.fpidevCode;

import com.example.fpidevCode.entities.User;
import com.example.fpidevCode.interfaces.ServiceUser;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;

import javax.swing.*;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.Objects;

public class MotDePasseOublieController {
    @FXML
    private Button Exit;

    @FXML
    private AnchorPane codeVerifAnchor;

    @FXML
    private TextField codeVerification;

    @FXML
    private TextField confirmerNouveauMdp;

    @FXML
    private Label echecLoginLabel;

    @FXML
    private Label echecLoginLabel1;

    @FXML
    private TextField nouveauMdp;

    @FXML
    private AnchorPane nouveauMdpAnchor;

    @FXML
    private Button retourButton;

    @FXML
    private Button retourButton1;

    @FXML
    private Button validerCode;

    @FXML
    private Button validerMdp;

    ServiceUser serviceUser = new ServiceUser();
    private static int rand;
    public static void setRand(int r) {
        rand = r;
    }
    private static User loggedInUser;
    public static void setLoggedInUser(User user) {
        loggedInUser = user;
    }

    // Méthode getter pour obtenir le numéro aléatoire
    public static int getRand() {
        return rand;
    }
    @FXML
    void retourButtonOnClick(ActionEvent event) {
        serviceUser.changeScreen(event, "/com/example/fpidev/login.fxml", "LOGIN");
    }

    @FXML
    void validerCodeOnClick(ActionEvent event) {
        if (Integer.parseInt(codeVerification.getText()) == rand){
            codeVerifAnchor.setVisible(false);
            nouveauMdpAnchor.setVisible(true);
        }
        else {
            JOptionPane.showMessageDialog(null,"Code incorrecte! ");
        }
    }

    private boolean getError(){
        String thisUserMdp = loggedInUser.getMotDePasse();
        if(nouveauMdp.getText().isBlank()|| nouveauMdp.getText().length() < 8 || nouveauMdp.getText().matches("[^a-zA-Z0-9]")){
            echecLoginLabel.setTextFill(Color.RED);
            echecLoginLabel.setText("Le mot de passe est invalide");
            return true;
        }
        if(confirmerNouveauMdp.getText().isBlank()){
            echecLoginLabel.setTextFill(Color.RED);
            echecLoginLabel.setText("La confirmation du mot de passe est invalide");
            return true;
        }
        if(!Objects.equals(confirmerNouveauMdp.getText(), nouveauMdp.getText())){
            echecLoginLabel.setTextFill(Color.RED);
            echecLoginLabel.setText("Le mot de passe doit etre le meme");
            return true;
        }
        return false;
    }
    @FXML
    void validerMdpOnClick(ActionEvent event) throws NoSuchAlgorithmException {
        if (!getError()){
            try {
                serviceUser.modifierMdp(loggedInUser, nouveauMdp.getText());
                JOptionPane.showMessageDialog(null,"Mot de Passe modifié avec succès !");
                serviceUser.changeScreen(event, "/com/example/fpidev/login.fxml", "LOGIN");
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    @FXML
    void initialize() {
    }
}
