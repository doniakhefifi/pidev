package com.example.fpidevCode.services;

import com.example.fpidevCode.entities.Livreur;
import com.example.fpidevCode.utils.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LivreurService implements CRUD<Livreur> {

    public void add(Livreur livreur) throws SQLException {
            String query = "INSERT INTO `livreur`(`livreur_name`, `contact_number`, `date`, `type_vehicule`, `salaire`) VALUES (?,?,?,?,?)";
        Connection connect = DBConnection.getConnection();
        PreparedStatement preparedStatement = connect.prepareStatement(query);

        preparedStatement.setString(1, livreur.getLivreurName());
        preparedStatement.setString(2, livreur.getContactNumber());
        preparedStatement.setString(3, livreur.getDate());
        preparedStatement.setString(4, livreur.getTypeVehicule());
        preparedStatement.setFloat(5, livreur.getSalaire());
        preparedStatement.executeUpdate();
        System.out.println("Livreur added");
    }

    public void delete(int id) throws SQLException {
        String query = "DELETE FROM livreur WHERE id = ?";
        Connection connect = DBConnection.getConnection();
        PreparedStatement preparedStatement = connect.prepareStatement(query);

        preparedStatement.setInt(1, id);
        preparedStatement.executeUpdate();
        System.out.println("Livreur deleted");
    }

    public List<Livreur> readAll() throws SQLException {
        List<Livreur> livreurs = new ArrayList<>();
        String query = "SELECT * FROM livreur";
        Connection connect = DBConnection.getConnection();
        PreparedStatement preparedStatement = connect.prepareStatement(query);

        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            Livreur livreur = new Livreur();
            livreur.setLivreurId(resultSet.getInt("id"));
            livreur.setLivreurName(resultSet.getString("livreur_name"));
            livreur.setContactNumber(resultSet.getString("contact_number"));
            livreur.setDate(resultSet.getString("date"));
            livreur.setTypeVehicule(resultSet.getString("type_vehicule"));
            livreur.setSalaire(resultSet.getFloat("salaire"));
            livreurs.add(livreur);
        }

        return livreurs;
    }

    public Livreur searchById(int livreurId) throws SQLException {
        String query = "SELECT * FROM livreur WHERE id = ?";
        Connection connect =DBConnection.getConnection();
        PreparedStatement preparedStatement = connect.prepareStatement(query);
        preparedStatement.setInt(1, livreurId);

        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            Livreur livreur = new Livreur();
            livreur.setLivreurId(resultSet.getInt("id"));
            livreur.setLivreurName(resultSet.getString("livreur_name"));
            livreur.setContactNumber(resultSet.getString("contact_number"));
            livreur.setDate(resultSet.getString("date"));
            livreur.setTypeVehicule(resultSet.getString("type_vehicule"));
            livreur.setSalaire(resultSet.getFloat("salaire"));
            return livreur;
        }

        return null;
    }

    public Livreur searchByName(String livreurName) throws SQLException {
        String query = "SELECT * FROM livreur WHERE livreur_name = ?";
        Connection connect = DBConnection.getConnection();
        PreparedStatement preparedStatement = connect.prepareStatement(query);
        preparedStatement.setString(1, livreurName);

        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            Livreur livreur = new Livreur();
            livreur.setLivreurId(resultSet.getInt("id"));
            livreur.setLivreurName(resultSet.getString("livreur_name"));
            livreur.setContactNumber(resultSet.getString("contact_number"));
            livreur.setDate(resultSet.getString("date"));
            livreur.setTypeVehicule(resultSet.getString("type_vehicule"));
            livreur.setSalaire(resultSet.getFloat("salaire"));
            return livreur;
        }

        return null;
    }


    public void update(Livreur livreur) throws SQLException {
        String query = "UPDATE `livreur` SET `livreur_name`=?,`contact_number`=?,`date`=?,`type_vehicule`=?,`salaire`=? WHERE `id`=?";
        Connection connect =DBConnection.getConnection();
        PreparedStatement preparedStatement = connect.prepareStatement(query);

        preparedStatement.setString(1, livreur.getLivreurName());
        preparedStatement.setString(2, livreur.getContactNumber());
        preparedStatement.setString(3, livreur.getDate());
        preparedStatement.setString(4, livreur.getTypeVehicule());
        preparedStatement.setFloat(5, livreur.getSalaire());
        preparedStatement.setInt(6, livreur.getLivreurId());
        preparedStatement.executeUpdate();
        System.out.println("Livreur updated");
    }
}
