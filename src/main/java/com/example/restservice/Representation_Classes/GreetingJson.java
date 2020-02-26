package com.example.restservice.Representation_Classes;

public class GreetingJson {
    private final long id;
    private final String content;

    public GreetingJson(long id, String content) {
        this.id = id;
        this.content = content;
    }

    public long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }
}
