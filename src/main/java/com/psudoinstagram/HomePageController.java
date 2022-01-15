package com.psudoinstagram;

import com.psudoinstagram.model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.controlsfx.control.textfield.TextFields;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class HomePageController implements Initializable {
    @FXML
    private ListView<Post> homeList;
    User consumerUser;
    @FXML
    private TextField searchUserField;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ArrayList<String> auto = new ArrayList<String>();
        for (User usr:Data.allUsers) {
            auto.add(usr.userName);
        }
        TextFields.bindAutoCompletion(searchUserField,auto);
    }
    @FXML
    void postPageButton(ActionEvent event) throws IOException {
        ObservableList<Post> observableList = FXCollections.observableArrayList();
        for (int x = 0; x < Data.HomePost.size(); x++) {
            observableList.add(Data.HomePost.get(x));
        }
        homeList.setItems(observableList);
        for (Post homepost:Data.HomePost) {
            if (homepost.id == homeList.getSelectionModel().getSelectedIndex()+1){
                goPostPage(homepost);
            }
        }
    }
    @FXML
    void searchUserButton(ActionEvent event) throws IOException {
        for (User usr: Data.allUsers) {
            if (usr.userName.equals(searchUserField.getText())){
                goUserPage(usr);
            }
        }
    }
    @FXML
    void goMessagePage(ActionEvent event) throws IOException{
        FXMLLoader messageLoader = new FXMLLoader(getClass().getResource("messagePage.fxml"));
        AnchorPane message = messageLoader.load();
        // Get the Controller from the FXMLLoader
        MessagePageController msgController = messageLoader.getController();
        msgController.currentUser = consumerUser;
        Stage stage = new Stage();
        stage.setTitle("MESSAGE PAGE");
        stage.setScene(new Scene(message));
        stage.show();
        msgController.buildChatRoomsList();
    }

    @FXML
    void refresh(ActionEvent event) {
        ObservableList<Post> observableList = FXCollections.observableArrayList();
        for (int x = 0; x < Data.HomePost.size(); x++) {
            observableList.add(Data.HomePost.get(x));
        }
        homeList.setItems(observableList);
    }
    @FXML
    void profileButton(ActionEvent event) throws IOException {
        FXMLLoader profileLoader = new FXMLLoader(getClass().getResource("profile.fxml"));
        AnchorPane profile = profileLoader.load();
        // Get the Controller from the FXMLLoader
        ProfileController Procontroller = profileLoader.getController();
        Procontroller.profileUser = consumerUser;
        Stage stage = new Stage();
        stage.setTitle("Profile");
        stage.setScene(new Scene(profile));
        stage.show();
        Procontroller.list();
    }
    void goUserPage(User user) throws IOException {
        FXMLLoader userPageLoader = new FXMLLoader(getClass().getResource("userPage.fxml"));
        AnchorPane userPage = userPageLoader.load();
        // Get the Controller from the FXMLLoader
        UsersPageController usersPageController = userPageLoader.getController();
        usersPageController.searchedUser = user;
        usersPageController.clientUser = consumerUser;
        Stage stage = new Stage();
        stage.setTitle("User Page");
        stage.setScene(new Scene(userPage));
        stage.show();
        usersPageController.showPost();
    }
    void goPostPage(Post post) throws IOException {
        FXMLLoader postPageLoader = new FXMLLoader(getClass().getResource("postPage.fxml"));
        AnchorPane postPage = postPageLoader.load();
        // Get the Controller from the FXMLLoader
        PostPageController postPageController = postPageLoader.getController();
        postPageController.currentPost = post;
        postPageController.currentUser = consumerUser;
        Stage stage = new Stage();
        stage.setTitle("Post Page");
        stage.setScene(new Scene(postPage));
        stage.show();
        postPageController.list();
        postPageController.likeState();
        postPageController.setPost();
    }
}
