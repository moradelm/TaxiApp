package com.example.taxi;

import Model.Client;
import Services.ClientService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class DriverInterface {

    @FXML
    private VBox clientsVBox;
    @FXML
    private Button logoutButton;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private void initialize() {
        loadAllClients();
    }

    private void loadAllClients() {
        ClientService clientService = new ClientService();
        List<Client> clients = clientService.getAllClients();
        clientsVBox.getChildren().clear();

        for (Client client : clients) {
            HBox clientCard = createClientCard(client);
            clientsVBox.getChildren().add(clientCard);
        }
        scrollPane.setContent(clientsVBox);
    }

    private HBox createClientCard(Client client) {
        HBox clientCard = new HBox();
        clientCard.setSpacing(10);
        clientCard.setStyle("-fx-background-color: #f0f0f0; -fx-padding: 10;");

        VBox clientInfoVBox = new VBox();
        clientInfoVBox.setSpacing(5);

        Text clientNameText = new Text("Client: " + client.getNom());
        Text phoneNumberText = new Text("Phone Number: " + client.getTelephone());

        clientInfoVBox.getChildren().addAll(clientNameText, phoneNumberText);

        HBox buttonBox = new HBox();
        buttonBox.setSpacing(10);

        Button acceptButton = new Button("Accept");
        Button declineButton = new Button("Decline");

        acceptButton.setStyle("-fx-background-color: #87FF65;");
        declineButton.setStyle("-fx-background-color: #FF0000;");

        acceptButton.setOnAction(event -> handleAcceptClient(client, clientCard));
        declineButton.setOnAction(event -> handleDeclineClient(client, clientCard));

        buttonBox.getChildren().addAll(acceptButton, declineButton);

        clientCard.getChildren().addAll(clientInfoVBox, buttonBox);

        return clientCard;
    }

    private void handleAcceptClient(Client client, HBox clientCard) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Accept Client");
        alert.setHeaderText("Do you want to accept this client?");
        alert.setContentText("Client: " + client.getNom() + "\nPhone Number: " + client.getTelephone());


        ButtonType yesButton = new ButtonType("Yes");
        ButtonType noButton = new ButtonType("No");

        alert.getButtonTypes().setAll(yesButton, noButton);

        alert.showAndWait().ifPresent(buttonType -> {
            if (buttonType == yesButton) {
                System.out.println("Driver accepted the client: " + client.getNom());
                clientsVBox.getChildren().remove(clientCard);
            }
        });
    }

    private void handleDeclineClient(Client client, HBox clientCard) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Decline Client");
        alert.setHeaderText("Are you sure you want to decline this client?");
        alert.setContentText("Client: " + client.getNom() + "\nPhone Number: " + client.getTelephone());

        ButtonType yesButton = new ButtonType("Yes");
        ButtonType noButton = new ButtonType("No");

        alert.getButtonTypes().setAll(yesButton, noButton);

        alert.showAndWait().ifPresent(buttonType -> {
            if (buttonType == yesButton) {
                System.out.println("Driver declined the client: " + client.getNom());
                clientsVBox.getChildren().remove(clientCard);
            }
        });
    }


    @FXML
    private void handleLogoutButton(ActionEvent event) {
        Stage stage = (Stage) logoutButton.getScene().getWindow();
        stage.close();
        showLoginForm();
    }

    private void showLoginForm() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Login.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
