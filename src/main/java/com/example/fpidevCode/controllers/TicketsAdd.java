package com.example.fpidevCode.controllers;

import com.example.fpidevCode.ClientController;
import com.example.fpidevCode.LoginController;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.example.fpidevCode.entities.evenement;
import com.example.fpidevCode.entities.tickets;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import com.example.fpidevCode.services.event_service;
import com.example.fpidevCode.services.tickets_service;
import com.example.fpidevCode.utils.Sendmail;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.ResourceBundle;


public class TicketsAdd implements Initializable {


    private final tickets_service ts= new tickets_service();

    @FXML
    private TextField priceT;

    @FXML
    private TextField quantiteT;

    @FXML
    private TextField typeT;

    private event_service eventService = new event_service();

    String filePath;
    private  Sendmail sn = new Sendmail();

private event_service es= new event_service();
    @FXML
    private ComboBox<evenement> ComboEvent;


    @FXML
    void addT(ActionEvent event) {
        try {
            String idevent = String.valueOf(ComboEvent.getValue().getEvent_id());
            String prixt = priceT.getText();
            String quantite = quantiteT.getText();
            String type = typeT.getText();


            // Vérifier si les champs obligatoires ne sont pas vides
            if (idevent.isEmpty() || prixt.isEmpty() || quantite.isEmpty() || type.isEmpty() ) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setContentText("Veuillez remplir tous les champs.");
                alert.showAndWait();
                return; // Sortir de la méthode si les champs obligatoires sont vides
            }

            // Vérifier si les valeurs numériques sont valides
            int Quantite = 0;
            int prix = 0;
            int id = 0;
            try {
                Quantite = Integer.parseInt(quantite);
                prix = Integer.parseInt(prixt);
                id = Integer.parseInt(idevent);
            } catch (NumberFormatException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setContentText("Veuillez saisir des valeurs numériques valides pour la quantité, le prix et la catégorie.");
                alert.showAndWait();
                return; // Sortir de la méthode si les valeurs numériques ne sont pas valides
            }
            evenement e = es.getOneById(id);

            String qrText="Event : "+e.getEvent_name()+"Ticke type"+type+"Price :"+prix;

            this.generateQRCodeAndSave(qrText, String.valueOf(prix));


            // Si toutes les validations sont passées, ajouter le produit
            tickets t = new tickets(type, prix, Quantite, id,filePath);

            ts.addwithuser(t,ClientController.getLoggedUSer().getId());


            sn.envoyer(
                    "youssef.guebzili@esprit.tn",
                    "Ajout d'un ticket",
                    "Le ticket de type"+type+" a été ajouté avec succès"
            );
            // Afficher une alerte d'information en cas de succès
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Succès");
            alert.setContentText("Le produit a été ajouté avec succès.");
            alert.showAndWait();
        } catch (IllegalArgumentException | WriterException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }


    public String generateQRCodeAndSave(String text, String fileName) throws WriterException {
        // Generate the QR code

        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, 250, 250);
        BufferedImage bufferedImage = MatrixToImageWriter.toBufferedImage(bitMatrix);

        // Convert the BufferedImage to a JavaFX Image
        Image fxImage = SwingFXUtils.toFXImage(bufferedImage, null);

        // Save the image to the specified directory
        String directoryPath = "src/main/java/imagesqr";
        Path directory = Paths.get(directoryPath);
        if (!Files.exists(directory)) {
            try {
                Files.createDirectories(directory);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        filePath = directoryPath + "/" + fileName + ".png";
        System.out.println(filePath);
        File file = new File(filePath);
        try {
            ImageIO.write(SwingFXUtils.fromFXImage(fxImage, null), "png", file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return filePath;
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
            List<evenement> evenements = this.eventService.affichage();
            this.ComboEvent.getItems().addAll(evenements);
    }
}

