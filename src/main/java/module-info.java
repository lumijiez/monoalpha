module org.lumijiez.monoalpha {
    requires javafx.fxml;
    requires atlantafx.base;


    opens org.lumijiez.monoalpha to javafx.fxml;
    exports org.lumijiez.monoalpha;
    exports org.lumijiez.monoalpha.util;
    opens org.lumijiez.monoalpha.util to javafx.fxml;
}