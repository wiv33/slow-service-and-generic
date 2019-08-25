package com.example.webclient.demo.repsitory;

public class Workpart {
    private String title;
    private String location;

//    public Workpart() {
//    }

    public Workpart(String title, String location) {
        this.title = title;
        this.location = location;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return "Workpart{" +
                "title='" + title + '\'' +
                ", location='" + location + '\'' +
                '}';
    }
}
