package com.psudoinstagram;

import com.jfoenix.controls.JFXButton;
import com.psudoinstagram.model.Post;
import com.psudoinstagram.model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class UsersPageController{
    User clientUser ;
    User searchedUser;

    @FXML
    private JFXButton followUserButton;
    @FXML
    private ListView<String> userPostList;

    @FXML
    void showMeButton(ActionEvent event) {
        //TODO dont use this
        for (User usr:clientUser.followings) {
            if (usr.equals(searchedUser)){
                followUserButton.setText("!!!!FOLLOWED");
            }
        }

    }

    @FXML
    void followUserButton(ActionEvent event) {
        int flag =0;
        for (User usr:searchedUser.followers) {
            if (usr.equals(clientUser)){
                followUserButton.setText("Follow");
                followUserButton.setTextFill(Color.BLACK);
                searchedUser.followers.remove(clientUser);
                clientUser.followings.remove(searchedUser);
                flag = 1;
            }
        }
        if(flag==0){
            followUserButton.setText("Followed!!");
            followUserButton.setTextFill(Color.WHITE);
            searchedUser.followers.add(clientUser);
            clientUser.followings.add(searchedUser);
        }
    }

    @FXML
    void followers(ActionEvent event) throws IOException {
        FXMLLoader tablefx = new FXMLLoader(getClass().getResource("tables.fxml"));
        AnchorPane table = tablefx.load();
        // Get the Controller from the FXMLLoader
        TablesController controller = tablefx.getController();
        controller.show = searchedUser.followers;
        Stage stage = new Stage();
        stage.setScene(new Scene(table));
        stage.show();
        controller.simpleList();
    }

    @FXML
    void followings(ActionEvent event) throws IOException {
        FXMLLoader tablefx = new FXMLLoader(getClass().getResource("tables.fxml"));
        AnchorPane table = tablefx.load();
        // Get the Controller from the FXMLLoader
        TablesController controller = tablefx.getController();
        controller.show = searchedUser.followings;
        Stage stage = new Stage();
        stage.setScene(new Scene(table));
        stage.show();
        controller.simpleList();
    }

    @FXML
    void postPage(ActionEvent event) throws IOException {
        showPost();
        for (Post mypst:searchedUser.posts) {
            if (mypst.id == userPostList.getSelectionModel().getSelectedIndex()+1){
                goPostPage(mypst);
            }
        }
    }
    void showPost(){
        ObservableList<String> observableList = FXCollections.observableArrayList();
        for (int x = 0; x < searchedUser.posts.size(); x++) {
            observableList.add(searchedUser.posts.get(x).text);
        }
        userPostList.setItems(observableList);
    }




    void goPostPage(Post post) throws IOException {
        FXMLLoader postPageLoader = new FXMLLoader(getClass().getResource("postPage.fxml"));
        AnchorPane postPage = postPageLoader.load();
        // Get the Controller from the FXMLLoader
        PostPageController postPageController = postPageLoader.getController();
        postPageController.post = post;
        postPageController.currentUser = clientUser;
        Stage stage = new Stage();
        stage.setTitle("Post Page");
        stage.setScene(new Scene(postPage));
        stage.show();
        postPageController.list();
        postPageController.likeState();
        postPageController.setPost();
    }
}
