package entities;

import java.util.Date;

public class Livraison {
    private int livraisonId;
    private String clientName;
    private String deliveryAddress;
    private String deliveryDate;
    private String status;
    private String commande;
    private int livreurId;

    public Livraison(){

    }

    public Livraison(int livraisonId, String clientName, String deliveryAddress, String deliveryDate, String status, String commande, int livreurId) {
        this.livraisonId = livraisonId;
        this.clientName = clientName;
        this.deliveryAddress = deliveryAddress;
        this.deliveryDate = deliveryDate;
        this.status = status;
        this.commande = commande;
        this.livreurId = livreurId;
    }

    public Livraison(String clientName, String deliveryAddress, String deliveryDate, String status, String commande, int livreurId) {
        this.clientName = clientName;
        this.deliveryAddress = deliveryAddress;
        this.deliveryDate = deliveryDate;
        this.status = status;
        this.commande = commande;
        this.livreurId = livreurId;
    }

    public int getLivraisonId() {
        return livraisonId;
    }

    public void setLivraisonId(int livraisonId) {
        this.livraisonId = livraisonId;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCommande() {
        return commande;
    }

    public void setCommande(String commande) {
        this.commande = commande;
    }

    public int getLivreurId() {
        return livreurId;
    }

    public void setLivreurId(int livreurId) {
        this.livreurId = livreurId;
    }
}
