package com.example.taxi;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;

public class RegisterDriver {

    @FXML
    private ComboBox<String> genderComboBox;

    public void initialize() {
        // Set default values for the ComboBox
        genderComboBox.setItems(FXCollections.observableArrayList("Male", "Female"));
    }

}
