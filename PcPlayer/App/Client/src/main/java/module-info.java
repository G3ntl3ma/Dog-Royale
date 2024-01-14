module views {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires javafx.graphics;
    requires com.google.gson;
    requires Common;


    exports Dog.Client;
    exports Dog.Client.Interfaces;
    exports views;
    opens views to javafx.fxml;
}