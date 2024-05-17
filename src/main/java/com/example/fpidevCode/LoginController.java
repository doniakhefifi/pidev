package com.example.fpidevCode;

import com.example.fpidevCode.entities.User;
import com.example.fpidevCode.interfaces.ServiceUser;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Random;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


public class
LoginController {
    @FXML
    private Button Exit;

    @FXML
    private Label echecLoginLabel;

    @FXML
    private Button loginButton;

    @FXML
    private Button mdpOublie;

    @FXML
    private TextField pseudoLogin;

    @FXML
    private Button signupLogin;
    @FXML
    private PasswordField mdpLogin;

    @FXML
    void initialize() {
        Exit.setOnMouseClicked(event -> {
            System.exit(0);
        });
    }
    ServiceUser serviceUser = new ServiceUser();

    private void handelLogin(ActionEvent event) throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        try {
            if (serviceUser.utilisateurLoggedIn(pseudoLogin.getText(), mdpLogin.getText()) == -1) {
                echecLoginLabel.setTextFill(Color.RED);
                echecLoginLabel.setText("Pseudo ou mot de passe incorrect");
                mdpOublie.setVisible(true);
            } else {
                User user = serviceUser.afficherParPseudo(pseudoLogin.getText());
                echecLoginLabel.setTextFill(Color.GREEN);
                echecLoginLabel.setText("Bienvenue, " + user.getNom());

                switch (user.getRole()) {
                    case "ROLE_ADMIN":
                        LivreurCrud.setLoggedInUser(user);
                        serviceUser.changeScreen(event, "/com/example/fpidev/LivreurCrud.fxml", "Admin");
                        break;
                    case "ROLE_CLIENT":
                        ClientController.setLoggedInUser(user);
                        serviceUser.changeScreen(event, "/com/example/fpidev/client.fxml", "Client");
                        break;
                    default:
                        break;
                }
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la connexion: " + e.getMessage());
        }
    }
    @FXML
    public void loginButtonOnClick(ActionEvent event) throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {


            if (!pseudoLogin.getText().isBlank()&& !mdpLogin.getText().isBlank()){
                handelLogin(event);
            }
            else {
                echecLoginLabel.setTextFill(Color.RED);
                echecLoginLabel.setText("Entrer correctement votre email et mot de passe");
            }



    }
    @FXML
    private void mdpOublieOnClick(ActionEvent event){
        Random rd = new Random();
        int Rand = rd.nextInt(1000000+1);
        sendMail(event, Rand);
    }
    private void sendMail(ActionEvent event, int Rand){

        Properties props=new Properties();
        props.put("mail.smtp.host","smtp.gmail.com");
        props.put("mail.smtp.port",465);
        props.put("mail.smtp.user","key95661@gmail.com");
        props.put("mail.smtp.auth",true);
        props.put("mail.smtp.starttls.enable",true);
        props.put("mail.smtp.debug",true);
        props.put("mail.smtp.socketFactory.port",465);
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.socketFactory.fallback",false);

        try {
            Session session = Session.getDefaultInstance(props, null);
            session.setDebug(true);
            MimeMessage message = new MimeMessage(session);
            message.setSubject("Code de Confirmation d'oublie le mot de passe");
            message.setFrom(new InternetAddress("key95661@gmail.com"));
            message.setText("Voici code de Confirmation d'oublie le mot de passe : " + String.valueOf(Rand));
            User newUser = serviceUser.afficherParPseudo(pseudoLogin.getText());
            MotDePasseOublieController.setLoggedInUser(newUser);
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(newUser.getEmail()));
            try
            {
                MotDePasseOublieController.setRand(Rand);
                Transport transport = session.getTransport("smtp");
                transport.connect("smtp.gmail.com","key95661@gmail.com","hskz nctt iufy qhxk");
                transport.sendMessage(message, message.getAllRecipients());
                transport.close();
                serviceUser.changeScreen(event, "/com/example/fpidev/motDePasseOublie.fxml", "Vérifier le code");
            }catch(Exception e)
            {
                System.out.println(e.getMessage());
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    @FXML
    public void signupLoginButtonOnClick(ActionEvent event){
        serviceUser.changeScreen(event, "/com/example/fpidev/signup.fxml", "Sign Up");
    }

}
