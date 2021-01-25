package com.edu.aralpan.aralpanmobile.model;

import java.io.Serializable;

public class UserModel implements Serializable {

    private String user, user_game_mode, user_level, user_score;
    private int id;

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getUser_game_mode() {
        return user_game_mode;
    }

    public void setUser_game_mode(String user_game_mode) {
        this.user_game_mode = user_game_mode;
    }

    public String getUser_level() {
        return user_level;
    }

    public void setUser_level(String user_level) {
        this.user_level = user_level;
    }

    public String getUser_score() {
        return user_score;
    }

    public void setUser_score(String user_score) {
        this.user_score = user_score;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}

