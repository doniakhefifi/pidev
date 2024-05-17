package com.example.fpidevCode.services;

import com.example.fpidevCode.entities.tickets;
import com.example.fpidevCode.utils.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class tickets_service implements IService<tickets> {
    private Connection conn;
    private Statement ste;
    private PreparedStatement pst;

    public tickets_service() {
        conn= DBConnection.getConnection();

}

    public void addwithuser(tickets T , int userId) {
        try {
            String req = "INSERT INTO tickets (tickets_type,tickets_price,quantite,event_id,qrcode,user_id) VALUES ('"+T.getTickets_type()+"','"+T.getTickets_price()+"','"+T.getQuantite()+"','"+T.getEvent_id()+"','"+T.getQrcode()+"','"+userId+"')";
            Statement st = conn.createStatement();
            st.executeUpdate(req);
            System.out.println("Tickets Added successfully!");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    /*public void addPst(evenement E) {
        String redquete = "INSERT INTO evenement (event_name,event_theme,event_date) VALUES (?,?,?)";

        try {
            this.pst = this.conn.prepareStatement(redquete);
            this.pst.setString(1, E.getEvent_name());
            this.pst.setString(2, E.getEvent_theme());
            this.pst.setDate(3, Utils.getSqlDate(E.getEvent_date())) ;

            this.pst.executeUpdate();
        } catch (SQLException var4) {
            throw new RuntimeException(var4);
        }
    }*/

    @Override
    public void add(tickets var1) {

    }

    @Override
    public void delete(int id) {
        String query = "DELETE FROM tickets WHERE tickets_id=?;";
        try(PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setInt(1, id);

            preparedStatement.executeUpdate();
            System.out.println("Ticket Deleted successfully!");
        } catch (SQLException se) {
            se.printStackTrace();
        }
    }

    @Override
    public void update(tickets T) {
        String query = "UPDATE evenement SET tickets_type=?, tickets_price=?,quantite=?,event_id=? WHERE tickets_id=?";
        try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {

            preparedStatement.setString(1, T.getTickets_type());
            preparedStatement.setFloat(2, T.getTickets_price());
            preparedStatement.setInt(3,T.getQuantite());
            preparedStatement.setInt(4,T.getEvent_id());
            preparedStatement.setInt(5, T.getTickets_id());
            System.out.println("Ticket updated successfully!");

            preparedStatement.executeUpdate();
        } catch(SQLException se) {
            se.printStackTrace();
        }
    }

    @Override
    public List<tickets> readAll() {
        String query = "SELECT * FROM tickets";
        List<tickets> list = new ArrayList<>();

        try {
            this.ste = this.conn.createStatement();
            ResultSet rs = this.ste.executeQuery(query);

            while(rs.next()) {
                list.add(new tickets(rs.getInt(1),
                        rs.getString("tickets_type"),
                        rs.getFloat("tickets_price"),
                        rs.getInt("quantite"),
                        rs.getInt("event_id")));
            }


            return list;
        } catch (SQLException var4) {
            throw new RuntimeException(var4);
        }
    }

   /* public List<tickets> getInnerJoinevenement(int id){
        String query="SELECT * FROM tickets INNER JOIN evenement ON (tickets.event_id = evenement.event_id) WHERE tickets.event_id=?";
        List<tickets> list = new ArrayList<>();

        try {
            this.ste = this.conn.createStatement();
            ResultSet rs = this.ste.executeQuery(query);


            while(rs.next()) {
                list.add(new tickets(rs.getInt(1),
                        rs.getString("tickets_type"),
                        rs.getFloat("tickets_price"),
                        rs.getInt("quantite"),
                        rs.getInt("event_id")));
            }


            return list;
        } catch (SQLException var4) {
            throw new RuntimeException(var4);
        }
    }*/
  /*  public List<evenement> readAll() {
        String requete = "SELECT * FROM evenement";
        List<evenement> list = new ArrayList<>();

        try {
            this.ste = this.conn.createStatement();
            ResultSet rs = this.ste.executeQuery(requete);

            while(rs.next()) {
                int event_id = rs.getInt(1);
                String event_name = rs.getString("event_name");
                String event_theme = rs.getString("event_theme");
                Date event_date = rs.getDate("event_date");

                // Créez un nouvel objet Evenement et ajoutez-le à la liste
                evenement Evenement = new evenement(event_id, event_name, event_theme, event_date );
                list.add(Evenement);
            }

            return list;
        } catch (SQLException var4) {
            throw new RuntimeException(var4);
        }
    }*/


   /* @Override
    public evenement readById(int var1) {
        return null;
    }*/
   @Override
   public tickets readById(int var1) {
       return null;
   }
}

