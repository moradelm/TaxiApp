package com.example.taxi;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;

import java.net.URL;
import java.util.ResourceBundle;

public class RegisterClient implements Initializable{

    @FXML
    private ComboBox<String> sexe;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        sexe.getItems().addAll("Male","Female");
    }

    @FXML
    private void handleComboBoxClick() {
        sexe.getItems().clear();
        sexe.getItems().addAll("Male", "Female");
    }

}
