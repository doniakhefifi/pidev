package com.example.fpidevCode.entities;

import java.util.Objects;

public class User {
    int id ;
    private String nom;
    private String prenom;
    private String email;
    private String motDePasse;
    private int numTel ;
    private String  roles;
    private String role;
    private int is_verified ;


    public User() {
    }

    public User(int id, String roles, String nom, String prenom, String email, String motDePasse, String role, int is_verified , int numTel) {
        this.id = id;
        this.roles = roles;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.motDePasse = motDePasse;
        this.role = role;
        this.is_verified = is_verified;
        this.numTel = numTel;
    }

    public User(String roles, String nom, String prenom, String email, String motDePasse, String role, int is_verified, int numTel) {
        this.roles = roles;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.motDePasse = motDePasse;
        this.role = role;
        this.is_verified = is_verified;
        this.numTel = numTel;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public int getIs_verified() {
        return is_verified;
    }

    public void setIs_verified(int is_verified) {
        this.is_verified = is_verified;
    }

    public int getNumTel() {
        return numTel;
    }

    public void setNumTel(int numTel) {
        this.numTel = numTel;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", roles='" + roles + '\'' +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", email='" + email + '\'' +
                ", motDePasse='" + motDePasse + '\'' +
                ", role='" + role + '\'' +
                ", is_verified=" + is_verified +
                ", numTel=" + numTel +
                '}';
    }
}
