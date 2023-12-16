package com.example.taxi;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

public class Login {

    @FXML
    private Text createAccount;
    @FXML
    private void handleCreateAccountClick(){
        try{

        FXMLLoader loader = new FXMLLoader(getClass().getResource("signUpAs.fxml"));
        Parent root = loader.load();

        Stage stage = new Stage();
        stage.setScene(new Scene(root));

        Stage currentStage = (Stage) createAccount.getScene().getWindow();
        currentStage.close();

        stage.show();
    }catch(IOException e){
            e.printStackTrace();
        }
    }

}
