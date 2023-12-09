package com.example.myapplication.messages.menu;

import lombok.Data;

@Data
public class ConnectToServer  {
    private String name;
    private boolean isObserver = true;

    public void setName(String name) {
        this.name = name;
    }

    public void setIsObserver(boolean b) {
        this.isObserver = b;
    }
}
