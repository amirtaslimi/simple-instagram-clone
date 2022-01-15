package com.psudoinstagram;

import com.psudoinstagram.model.Data;
import com.psudoinstagram.model.Post;
import com.psudoinstagram.model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class ProfileController {
    User profileUser;



    @FXML
    private ListView<String> mypostList;

    @FXML
    private TextArea sendPost;


//    @Override
//    void initialize() {
//        if (profileUser != null ) {
//            for (int x = 0; x < profileUser.posts.size(); x++) {
//                observableList.add(profileUser.posts.get(x).text);
//            }
//            mypostList.setItems(observableList);
//            System.out.println(mypostList);
//        }
//    }


    @FXML
    void followers(ActionEvent event) throws IOException {
        FXMLLoader tablefx = new FXMLLoader(getClass().getResource("tables.fxml"));
        AnchorPane table = tablefx.load();
        // Get the Controller from the FXMLLoader
        TablesController controller = tablefx.getController();
        controller.show = profileUser.followers;
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
        controller.show = profileUser.followings;
        Stage stage = new Stage();
        stage.setScene(new Scene(table));
        stage.show();
        controller.simpleList();
    }


    @FXML
    void sendPostButton(ActionEvent event) {
        if (!sendPost.getText().isEmpty()) {
            Post post = new Post(sendPost.getText());
            post.user=profileUser;
            profileUser.posts.add(post);
            Data.HomePost.add(post);
        }
        list();
    }
    @FXML
    void postPage(ActionEvent event) throws IOException {
        ObservableList<String> observableList = FXCollections.observableArrayList();
        for (int x = 0; x < profileUser.posts.size(); x++) {
            observableList.add(profileUser.posts.get(x).text);
        }
        mypostList.setItems(observableList);
        for (Post mypst:profileUser.posts) {
            if (mypst.id == mypostList.getSelectionModel().getSelectedIndex()+1){
                goPostPage(mypst);
            }
        }
    }

    void list(){
        ObservableList observableList = FXCollections.observableArrayList();
        for (int x = 0; x < profileUser.posts.size(); x++) {
            observableList.add(profileUser.posts.get(x).text);
        }
        mypostList.setItems(observableList);
    }
    void goPostPage(Post post) throws IOException {
        FXMLLoader postPageLoader = new FXMLLoader(getClass().getResource("postPage.fxml"));
        AnchorPane postPage = postPageLoader.load();
        // Get the Controller from the FXMLLoader
        PostPageController postPageController = postPageLoader.getController();
        postPageController.post = post;
        postPageController.currentUser = profileUser;
        Stage stage = new Stage();
        stage.setTitle("Post Page");
        stage.setScene(new Scene(postPage));
        stage.show();
        postPageController.list();
        postPageController.likeState();
        postPageController.setPost();
    }

//    @Override
//    public void initialize(URL url, ResourceBundle resourceBundle) {
//        if (profileUser != null ) {
//            for (int x = 0; x < profileUser.posts.size(); x++) {
//                observableList.add(profileUser.posts.get(x).text);
//            }
//            mypostList.setItems(observableList);
//            System.out.println(mypostList);
//            System.out.println("klj");
//        }
//    }
}
