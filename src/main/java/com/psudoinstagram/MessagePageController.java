package com.psudoinstagram;

import com.psudoinstagram.model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class MessagePageController {
    User currentUser;
    ChatRoom currentChatRoom;
    Message currentMessage;
    @FXML
    private TextField addUserChatField;
    @FXML
    private TextField chatName;
    @FXML
    private ListView<ChatRoom> chatRoomsList;
    @FXML
    private ListView<Message> messagesList;
    @FXML
    private TextArea sendMessageField;
    @FXML
    private TextField forwardMessageField;
    @FXML
    private Label selectedMessage;

    @FXML
    void addUserChatButton(ActionEvent event) {

    }

    @FXML
    void buildChatButton(ActionEvent event) {
        ChatRoom chatRoom = new ChatRoom(chatName.getText());
        currentUser.userChats.add(chatRoom);
        chatRoom.admins.add(currentUser);
        buildChatRoomsList();
    }

    @FXML
    void chatRoomsList(MouseEvent event) {
        buildChatRoomsList();
        for (ChatRoom chatRoom:currentUser.userChats) {
            if (Objects.equals(chatRoom, chatRoomsList.getSelectionModel().getSelectedItem())){
                buildChatTexts(chatRoom);
                currentChatRoom=chatRoom;
            }
        }
    }
    @FXML
    void forwardMessageButton(ActionEvent event) {

    }
    @FXML
    void messagesList(MouseEvent event) {
        selectedMessage.setText(messagesList.getSelectionModel().getSelectedItem().text);
    }
    @FXML
    void manageChatRoomButton(ActionEvent event) throws IOException {
        currentChatRoom=chatRoomsList.getSelectionModel().getSelectedItem();
        goManageChatroom(currentChatRoom);
    }

    @FXML
    void sendMessageButton(ActionEvent event) {
        Message message = new Message(sendMessageField.getText());
        message.user = currentUser;
        if (!Objects.equals(selectedMessage.getText(), "")){
            message.relatedChat=selectedMessage.getText();
        }
        currentChatRoom.messages.add(message);

        buildChatTexts(currentChatRoom);

    }


    void buildChatRoomsList(){
        ObservableList<ChatRoom> observableList = FXCollections.observableArrayList();
        for (int x = 0; x < currentUser.userChats.size(); x++) {
            observableList.add(currentUser.userChats.get(x));
        }
        chatRoomsList.setItems(observableList);
    }
    void buildChatTexts(ChatRoom chatroom){
        ObservableList<Message> observableList = FXCollections.observableArrayList();
        for (int x = 0; x < chatroom.messages.size(); x++) {
            observableList.add(chatroom.messages.get(x));
        }
        messagesList.setItems(observableList);
    }
    void goManageChatroom(ChatRoom chatRoom) throws IOException {
        FXMLLoader manageChatroomPageLoader = new FXMLLoader(getClass().getResource("manageChatroomPage.fxml"));
        AnchorPane mngechatroom = manageChatroomPageLoader.load();
        // Get the Controller from the FXMLLoader
        ManageChatroomController manageChatroomController = manageChatroomPageLoader.getController();
        manageChatroomController.chatRoom = chatRoom;
        Stage stage = new Stage();
        stage.setTitle("Manage Chatroom");
        stage.setScene(new Scene(mngechatroom));
        stage.show();
        manageChatroomController.showList();
    }
}