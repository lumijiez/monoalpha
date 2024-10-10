package org.lumijiez.monoalpha;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("monoalpha.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1280, 720);
        stage.setMinHeight(720);
        stage.setMinWidth(1280);
        stage.setTitle("MonoAlpha");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}