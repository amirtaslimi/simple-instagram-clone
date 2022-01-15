package com.psudoinstagram.model;

public class Message {
    public final int id;
    private static int helpId;
    public User user;
    public String text;
    public String relatedChat;

    @Override
    public String toString() {
        return
                 user +
                 "  reply on:" + relatedChat +
                 "  text: " + text ;
    }


    public Message(String text) {
        id = getNewId();
        this.text = text;
    }
    private int getNewId() {
        helpId++;
        return helpId;
    }
}
