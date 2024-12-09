module com.example.librarymangmentsystem {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.bootstrapfx.core;

    opens com.example.librarymangmentsystem to javafx.fxml;
    exports com.example.librarymangmentsystem;
}