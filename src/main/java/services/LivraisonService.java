package services;

import entities.Livraison;
import utils.DataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LivraisonService implements CRUD<Livraison> {

    public void add(Livraison livraison) throws SQLException
    {
        String query = "INSERT INTO livraison (client_name, delivery_address, delivery_date, `livreurId`, `status`, `commande`) VALUES (?, ?, ?, ?, ?, ?)";
        Connection connect = DataSource.getInstance().getCnx();
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
        String query = "DELETE FROM livraison WHERE livraison_id = ?";
        Connection connect = DataSource.getInstance().getCnx();
        PreparedStatement preparedStatement = connect.prepareStatement(query);

        preparedStatement.setInt(1, id);
        preparedStatement.executeUpdate();
        System.out.println("Livraison deleted");
    }
    public List<Livraison> readAll()  throws SQLException
    {
        List<Livraison> livraisons = new ArrayList<>();
        String query = "SELECT * FROM livraison";
        Connection connect = DataSource.getInstance().getCnx();
        PreparedStatement preparedStatement = connect.prepareStatement(query);

        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            Livraison livraison = new Livraison();
            livraison.setLivraisonId(resultSet.getInt("livraison_id"));
            livraison.setClientName(resultSet.getString("client_name"));
            livraison.setDeliveryAddress(resultSet.getString("delivery_address"));
            livraison.setDeliveryDate(resultSet.getString("delivery_date"));
            livraison.setLivreurId(resultSet.getInt("livreurId"));
            livraison.setStatus(resultSet.getString("status"));
            livraison.setCommande(resultSet.getString("commande"));
            livraisons.add(livraison);
        }

        return livraisons;
    }
    public void update(Livraison livraison) throws SQLException
    {
        String query = "UPDATE livraison SET client_name = ?, delivery_address = ?, delivery_date = ? , livreurId = ?, status = ? , commande = ? WHERE livraison_id = ?";
        Connection connect = DataSource.getInstance().getCnx();
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

}
