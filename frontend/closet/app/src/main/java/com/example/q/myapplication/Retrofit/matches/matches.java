package com.example.q.myapplication.Retrofit.matches;

import java.io.IOException;

public class matches {
    /**
     * id : 6
     * user : http://0.0.0.0:8000/users/6?format=json
     * clothes_list : -1
     * like : false
     * occasion : daily
     */
    private int status;
    protected content content;

    private class content{
        private int id;
        private String user;
        private String clothes_list;
        private boolean like;
        private String occasion;
    }

    private int id;
    private String user;
    private String clothes_list;
    private boolean like;
    private String occasion;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getClothes_list() {
        return clothes_list;
    }

    public void setClothes_list(String clothes_list) {
        this.clothes_list = clothes_list;
    }

    public boolean isLike() {
        return like;
    }

    public void setLike(boolean like) {
        this.like = like;
    }

    public String getOccasion() {
        return occasion;
    }

    public void setOccasion(String occasion) {
        this.occasion = occasion;
    }

    public void show(){
        try {

            System.out.println(status);

            System.out.println(content.id);
            System.out.println(content.user);
            System.out.println(content.clothes_list);
            System.out.println(content.occasion);
            System.out.println(content.like);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
