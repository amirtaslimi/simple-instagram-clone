package com.psudoinstagram;

import com.psudoinstagram.model.*;
import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField usernameField;

    @FXML
    private Label welcomeText;

    @FXML
    void loginButton(ActionEvent event) throws IOException {
        User usr = userLogin(usernameField.getText(), passwordField.getText());
        if (usr != null)
            gohomePage(usr);
    }

    @FXML
    void registerButton(ActionEvent event) {
        userRegister(usernameField.getText(), passwordField.getText());
    }
    public int userRegister(String username, String password) {
        if (username.isEmpty()|| password.isEmpty()) {
            welcomeText.setTextFill(Color.RED);
            welcomeText.setText("please fill boxes");
            System.out.println(username);
            return 0;
        } else{
            for (User usr:Data.allUsers) {
                if (username.equals(usr.userName)){
                    welcomeText.setTextFill(Color.RED);
                    welcomeText.setText("this username already exist");
                    return 0;
                }
            }
        }
        welcomeText.setTextFill(Color.GREEN);
        welcomeText.setText("you added in users, please login");
        User user = new User(username, password);
        Data.allUsers.add(user);
        return 0;
    }





    public User userLogin(String username, String password) {
        for (User usr : Data.allUsers) {
            if (password.equals(usr.userPass) && username.equals(usr.userName)) {
                welcomeText.setTextFill(Color.GREEN);
                welcomeText.setText("welcome!!");
                return usr;
            }
        }
        welcomeText.setTextFill(Color.RED);
        welcomeText.setText("currentUser not found!!!");
        return null;
    }


    void gohomePage(User usr) throws IOException {
       // AnchorPane root2 = FXMLLoader.load(this.getClass().getResource("homePage.fxml"));
        FXMLLoader loader = new FXMLLoader(getClass().getResource("homePage.fxml"));
        AnchorPane home = loader.load();
        // Get the Controller from the FXMLLoader
        HomePageController controller = loader.getController();
        controller.consumerUser = usr;
        Stage stage = new Stage();
        stage.setTitle("Home");
        stage.setScene(new Scene(home));
        stage.show();


    }
}
