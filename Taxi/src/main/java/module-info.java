module com.example.taxi {
    requires javafx.controls;
    requires javafx.fxml;
            
                            
    opens com.example.taxi to javafx.fxml;
    exports com.example.taxi;
}