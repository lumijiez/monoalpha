package org.lumijiez.monoalpha;

import atlantafx.base.theme.Dracula;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class MonoAlpha extends Application {
    @Override
    public void start(Stage stage) throws IOException {

        Application.setUserAgentStylesheet(new Dracula().getUserAgentStylesheet());
        FXMLLoader fxmlLoader = new FXMLLoader(MonoAlpha.class.getResource("monoalpha.fxml"));
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