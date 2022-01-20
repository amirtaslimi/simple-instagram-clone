package com.psudoinstagram;

import com.psudoinstagram.model.Post;
import com.psudoinstagram.model.PostType;
import com.psudoinstagram.model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class PostPageController{
    User currentUser;
    Post currentPost;
    Post comment;

    @FXML
    private ListView<Post> commentList;
    @FXML
    private TextField commentField;
    @FXML
    private Circle likeCircle;
    @FXML
    private Label postLable;
    @FXML
    void likeCircle(MouseEvent event) {
        int flag = 0;
        for (User usr : currentPost.likedUsers) {
            if (usr.equals(currentUser)) {
                currentPost.likedUsers.remove(currentUser);
                likeCircle.setFill(Color.BLACK);
                flag = 1;
            }
        }
        if (flag == 0) {
            currentPost.likedUsers.add(currentUser);
            likeCircle.setFill(Color.RED);
        }
    }

    @FXML
    void sendComment(ActionEvent event) {
        if (!commentField.getText().isEmpty()) {
            Post comment = new Post(commentField.getText());
            comment.user= currentUser;
            currentPost.comments.add(comment);
        }
        list();
    }


    @FXML
    void openReply(ActionEvent event) throws IOException {
        goPostPage(commentList.getSelectionModel().getSelectedItem());
    }

    void list(){
        ObservableList<Post> observableList = FXCollections.observableArrayList();
        for (int x = 0; x < currentPost.comments.size(); x++) {
            observableList.add(currentPost.comments.get(x));
        }
        commentList.setItems(observableList);

    }
    void likeState(){
        for (User usr : currentPost.likedUsers) {
            if (usr.equals(currentUser)) {
                likeCircle.setFill(Color.RED);
            }
        }
    }
    void setPost(){
        if (currentPost.postType == PostType.PHOTO){
            postLable.setText(currentPost.text);
            Image image = new Image(currentPost.file.toURI().toString());
            ImageView imageView = new ImageView(image);
            imageView.setX(170);
            imageView.setY(10);
            imageView.setFitWidth(270);
            imageView.setPreserveRatio(true);
            postLable.setGraphic(imageView);
        }
        else if (currentPost.postType == PostType.VIDEO){
            postLable.setText(currentPost.text);
            Media media = new Media(currentPost.file.toURI().toString());
            MediaPlayer player = new MediaPlayer(media);
            MediaView mediaView = new MediaView(player);
            player.play();
            player.setStopTime(Duration.seconds(30));
            mediaView.setX(170);
            mediaView.setY(10);
            mediaView.setFitWidth(270);
            mediaView.setPreserveRatio(true);
            postLable.setGraphic(mediaView);
        }
        else {
            postLable.setText(currentPost.text);
        }
    }
    void goPostPage(Post post) throws IOException {
        FXMLLoader postPageLoader2 = new FXMLLoader(getClass().getResource("postPage.fxml"));
        AnchorPane postPage2 = postPageLoader2.load();
        // Get the Controller from the FXMLLoader
        PostPageController postPageController = postPageLoader2.getController();
        postPageController.currentPost = post;
        postPageController.currentUser = currentUser;
        Stage stage = new Stage();
        stage.setTitle("Post Page");
        stage.setScene(new Scene(postPage2));
        stage.show();
        postPageController.list();
        postPageController.likeState();
        postPageController.setPost();
    }



}

