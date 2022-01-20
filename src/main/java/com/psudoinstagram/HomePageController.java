package com.psudoinstagram;

import com.psudoinstagram.model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.Duration;
import org.controlsfx.control.textfield.TextFields;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class HomePageController implements Initializable {
    User consumerUser;
    ObservableList<Post> observableList = FXCollections.observableArrayList();

    @FXML
    private ListView<Post> homeList;
    @FXML
    private TextField searchUserField;
    @FXML
    private Label searchLabel;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ArrayList<String> auto = new ArrayList<String>();
        for (User usr:Data.allUsers) {
            auto.add(usr.userName);
        }
        TextFields.bindAutoCompletion(searchUserField,auto);

        homeList.setCellFactory(new Callback<ListView<Post>, ListCell<Post>>() {
            @Override
            public ListCell<Post> call(ListView<Post> stringListView) {
                ListCell<Post> cell = new ListCell<Post>() {
                    @Override
                    protected void updateItem(Post pst, boolean btl) {
                        super.updateItem(pst, btl);
                        if (pst != null) {
                            if (pst.postType == PostType.PHOTO){
                                setText(pst.text);
                                Image image = new Image(pst.file.toURI().toString());
                                ImageView imageView = new ImageView(image);
                                imageView.setX(170);
                                imageView.setY(10);
                                imageView.setFitWidth(270);
                                imageView.setPreserveRatio(true);
                                setGraphic(imageView);
                            }
                            else if (pst.postType == PostType.VIDEO){
                                setText(pst.text);
                                Media media = new Media(pst.file.toURI().toString());
                                MediaPlayer player = new MediaPlayer(media);
                                MediaView mediaView = new MediaView(player);
                                player.play();
                                player.setStopTime(Duration.seconds(30));
                                mediaView.setX(170);
                                mediaView.setY(10);
                                mediaView.setFitWidth(270);
                                mediaView.setPreserveRatio(true);
                                setGraphic(mediaView);
                            }
                            else {
                                setText(pst.text);
                            }
                        }
                        else {
                            setText(null);
                            setGraphic(null);
                        }
                    }
                };
                return cell;}
        });


    }
    @FXML
    void postPageButton(ActionEvent event) throws IOException {
        for (Post homepost:Data.HomePost) {
            if (homepost.equals(homeList.getSelectionModel().getSelectedItem())){
                goPostPage(homepost);
            }
        }
    }
    @FXML
    void searchUserButton(ActionEvent event) throws IOException {
        for (User usr: Data.allUsers) {
            if (usr.userName.equals(searchUserField.getText()) && !usr.blockedUsers.contains(consumerUser)){
                searchLabel.setText("User founded!.");
                searchLabel.setTextFill(Color.GREEN);
                goUserPage(usr);
            }
            else if (usr.userName.equals(searchUserField.getText()) && usr.blockedUsers.contains(consumerUser)){
                searchLabel.setText("Sorry you were blocked by user.");
                searchLabel.setTextFill(Color.RED);
                break;
            }
        }
    }
    @FXML
    void refreshHomePage(ActionEvent event) {
        for (int x = 0; x < Data.HomePost.size(); x++) {
            if (! consumerUser.posts.contains(Data.HomePost.get(x))){
                observableList.add(Data.HomePost.get(x));
            }
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
        usersPageController.setProfile();
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
