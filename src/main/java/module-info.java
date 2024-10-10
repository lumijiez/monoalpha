module org.lumijiez.monoalpha {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.lumijiez.monoalpha to javafx.fxml;
    exports org.lumijiez.monoalpha;
}