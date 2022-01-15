package com.psudoinstagram.model;

import java.util.ArrayList;

public class ChatRoom {
    public final int id;
    private static int helpId;

    @Override
    public String toString() {
        return
                 name
              ;
    }

    public String name;
    public ArrayList<User>admins = new ArrayList<>();
    public ArrayList<User>members= new ArrayList<>();
    public ArrayList<Message> messages = new ArrayList<>();

    public ChatRoom(String name) {
        this.name = name;
        id = getNewId();
    }
    private int getNewId() {
        helpId++;
        return helpId;
    }
}
