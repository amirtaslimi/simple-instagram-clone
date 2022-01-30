package com.psudoinstagram.model;

import java.io.File;
import java.util.ArrayList;

public class Post {
    public final int id;
    public User user;
    private static int helpId;
    public String text;
    public ArrayList<User>likedUsers = new ArrayList<>();
    public ArrayList<Post>comments = new ArrayList<>();
    public ArrayList<User> taggedUser = new ArrayList<>();
    public String imageFlag;
    public File file;
    public PostType postType;

    public Post(String text, File file) {
        id = getNewId();
        this.text = text;
        this.file=file;
    }

    @Override
    public String toString() {
        return
                " " + user +
                ", text='" + text + '\'';
    }

    public Post(String text) {
        id = getNewId();
        this.text = text;
    }
    public Post(){
        id = getNewId();
    }

    private int getNewId() {
        helpId++;
        return helpId;
    }
}
