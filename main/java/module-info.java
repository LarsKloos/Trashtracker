module login.login {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;


    exports login.login;



    opens login.login to javafx.fxml;
}
