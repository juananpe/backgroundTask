module eus.ehu.demo {
    requires javafx.controls;
    requires javafx.fxml;


    opens eus.ehu.demo to javafx.fxml;
    exports eus.ehu.demo;
}