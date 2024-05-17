package com.example.fpidevCode.entities;

public class Livreur {
    private int livreurId;
    private String livreurName;
    private String contactNumber;
    private String date;
    private String typeVehicule;
    private float salaire;

    public Livreur(){

    }

    public Livreur(int livreurId, String livreurName, String contactNumber, String date, String typeVehicule, float salaire) {
        this.livreurId = livreurId;
        this.livreurName = livreurName;
        this.contactNumber = contactNumber;
        this.date = date;
        this.typeVehicule = typeVehicule;
        this.salaire = salaire;
    }

    public Livreur(String livreurName, String contactNumber, String date, String typeVehicule, float salaire) {
        this.livreurName = livreurName;
        this.contactNumber = contactNumber;
        this.date = date;
        this.typeVehicule = typeVehicule;
        this.salaire = salaire;
    }

    public int getLivreurId() {
        return livreurId;
    }

    public void setLivreurId(int livreurId) {
        this.livreurId = livreurId;
    }

    public String getLivreurName() {
        return livreurName;
    }

    public void setLivreurName(String livreurName) {
        this.livreurName = livreurName;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Livreur{" +
                "livreurId=" + livreurId +
                ", livreurName='" + livreurName + '\'' +
                ", contactNumber='" + contactNumber + '\'' +
                ", date='" + date + '\'' +
                ", typeVehicule='" + typeVehicule + '\'' +
                ", salaire=" + salaire +
                '}';
    }

    public String getTypeVehicule() {
        return typeVehicule;
    }

    public void setTypeVehicule(String typeVehicule) {
        this.typeVehicule = typeVehicule;
    }

    public float getSalaire() {
        return salaire;
    }

    public void setSalaire(float salaire) {
        this.salaire = salaire;
    }
}
