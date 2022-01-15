package com.psudoinstagram;

import com.psudoinstagram.model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;

import java.util.ArrayList;
import java.util.List;

public class TablesController {

    @FXML
    private ListView<String> simpleList;
    ArrayList<User> show = new ArrayList<>();
    private List<String> stringList = new ArrayList<>();
    // TODO check this
    @FXML
    void simpleList() {
        ObservableList observableList = FXCollections.observableArrayList();
        for(int x=0; x<show.size(); x++) {
            observableList.add(show.get(x).userName);
        }
        simpleList.setItems(observableList);
    }
    @FXML
    void initialize() {
        ObservableList observableList = FXCollections.observableArrayList();
        for(int x=0; x<show.size(); x++) {
            observableList.add(show.get(x).userName);
        }
        simpleList.setItems(observableList);
    }
}
