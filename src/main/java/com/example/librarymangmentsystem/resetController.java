package com.example.librarymangmentsystem;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
public class resetController {
    public void handleResetPasswordClick(ActionEvent event) {
        try {
            Parent verificationRoot = FXMLLoader.load(getClass().getResource("Verification.fxml"));
            Scene verificationScene = new Scene(verificationRoot);
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.setScene(verificationScene);
            currentStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
