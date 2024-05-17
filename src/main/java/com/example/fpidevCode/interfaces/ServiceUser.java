package com.example.fpidevCode.interfaces;

import com.example.fpidevCode.LoginController;
import com.example.fpidevCode.entities.User;
import com.example.fpidevCode.utils.DBConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceUser implements IService<User> {
    private Connection cnx;
    private Statement st;
    private PreparedStatement pst;

    public ServiceUser() {
        cnx = DBConnection.getConnection();
    }

    public void changeScreen(ActionEvent event, String fxmlFile, String title) {
        try {
            FXMLLoader loader = new FXMLLoader(LoginController.class.getResource(fxmlFile));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setTitle(title);
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void ajouter(User user) throws SQLException, NoSuchAlgorithmException {
        // String hashedPassword = encryptor.encryptString(user.getMdp());
        String req = "INSERT INTO `user`(email, roles, motDePasse, nom , `prenom`, `role`, `numTel`, `is_verified`) VALUES (?,?,?,?,?,?,?,?)";
        PreparedStatement ps = cnx.prepareStatement(req);
        ps.setString(1, user.getEmail());
        ps.setString(2, "[\"" + user.getRole() + "\"]");
        ps.setString(3, user.getMotDePasse());
        ps.setString(4, user.getNom());
        ps.setString(5, user.getPrenom());
        ps.setString(6, user.getRole());
        ps.setInt(7, user.getNumTel());
        ps.setInt(8, user.getIs_verified());
        ps.executeUpdate();
        System.out.println("User Ajoutée");
    }


    @Override
    public void supprimer(User user) throws SQLException {

    }

    @Override
    public void modifier(User user) throws SQLException {

    }

    @Override
    public List<User> afficher() {
        List<User> utilisateurs = new ArrayList<>(); // Initialize the ObservableList
        String req = "SELECT * FROM `user` ";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                User u = new User();
                u.setEmail(rs.getString("email"));
                u.setId(rs.getInt("id"));
                u.setNom(rs.getString("NOM"));
                u.setPrenom(rs.getString("PRENOM"));
                u.setNumTel(rs.getInt("NUMTEL"));
                u.setIs_verified(rs.getInt("is_verified"));
                u.setMotDePasse(rs.getString("motdepasse"));
                u.setRole(rs.getString("ROLE"));
                u.setRoles(rs.getString("roles"));
                utilisateurs.add(u);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return utilisateurs;
    }


    public int utilisateurLoggedIn(String email, String password) {

        int id = -1;
        try {
            String req = "SELECT * from user where email = ?";
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {

                id = rs.getInt("id");
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return id;
    }

    public User afficherParPseudo(String email) throws SQLException {
        User u = new User();
        String req = "SELECT * FROM `user` WHERE email=?";
        PreparedStatement ps = cnx.prepareStatement(req);
        ps.setString(1, email);
        ResultSet rs = ps.executeQuery(); // Remove req from executeQuery
        while (rs.next()) {
            u.setEmail(rs.getString("email"));
            u.setId(rs.getInt("id"));
            u.setNom(rs.getString("NOM"));
            u.setPrenom(rs.getString("PRENOM"));
            u.setNumTel(rs.getInt("NUMTEL"));
            u.setIs_verified(rs.getInt("is_verified"));
            u.setMotDePasse(rs.getString("motdepasse"));
            u.setRole(rs.getString("ROLE"));
            u.setRoles(rs.getString("roles"));
        }
        return u;
    }

    public List<User> afficherParRole(String role) {
        List<User> utilisateurs = new ArrayList<>(); // Initialize the ObservableList
       /* String req = "SELECT * FROM `users` WHERE `role`=?";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setString(1, role);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                User u = new User();
                u.setPseudo(rs.getString("PSEUDO"));
                u.setCin(rs.getInt("CIN"));
                u.setNom(rs.getString("NOM"));
                u.setPrenom(rs.getString("PRENOM"));
                u.setNumtel(rs.getInt("NUMTEL"));
                u.setEmail(rs.getString("EMAIL"));
                u.setMdp(rs.getString("MDP"));
                u.setRole(rs.getString("ROLE"));
                utilisateurs.add(u);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }*/
        return utilisateurs;
    }

    public void modifierMdp(User user, String mdp) throws SQLException {
      /*  String req = "UPDATE `users` SET `mdp`=? WHERE pseudo=?";
        PreparedStatement ps = cnx.prepareStatement(req);
        ps.setString(1, mdp);
        ps.setString(2, user.getPseudo());
        ps.executeUpdate();
        System.out.println("Personne modifie");*/
    }

    public int ChercherMail(String email) {

        try {
            String req = "SELECT * from `user` WHERE `email` ='" + email + "'";
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                if (rs.getString("email").equals(email)) {
                    System.out.println("mail trouvé ! ");
                    return 1;
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return -1;
    }


}

