package com.example.fpidevCode.services;

import com.example.fpidevCode.entities.Livraison;
import com.example.fpidevCode.utils.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LivraisonService implements CRUD<Livraison> {

    public void add(Livraison livraison) throws SQLException
    {
        String query = "INSERT INTO livraison (client_name, delivery_adress , delivery_date, `user_id`, `status`, `commande`) VALUES (?, ?, ?, ?, ?, ?)";
        Connection connect = DBConnection.getConnection();
        PreparedStatement preparedStatement = connect.prepareStatement(query);
        preparedStatement.setString(1, livraison.getClientName());
        preparedStatement.setString(2, livraison.getDeliveryAddress());
        preparedStatement.setString(3, livraison.getDeliveryDate());
        preparedStatement.setInt(4, livraison.getLivreurId());
        preparedStatement.setString(5, livraison.getStatus());
        preparedStatement.setString(6, livraison.getCommande());
        preparedStatement.executeUpdate();
        System.out.println("Livraison added");
    }
    public void delete(int id) throws SQLException
    {
        String query = "DELETE FROM livraison WHERE id = ?";
        Connection connect = DBConnection.getConnection();
        PreparedStatement preparedStatement = connect.prepareStatement(query);

        preparedStatement.setInt(1, id);
        preparedStatement.executeUpdate();
        System.out.println("Livraison deleted");
    }
    public List<Livraison> readAll()  throws SQLException
    {
        List<Livraison> livraisons = new ArrayList<>();
        String query = "SELECT * FROM livraison";
        Connection connect = DBConnection.getConnection();
        PreparedStatement preparedStatement = connect.prepareStatement(query);

        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            Livraison livraison = new Livraison();
            livraison.setLivraisonId(resultSet.getInt("id"));
            livraison.setClientName(resultSet.getString("client_name"));
            livraison.setDeliveryAddress(resultSet.getString("delivery_adress"));
            livraison.setDeliveryDate(resultSet.getString("delivery_date"));
            livraison.setLivreurId(resultSet.getInt("user_id"));
            livraison.setStatus(resultSet.getString("status"));
            livraison.setCommande(resultSet.getString("commande"));
            livraisons.add(livraison);
        }

        return livraisons;
    }
    public void update(Livraison livraison) throws SQLException
    {
        String query = "UPDATE livraison SET client_name = ?, delivery_adress = ?, delivery_date = ? , user_id = ?, status = ? , commande = ? WHERE id = ?";
        Connection connect = DBConnection.getConnection();
        PreparedStatement preparedStatement = connect.prepareStatement(query);

        preparedStatement.setString(1, livraison.getClientName());
        preparedStatement.setString(2, livraison.getDeliveryAddress());
        preparedStatement.setString(3, livraison.getDeliveryDate());
        preparedStatement.setInt(4, livraison.getLivreurId());
        preparedStatement.setString(5, livraison.getStatus());
        preparedStatement.setString(6, livraison.getCommande());
        preparedStatement.setInt(7, livraison.getLivraisonId());
        preparedStatement.executeUpdate();
        System.out.println("Livraison updated");
    }

    public List<Livraison> SearchByClientName(String name)  throws SQLException
    {
        List<Livraison> livraisons = new ArrayList<>();
        String query = "SELECT * FROM livraison where client_name like ?";
        Connection connect = DBConnection.getConnection();
        PreparedStatement preparedStatement = connect.prepareStatement(query);
            // Set the parameter safely to avoid SQL injection
            preparedStatement.setString(1, "%" + name + "%");



        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            Livraison livraison = new Livraison();
            livraison.setLivraisonId(resultSet.getInt("id"));
            livraison.setClientName(resultSet.getString("client_name"));
            livraison.setDeliveryAddress(resultSet.getString("delivery_adress"));
            livraison.setDeliveryDate(resultSet.getString("delivery_date"));
            livraison.setLivreurId(resultSet.getInt("user_id"));
            livraison.setStatus(resultSet.getString("status"));
            livraison.setCommande(resultSet.getString("commande"));
            livraisons.add(livraison);
        }

        return livraisons;
    }

}
