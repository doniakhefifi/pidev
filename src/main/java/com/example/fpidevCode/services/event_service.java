package com.example.fpidevCode.services;

import com.example.fpidevCode.entities.evenement;
import com.example.fpidevCode.utils.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
//import utils.data_source;


public class event_service implements IService<evenement> {
    private Connection conn;
    private Statement ste;
    private PreparedStatement pst;

    public event_service() {
        conn= DBConnection.getConnection();
    }

    @Override
    public void add(evenement E) {
        String requete="INSERT INTO evenement (event_name,event_theme) VALUES ('"+E.getEvent_name()+"','"+E.getEvent_theme()+"')";

        try {
            this.ste=this.conn.createStatement();
            this.ste.executeUpdate(requete);
            System.out.println("Event Added successfully!");
        } catch (SQLException e) {
            throw new RuntimeException(e);
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
    public void delete(int id) {
        String query = "DELETE FROM evenement WHERE id=?;";
        try(PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            System.out.println("Event Deleted successfully!");
            preparedStatement.executeUpdate();
        } catch (SQLException se) {
            se.printStackTrace();
        }
    }

    @Override
    public void update(evenement e) {
        String query = "UPDATE evenement SET event_name=?, event_theme=? WHERE id=?;";
        try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {

            preparedStatement.setString(1, e.getEvent_name());
            preparedStatement.setString(2, e.getEvent_theme());
          //  preparedStatement.setDate(3, Utils.getSqlDate(e.getEvent_date()));
            preparedStatement.setInt(3, e.getEvent_id());

            System.out.println("Event updated successfully!");
            preparedStatement.executeUpdate();
        } catch(SQLException se) {
            se.printStackTrace();
        }
    }

    @Override
    public List<evenement> readAll() {
        String query = "SELECT * FROM evenement";
        List<evenement> list = new ArrayList<>();

        try {
            this.ste = this.conn.createStatement();
            ResultSet rs = this.ste.executeQuery(query);

            while(rs.next()) {
                list.add(new evenement(rs.getInt(1),
                        rs.getString("event_name"),
                        rs.getString("event_theme")));
                        //rs.getDate("event_date")));
            }

            return list;
        } catch (SQLException var4) {
            throw new RuntimeException(var4);
        }
    }
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


    @Override
    public evenement readById(int var1) {
        return null;
    }

    public evenement getOneById(int idd) {
        evenement E = null;
        String req = "SELECT * FROM evenement WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(req)) {
            stmt.setInt(1, idd);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    E = new evenement(rs.getString("event_name"),
                            rs.getString("event_theme"));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return E;
    }

    public List<evenement> affichage(){

        List<evenement> evenements = new ArrayList<>();
        try {

            String req = "SELECT * FROM evenement";
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                evenement e = new evenement();
                e.setEvent_id(rs.getInt(1));
                e.setEvent_name(rs.getString(2));
                e.setEvent_theme(rs.getString(3));

                evenements.add(e);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return evenements;
    }
    public void supprimerJoin(int id) {
        String sql = "DELETE evenement FROM evenement JOIN tickets ON (tickets.event_id = evenement.id) WHERE tickets.event_id = ?";
        try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            System.out.println("Deleted successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    //recherche dynamuique
    public List<evenement> recherche(String x) {
        List<evenement> list = new ArrayList<>();
        try {
            String requete = "SELECT * FROM evenement WHERE event_name LIKE '%" + x + "%' OR event_theme LIKE '%" + x + "%'";
            this.ste = this.conn.createStatement();
            ResultSet rs = this.ste.executeQuery(requete);
            while (rs.next()) {
                list.add(new evenement(rs.getInt(1), rs.getString("event_name"), rs.getString("event_theme")));
            }
        } catch (SQLException var4) {
            throw new RuntimeException(var4);
        }
        return list;
    }


    public List<evenement> triNom() {

        List<evenement> list1;
        List<evenement> list2 = readAll();

        list1 = list2.stream().sorted(Comparator.comparing(evenement::getEvent_name)).collect(Collectors.toList());
        return list1;

    }


}
