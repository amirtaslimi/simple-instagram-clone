package com.psudoinstagram;

import com.jfoenix.controls.JFXButton;
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
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class UsersPageController implements Initializable {
    User clientUser;
    User searchedUser = new User();
    ObservableList<Post> observableList = FXCollections.observableArrayList();
    @FXML
    private Circle profileImage;
    @FXML
    private Label profileName;
    @FXML
    private JFXButton followUserButton;
    @FXML
    private JFXButton blockUserButton;
    @FXML
    private ListView<Post> userPostList;
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
    void blockUserButton(ActionEvent event) {
        int flag =0;
        for (User usr:searchedUser.followers) {
            if (usr.equals(clientUser)){
                blockUserButton.setText("Follow");
                blockUserButton.setTextFill(Color.BLACK);
                clientUser.blockedUsers.remove(searchedUser);
                flag = 1;
            }
        }
        if(flag==0){
            blockUserButton.setText("Blocked!!");
            blockUserButton.setTextFill(Color.WHITE);
            clientUser.blockedUsers.add(searchedUser);
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
        for (Post mypst:searchedUser.posts) {
            if (mypst.equals(userPostList.getSelectionModel().getSelectedItem())){
                goPostPage(mypst);
            }
        }
    }
    void showPost(){
        for (int x = 0; x < searchedUser.posts.size(); x++) {
            observableList.add(searchedUser.posts.get(x));
        }
        userPostList.setItems(observableList);
    }




    void goPostPage(Post post) throws IOException {
        FXMLLoader postPageLoader = new FXMLLoader(getClass().getResource("postPage.fxml"));
        AnchorPane postPage = postPageLoader.load();
        // Get the Controller from the FXMLLoader
        PostPageController postPageController = postPageLoader.getController();
        postPageController.currentPost = post;
        postPageController.currentUser = clientUser;
        Stage stage = new Stage();
        stage.setTitle("Post Page");
        stage.setScene(new Scene(postPage));
        stage.show();
        postPageController.list();
        postPageController.likeState();
        postPageController.setPost();
    }
    void setProfile(){
        if (searchedUser.profileImage!=null){
            Image image = new Image(searchedUser.profileImage.toURI().toString());
            profileImage.setFill(new ImagePattern(image));
            profileImage.setStroke(Color.SEAGREEN);
        }
        profileName.setText(searchedUser.userName);
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        userPostList.setCellFactory(new Callback<ListView<Post>, ListCell<Post>>() {
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
}
