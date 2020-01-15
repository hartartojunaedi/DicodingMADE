package com.example.mysubmitdicoding2;

public class NotificationItem {
    private int id;
    private String sender;
    private String Title;
    private String message;

    NotificationItem(int id, String sender, String message,String title) {
        this.id = id;
        this.sender = sender;
        this.message = message;
        this.Title=title;
    }

    //setter and getter
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }

    public String getTitle() {
        return Title;
    }
    public void setTitle(String title) {
        this.Title = title;
    }
}
