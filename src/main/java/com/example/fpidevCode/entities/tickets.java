package com.example.fpidevCode.entities;

public class tickets {
    private int tickets_id;
    private String tickets_type;
    private float tickets_price;
    private String qrcode;
    private int quantite;
    private int event_id;
    private evenement Evenement;

    public tickets(int tickets_id, String tickets_type, float tickets_price, int quantite, int event_id) {
        this.tickets_id = tickets_id;
        this.tickets_type = tickets_type;
        this.tickets_price = tickets_price;
        this.quantite = quantite;
        this.event_id = event_id;
    }

    public tickets(String tickets_type, float tickets_price, int quantite, int event_id, String qrcode) {
        this.tickets_type = tickets_type;
        this.tickets_price = tickets_price;
        this.quantite = quantite;
        this.qrcode = qrcode;
        this.event_id = event_id;
    }

    public tickets() {

    }

    public String getQrcode() {
        return qrcode;
    }

    public void setQrcode(String qrcode) {
        this.qrcode = qrcode;
    }

    public int getTickets_id() {
        return tickets_id;
    }

    public void setTickets_id(int tickets_id) {
        this.tickets_id = tickets_id;
    }

    public String getTickets_type() {
        return tickets_type;
    }

    public void setTickets_type(String tickets_type) {
        this.tickets_type = tickets_type;
    }

    public float getTickets_price() {
        return tickets_price;
    }

    public void setTickets_price(float tickets_price) {
        this.tickets_price = tickets_price;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    public int getEvent_id() {
        return event_id;
    }

    public void setEvent_id(int event_id) {
        this.event_id = event_id;
    }

    public evenement getEvenement() {
        return Evenement;
    }

    public void setEvenement(evenement Evenement) {
        this.Evenement = Evenement;
    }

    @Override
    public String toString() {
        return "tickets{" +
                "tickets_id=" + tickets_id +
                ", tickets_type='" + tickets_type + '\'' +
                ", tickets_price=" + tickets_price +
                ", quantite=" + quantite +
                ", event_id=" + event_id +
                '}';
    }
}