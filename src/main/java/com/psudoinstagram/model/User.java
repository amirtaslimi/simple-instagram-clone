package com.psudoinstagram.model;

import java.io.File;
import java.util.ArrayList;

public class User {
    public final int id;
    private static int helpId;
    public String userName;
    public String userPass;
    public File profileImage;
    public String phone;
    public String email;
    public ArrayList<Post> taggedPosts = new ArrayList<>();
    public ArrayList<Post> posts = new ArrayList<>();
    public ArrayList<User>followers = new ArrayList<>();
    public ArrayList<User>followings = new ArrayList<>();
    public ArrayList<User>blockedUsers = new ArrayList<>();
    public ArrayList<ChatRoom>userChats = new ArrayList<>();


    public User() {
        id = 0;
    }

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


