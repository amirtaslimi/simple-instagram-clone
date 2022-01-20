package com.psudoinstagram;

import com.psudoinstagram.model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.controlsfx.control.textfield.TextFields;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;

public class MessagePageController implements Initializable {
    User currentUser = new User();
    ChatRoom currentChatRoom;
    Message currentMessage;

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
    private Label manageStatus;



    @FXML
    void forwardMessageButton(ActionEvent event) {
        for (ChatRoom chatrrom:currentUser.userChats) {
            if(Objects.equals(chatrrom.name, forwardMessageField.getText())){
                Message message = new Message(currentMessage.text);
                message.user=currentUser;
                chatrrom.messages.add(message);
            }
        }
    }

    @FXML
    void buildChatButton(ActionEvent event) {
        ChatRoom chatRoom = new ChatRoom(chatName.getText());
        currentUser.userChats.add(chatRoom);
        chatRoom.admins.add(currentUser);
        chatRoom.members.add(currentUser);
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
        selectedMessage.setText("");
    }
    @FXML
    void clear(MouseEvent event) {
        selectedMessage.setText("");
        manageStatus.setText("");

    }
    @FXML
    void messagesList(MouseEvent event) {
        selectedMessage.setText(messagesList.getSelectionModel().getSelectedItem().text);
        currentMessage=messagesList.getSelectionModel().getSelectedItem();
    }
    @FXML
    void manageChatRoomButton(ActionEvent event) throws IOException {
        if (currentChatRoom.admins.contains(currentUser)){
            currentChatRoom=chatRoomsList.getSelectionModel().getSelectedItem();
            goManageChatroom(currentChatRoom);
        }
        else {
            manageStatus.setText("sorry, you dont have permission!");
            manageStatus.setTextFill(Color.BLACK);
        }
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
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ArrayList<String> auto = new ArrayList<String>();
        for (ChatRoom chRoom:currentUser.userChats) {
            auto.add(chRoom.name);
        }
        TextFields.bindAutoCompletion(forwardMessageField,auto);
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