package controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import entities.Livreur;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import services.LivreurService;

public class Home {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Pagination pagination;

    @FXML
    void openLivreur(MouseEvent event) {

    }

    @FXML
    void initialize() {
        assert pagination != null : "fx:id=\"pagination\" was not injected: check your FXML file 'home.fxml'.";

        try {
            LivreurService livreurService = new LivreurService();
            List<Livreur> livreurList = livreurService.readAll();

            int itemsPerPage = 3;
            int pageCount = (livreurList.size() + itemsPerPage - 1) / itemsPerPage;

            pagination.setPageCount(pageCount);
            pagination.setPageFactory(pageIndex -> createLivreurPage(livreurList, pageIndex, itemsPerPage));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private VBox createLivreurPage(List<Livreur> livreurList, int pageIndex, int itemsPerPage) {
        VBox pageBox = new VBox(10);
        pageBox.setPadding(new Insets(10));

        int start = pageIndex * itemsPerPage;
        int end = Math.min(start + itemsPerPage, livreurList.size());

        for (int i = start; i < end; i += 3) {
            HBox cardRow = new HBox(10);

            for (int j = i; j < Math.min(i + 3, end); j++) {
                Livreur livreur = livreurList.get(j);
                VBox livreurCard = createLivreurCard(livreur);
                cardRow.getChildren().add(livreurCard);
            }

            pageBox.getChildren().add(cardRow);
        }

        return pageBox;
    }

    private VBox createLivreurCard(Livreur livreur) {
        VBox card = new VBox(10);
        card.setStyle("-fx-background-color: white; -fx-border-color: black;");
        card.setPadding(new Insets(10));

        ImageView imageView = new ImageView(new Image("images/deliveryIllustration.png"));
        imageView.setFitWidth(100);
        imageView.setFitHeight(100);

        Label nameLabel = new Label("Name: " + livreur.getLivreurName());
        Label contactLabel = new Label("Contact: " + livreur.getContactNumber());
        Label dateLabel = new Label("Date: " + livreur.getDate());
        Label typeLabel = new Label("Type of Vehicle: " + livreur.getTypeVehicule());
        Label salaireLabel = new Label("Salaire: " + livreur.getSalaire() + "DT");

        card.getChildren().addAll(imageView, nameLabel, contactLabel, dateLabel, typeLabel, salaireLabel);

        return card;
    }
}
