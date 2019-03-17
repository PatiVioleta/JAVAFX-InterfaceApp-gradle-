package view;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import service.Service;

import javafx.scene.control.*;
import java.io.IOException;

public class LoginSceneCtrl {
    Service service;

    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;

    public LoginSceneCtrl(){}

    public void setController(Service service){
        this.service = service;
    }

    private void login(){
        String name = usernameField.getText();
        String pass = passwordField.getText();
        if( service.findOneUser(name,pass) != null){
            try{
            Stage stage = new Stage();
            stage.setTitle("Angajat: "+name);
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(LoginSceneCtrl.class.getResource("/view/angajatScene.fxml"));
            AnchorPane root = loader.load();

            stage.setScene(new Scene(root));
            AngajatSceneCtrl ctrl = loader.getController();
            ctrl.setController(service, stage);

            stage.show();

            ctrl.colorareTableView1();
            ctrl.colorareTableview2();

            usernameField.setText("");
            passwordField.setText("");

            }catch (IOException e){
                e.printStackTrace();
            }
        }
        else{
            errorWindow("Login error","Date incorecte!");
        }
    }

    private void errorWindow(String text, String m){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(text);
        alert.setContentText(m);
        alert.show();
    }

    @FXML
    private void loginHandler(){login();}

    public void close(){
        Platform.exit();
    }
}
