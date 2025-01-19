package login.login;

import javafx.application.HostServices;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class HelloController implements Initializable {

    @FXML
    private Button CancelButton;
    @FXML
    private Label WrongLogin;
    @FXML
    private TextField usernameTextField;
    @FXML
    private PasswordField PasswordField;
    @FXML
    private Button routebutton;
    @FXML
    private Button overviewbutton;
    @FXML
    private Text RotterdamNoordButton;

    private Stage stage;
    private Scene scene;
    private Parent root;

    private HostServices hostServices;

    @FXML
    protected void CancelButtonOnaction(ActionEvent e) {
        Stage stage = (Stage) CancelButton.getScene().getWindow();
        stage.close();
    }

    public void validateLogin(ActionEvent event) {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

        String verifyLogin = "SELECT * FROM account WHERE username = ? AND password = ?";

        try (PreparedStatement statement = connectDB.prepareStatement(verifyLogin)) {
            statement.setString(1, usernameTextField.getText());
            statement.setString(2, PasswordField.getText());

            ResultSet queryResult = statement.executeQuery();

            if (queryResult.next()) {
                switchToGui(event);
            } else {
                WrongLogin.setText("Invalid login. Please try again.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void switchToGui(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("Gui.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToRoute(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("route.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToOverview(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("overview.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public void setHostServices(HostServices hostServices) {
        this.hostServices = hostServices;
        System.out.println("HostServices is correct ingesteld.");
    }

    @FXML
    private void RouteRotterdamNoord(MouseEvent event) {

        try {
            String currentLocation = "52.0004,4.3561";
            String googleMapsUrl = "https://www.google.com/maps/dir/?api=1&origin=" + currentLocation + "&destination=Rotterdam+Noord";
            if (hostServices != null) {
                hostServices.showDocument(googleMapsUrl);
            } else {
                System.err.println("HostServices is not initialized.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
