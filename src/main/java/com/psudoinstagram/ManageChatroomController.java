package com.psudoinstagram;

import com.psudoinstagram.model.ChatRoom;
import com.psudoinstagram.model.Data;
import com.psudoinstagram.model.Message;
import com.psudoinstagram.model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class ManageChatroomController {
    ChatRoom chatRoom;

    @FXML
    private TextField usernameChatroomField;

    @FXML
    private ListView<String> usersListChatroom;

    @FXML
    void addUserChatButton(ActionEvent event) {
        for (User usr: Data.allUsers) {
            if (usr.userName.equals(usernameChatroomField.getText())){
                if (! chatRoom.members.contains(usr)){
                    chatRoom.members.add(usr);
                    usr.userChats.add(chatRoom);
                }
            }
        }
        showList();
    }

    @FXML
    void removeUserChatButton(ActionEvent event) {
        for (User usr: Data.allUsers) {
            if (usr.userName.equals(usernameChatroomField.getText())){
                if (chatRoom.members.contains(usr)){
                    chatRoom.members.remove(usr);
                    usr.userChats.remove(chatRoom);
                }
            }
        }
        showList();
    }
    public void showList() {
        ObservableList<String> observableList = FXCollections.observableArrayList();
        for (int x = 0; x < chatRoom.members.size(); x++) {
            observableList.add(chatRoom.members.get(x).userName);
        }
        usersListChatroom.setItems(observableList);
    }
}
