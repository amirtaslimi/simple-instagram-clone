package com.psudoinstagram;

import com.psudoinstagram.model.Post;
import com.psudoinstagram.model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;


public class PostPageController {
    User currentUser;
    Post post = new Post();

    @FXML
    private ListView<String> commentList;

    @FXML
    private TextField commentField;
    @FXML
    private Circle likeCircle;
    @FXML
    private Label postLable;
    @FXML
    void likeCircle(MouseEvent event) {
        int flag = 0;
        for (User usr : post.likedUsers) {
            if (usr.equals(currentUser)) {
                post.likedUsers.remove(currentUser);
                likeCircle.setFill(Color.BLACK);
                flag = 1;
            }
        }
        if (flag == 0) {
            post.likedUsers.add(currentUser);
            likeCircle.setFill(Color.RED);
        }
    }

    @FXML
    void sendComment(ActionEvent event) {
        if (!commentField.getText().isEmpty()) {
            Post comment = new Post(commentField.getText());
            comment.user= currentUser;
            post.comments.add(comment);
        }
        list();
    }

    void list(){
        ObservableList<String> observableList = FXCollections.observableArrayList();
        for (int x = 0; x < post.comments.size(); x++) {
            observableList.add(post.comments.get(x).toString());
        }
        commentList.setItems(observableList);

        for (User likedUser:post.likedUsers) {
            if (currentUser.equals(likedUser)){
                likeCircle.setFill(Color.RED);
            }
        }
    }
    void likeState(){
        for (User usr : post.likedUsers) {
            if (usr.equals(currentUser)) {
                likeCircle.setFill(Color.RED);
            }
        }
    }
    void setPost(){
        postLable.setText(post.text);
    }
}

