module com.example.trysql {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires java.persistence;
    requires org.hibernate.orm.core;
    requires java.naming;
    requires mysql.connector.java;
    requires java.sql;
    opens com.example.librarymangmentsystem to javafx.fxml;
    opens com.example.librarymangmentsystem.models;
    exports com.example.librarymangmentsystem;
}