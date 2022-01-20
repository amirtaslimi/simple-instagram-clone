package com.psudoinstagram;

import com.jfoenix.controls.JFXRadioButton;
import com.psudoinstagram.model.Data;
import com.psudoinstagram.model.Post;
import com.psudoinstagram.model.PostType;
import com.psudoinstagram.model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.ImageCursor;
import javafx.scene.Scene;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class ProfileController implements Initializable {
    User profileUser = new User();
    final FileChooser fileChooser = new FileChooser();
    final ObservableList<Post> observableList = FXCollections.observableArrayList();
    @FXML
    private ListView<Post> mypostList;
    @FXML
    private TextArea sendPost;
    @FXML
    private JFXRadioButton photoRedioButton;
    @FXML
    private JFXRadioButton videoRedioButton;


    @FXML
    void deletePostButton(ActionEvent event) {

    }


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
        if (photoRedioButton.isSelected()){
            File file = fileChooser.showOpenDialog(null);
            if (!sendPost.getText().isEmpty()) {
                Post post = new Post(sendPost.getText(),file);
                post.user=profileUser;
                post.postType = PostType.PHOTO;
                profileUser.posts.add(post);
                Data.HomePost.add(post);
            }
        }
        else if (videoRedioButton.isSelected()){
            File file = fileChooser.showOpenDialog(null);
            if (!sendPost.getText().isEmpty()) {
                Post post = new Post(sendPost.getText(),file);
                post.user=profileUser;
                post.postType = PostType.VIDEO;
                profileUser.posts.add(post);
                Data.HomePost.add(post);
            }
        }
        else {
            if (!sendPost.getText().isEmpty()) {
                Post post = new Post(sendPost.getText());
                post.user=profileUser;
                post.postType = PostType.TEXT;
                profileUser.posts.add(post);
                Data.HomePost.add(post);
            }
        }
        list();
    }
    @FXML
    void postPage(ActionEvent event) throws IOException {
        for (Post mypst:profileUser.posts) {
            if (mypst.equals(mypostList.getSelectionModel().getSelectedItem())){
                goPostPage(mypst);
            }
        }
    }

    void list(){
        for (int x = 0; x < profileUser.posts.size(); x++) {
            if (!observableList.contains(profileUser.posts.get(x))){
                observableList.add(profileUser.posts.get(x));
            }
        }
        mypostList.setItems(observableList);
   }
    void goPostPage(Post post) throws IOException {
        FXMLLoader postPageLoader = new FXMLLoader(getClass().getResource("postPage.fxml"));
        AnchorPane postPage = postPageLoader.load();
        // Get the Controller from the FXMLLoader
        PostPageController postPageController = postPageLoader.getController();
        postPageController.currentPost = post;
        postPageController.currentUser = profileUser;
        Stage stage = new Stage();
        stage.setTitle("Post Page");
        stage.setScene(new Scene(postPage));
        stage.show();
        postPageController.list();
        postPageController.likeState();
        postPageController.setPost();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //observableList.add(new Post("lkjlkjlkj","F:/amir.jpg"));

//        for (int x = 0; x < profileUser.posts.size(); x++) {
//            observableList.add(profileUser.posts.get(x));
//        }
        //mypostList.setItems(observableList);

        mypostList.setCellFactory(new Callback<ListView<Post>, ListCell<Post>>() {
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
