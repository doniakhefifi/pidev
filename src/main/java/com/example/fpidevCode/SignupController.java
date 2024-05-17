package com.example.fpidevCode;

import com.example.fpidevCode.entities.User;
import com.example.fpidevCode.interfaces.ServiceUser;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.swing.*;
import java.net.URL;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;

public class SignupController implements Initializable {
    @FXML
    private Button Exit;

    @FXML
    private Label cinSignupError;

    @FXML
    private PasswordField confirmMdpSignup;

    @FXML
    private Label confirmMdpSignupError;

    @FXML
    private TextField emailSignup;

    @FXML
    private Label emailSignupError;

    @FXML
    private Button loginSignup;

    @FXML
    private PasswordField mdpSignup;

    @FXML
    private Label mdpSignupError;

    @FXML
    private TextField nomSignup;

    @FXML
    private Label nomSignupError;

    @FXML
    private TextField numtelSignup;

    @FXML
    private Label numtelSignupError;

    @FXML
    private TextField prenomSignup;

    @FXML
    private Label prenomSignupError;

    @FXML
    private Label pseudoSignupError;

    @FXML
    private Button signUpButton;
    ServiceUser serviceUser = new ServiceUser();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    public void loginSignupButtonOnClick(ActionEvent event){
        serviceUser.changeScreen(event, "/com/example/fpidev/login.fxml", "Login");
    }
    public boolean getErrors(){
        pseudoSignupError.setText("");
        cinSignupError.setText("");
        nomSignupError.setText("");
        prenomSignupError.setText("");
        numtelSignupError.setText("");
        emailSignupError.setText("");
        mdpSignupError.setText("");
        confirmMdpSignupError.setText("");

        if(nomSignup.getText().isBlank() || !nomSignup.getText().matches("[a-zA-Z ]+")){
            nomSignupError.setTextFill(Color.RED);
            nomSignupError.setText("Le nom est invalide");
            return true;
        }
        if(prenomSignup.getText().isBlank() || !prenomSignup.getText().matches("[a-zA-Z ]+")){
            prenomSignupError.setTextFill(Color.RED);
            prenomSignupError.setText("Le prénom est invalide");
            return true;
        }

        if(numtelSignup.getText().isBlank() || !numtelSignup.getText().matches("\\d{1,12}")){
            numtelSignupError.setTextFill(Color.RED);
            numtelSignupError.setText("Le numéro de téléphone est invalide");
            return true;
        }
        if(emailSignup.getText().isBlank() || !emailSignup.getText().matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}")){
            emailSignupError.setTextFill(Color.RED);
            emailSignupError.setText("L'email est invalide");
            return true;
        }
        if(mdpSignup.getText().isBlank()|| mdpSignup.getText().length() < 8 || mdpSignup.getText().matches("[^a-zA-Z0-9]")){
            mdpSignupError.setTextFill(Color.RED);
            mdpSignupError.setText("Le mot de passe est invalide");
            return true;
        }
        if(confirmMdpSignup.getText().isBlank()){
            confirmMdpSignupError.setTextFill(Color.RED);
            confirmMdpSignupError.setText("La confirmation du mot de passe est invalide");
            return true;
        }
        if(!Objects.equals(confirmMdpSignup.getText(), mdpSignup.getText())){
            confirmMdpSignupError.setTextFill(Color.RED);
            confirmMdpSignupError.setText("Le mot de passe doit etre le meme");
            return true;
        }
            if (serviceUser.ChercherMail(emailSignup.getText()) != -1 ){
                pseudoSignupError.setTextFill(Color.RED);
                pseudoSignupError.setText("Cet eamil est déjà utilisé, veuillez en choisir un autre");
                return true;
            }
        return false;
    }
    @FXML
    public void signUpButtonButtonOnClick(ActionEvent event) throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        if (!getErrors()) {
            User u = new User();
            u.setNom(nomSignup.getText());
            u.setPrenom(prenomSignup.getText());
            u.setEmail(emailSignup.getText());
            u.setMotDePasse(mdpSignup.getText());
            u.setNumTel(Integer.parseInt(numtelSignup.getText()));
            u.setRole("ROLE_CLIENT");
            try {
                serviceUser.ajouter(u);
                System.out.println("Utilisateur ajouté avec succès !");
                JOptionPane.showMessageDialog(null,"Vous etes inscris avec succès ! Veuillez connecter maintenant.");
                serviceUser.changeScreen(event, "/com/example/fpidev/login.fxml", "LOGIN");
            } catch (SQLException e) {
                System.out.println("Erreur lors de l'ajout de l'utilisateur : " + e.getMessage());
            }

        }
    }
}
