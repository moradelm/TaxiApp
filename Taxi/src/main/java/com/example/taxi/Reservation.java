package com.example.taxi;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import Model.Client;
import Model.Driver;
import Services.Authentification;
import Services.ClientService;
import Services.DriverService;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
public class Reservation {
       
    @FXML
    private TextField lieu;
    @FXML
    private DatePicker date_depa;
    @FXML
    private DatePicker date_arri;
    @FXML
    private TextArea more_info;
    @FXML
    private TextField num_place;
    
    @FXML
    private TextField lieu_arriv;
    @FXML 
    private TextField prix;
    private int idclient=Authentification.id;
    private int idcond=ClientInterface.DriverId;
    private DriverService driverService;
    private ClientService clientService;
    
    public Reservation(){
       driverService=new DriverService();
       clientService =new ClientService();
    }
    public void InsertRese() {
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedDate = currentDate.format(formatter);
        String datedepa=date_depa.getValue().format(formatter);
        String datearri=date_arri.getValue().format(formatter);
       BdConnexion bd=new BdConnexion();
       int r=bd.executerAction(String.format("insert into reservation values(null,%d,%d,'%s','%s','%s','%s','%s',%d,'%s',%f)",idclient,idcond,lieu.getText(),formattedDate,datedepa,datearri,more_info.getText(),Integer.parseInt(num_place.getText()),lieu_arriv.getText(),Double.parseDouble(prix.getText())));
       if (r > 0) {
          Client clt;
          Driver dr;
          clt=clientService.getClient(idclient);
          dr=driverService.getDriver(idcond);
          myAlert(AlertType.INFORMATION, "Reservation made", "Client: " + clt.getNom() + "\n Driver: " + dr.getNom()+"\nPrix:"+Double.parseDouble(prix.getText()));

       }
       
        
    }
    void myAlert(AlertType type,String title,String msg) {
       Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}