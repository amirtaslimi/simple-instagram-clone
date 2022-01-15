package com.psudoinstagram.model;

import java.util.ArrayList;

public class Post {
    public final int id;
    public User user;

    @Override
    public String toString() {
        return
                " " + user +
                ", text='" + text + '\'';
    }

    private static int helpId;
    public String text;
    public ArrayList<User>likedUsers = new ArrayList<>();
    public ArrayList<Post>comments = new ArrayList<>();

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
