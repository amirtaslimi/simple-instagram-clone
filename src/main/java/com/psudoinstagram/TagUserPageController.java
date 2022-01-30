package com.psudoinstagram;

import com.psudoinstagram.model.Data;
import com.psudoinstagram.model.Post;
import com.psudoinstagram.model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import org.controlsfx.control.textfield.TextFields;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class TagUserPageController implements Initializable {
    Post post;

    @FXML
    private TextField usernameChatroomField;

    @FXML
    private ListView<String> usersListChatroom;

    @FXML
    void addUserChatButton(ActionEvent event) {
        for (User usr: Data.allUsers) {
            if (usr.userName.equals(usernameChatroomField.getText())){
                if (! usr.taggedPosts.contains(post)){
                    post.taggedUser.add(usr);
                    usr.taggedPosts.add(post);
                }
            }
        }
        showList();
    }

    @FXML
    void removeUserChatButton(ActionEvent event) {
        for (User usr: Data.allUsers) {
            if (usr.userName.equals(usernameChatroomField.getText())){
                if (usr.userName.equals(usernameChatroomField.getText())){
                    post.taggedUser.remove(usr);
                    usr.taggedPosts.remove(post);
                }
            }
        }
        showList();
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ArrayList<String> auto = new ArrayList<String>();
        for (User usr:Data.allUsers) {
            auto.add(usr.userName);
        }
        TextFields.bindAutoCompletion(usernameChatroomField,auto);
    }
    public void showList() {
        ObservableList<String> observableList = FXCollections.observableArrayList();
        for (int x = 0; x < post.taggedUser.size(); x++) {
            observableList.add(post.taggedUser.get(x).userName);
        }
        usersListChatroom.setItems(observableList);
    }
}
