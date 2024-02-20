module com.example.taximetrie {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.taximetrie to javafx.fxml;
    exports com.example.taximetrie;
    exports com.example.taximetrie.controller;
    opens com.example.taximetrie.controller to javafx.fxml;
}