package com.psudoinstagram.model;

import java.util.ArrayList;

public class User {
    public final int id;
    private static int helpId;
    public String userName;
    public String userPass;
    public ArrayList<Post> posts = new ArrayList<>();
    public ArrayList<User>followers = new ArrayList<>();
    public ArrayList<User>followings = new ArrayList<>();
    public ArrayList<ChatRoom>userChats = new ArrayList<>();


    public User(String userName, String userPass) {
        this.userName = userName;
        this.userPass = userPass;
        id = getNewId();
    }




    @Override
    public String toString() {
        return
                " userName: " + userName  ;
    }

    private int getNewId() {
        helpId++;
        return helpId;
    }

}


