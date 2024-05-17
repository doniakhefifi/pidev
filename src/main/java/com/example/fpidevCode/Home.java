package com.example.fpidevCode;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import com.example.fpidevCode.entities.Livraison;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import com.example.fpidevCode.services.LivraisonService;
import com.example.fpidevCode.services.Mailing;
import com.example.fpidevCode.services.PaymentService;

public class Home {

    @FXML
    private Pagination pagination;
    private PaymentService paymentService;

    private Livraison l;
    @FXML
    private TextField searchBox;

    public Home() {
        this.paymentService = new PaymentService(); // Initialisez votre service de paiement ici
    }

    @Deprecated
    void openLivreur(MouseEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/fpidev/LivreurCrud.fxml"));
        try {
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = (Stage) pagination.getScene().getWindow();
            stage.setTitle("Livreur");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void initialize() {
        assert pagination != null : "fx:id=\"pagination\" was not injected: check your FXML file 'home.fxml'.";

        try {
            LivraisonService livraisonService = new LivraisonService();
            List<Livraison> livraisonList = livraisonService.readAll();
            System.out.println(livraisonList);

            int itemsPerPage = 3;
            int pageCount = (livraisonList.size() + itemsPerPage - 1) / itemsPerPage;

            pagination.setPageCount(pageCount);
            pagination.setPageFactory(pageIndex -> createLivraisonPage(livraisonList, pageIndex, itemsPerPage));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void afficherListLivraison(List<Livraison> list) {
        try {
            int itemsPerPage = 3;
            int pageCount = (list.size() + itemsPerPage - 1) / itemsPerPage;
            pagination.setPageCount(pageCount);
            pagination.setPageFactory(pageIndex -> createLivraisonPage(list, pageIndex, itemsPerPage));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private VBox createLivraisonPage(List<Livraison> livraisonList, int pageIndex, int itemsPerPage) {
        VBox pageBox = new VBox(10);
        pageBox.setPadding(new Insets(10));

        int start = pageIndex * itemsPerPage;
        int end = Math.min(start + itemsPerPage, livraisonList.size());

        for (int i = start; i < end; i += 3) {
            HBox cardRow = new HBox(10);

            for (int j = i; j < Math.min(i + 3, end); j++) {
                Livraison livraison = livraisonList.get(j);
                VBox livraisonCard = createLivraisonCard(livraison);
                cardRow.getChildren().add(livraisonCard);
            }

            pageBox.getChildren().add(cardRow);
        }

        return pageBox;
    }

    private VBox createLivraisonCard(Livraison livraison) {
        VBox card = new VBox(10);
        card.setStyle("-fx-background-color: white; -fx-border-color: black;");
        card.setPadding(new Insets(10));

        ImageView imageView = new ImageView(new Image(new File("src/main/resources/com/example/IMG/deliveryIllustration.png").toURI().toString()));

        imageView.setFitWidth(100);
        imageView.setFitHeight(100);

        Label nameLabel = new Label("Client: " + livraison.getClientName());
        Label addressLabel = new Label("Address: " + livraison.getDeliveryAddress());
        Label dateLabel = new Label("Date: " + livraison.getDeliveryDate());
        Label totalAmountLabel = new Label("Total Amount: " + livraison.getStatus());

        Button payButton = new Button("Pay");
        payButton.setOnAction(event -> handlePayment(livraison));

        card.getChildren().addAll(imageView, nameLabel, addressLabel, dateLabel, totalAmountLabel, payButton);

        return card;
    }

    private void handlePayment(Livraison livraison) {
        Mailing.sendHtmlNotification("donia.khefifi@esprit.tn", "Cabine Notification", "test", "Removed");

        paymentService.processPayment(40, livraison);

    }


    @FXML
    public void ONtextchanged(Event event) {
    }

    @FXML
    public void OnsearchBtnClicked(ActionEvent actionEvent) throws SQLException {
        LivraisonService livraisonService = new LivraisonService();
        List<Livraison> filteredList = livraisonService.SearchByClientName(searchBox.getText());
        afficherListLivraison(filteredList);

    }
}


