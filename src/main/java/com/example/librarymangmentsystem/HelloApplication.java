package com.example.librarymangmentsystem;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import java.io.IOException;

public class HelloApplication extends Application {
        @Override
        public void start(Stage stage) throws IOException {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("WelcomeScreen.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            ImageView imageView = (ImageView) scene.lookup("#imageView");
            imageView.fitWidthProperty().bind(scene.widthProperty());
            imageView.fitHeightProperty().bind(scene.heightProperty());
            stage.setTitle("Bookly");
            stage.setScene(scene);
            stage.show();
        }
        public static void main(String[] args) {
            launch();
        }
    }