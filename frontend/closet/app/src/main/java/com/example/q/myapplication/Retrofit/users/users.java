package com.example.q.myapplication.Retrofit.users;

public class users {
    /**
     * email :
     * id : 17
     * phone :
     * profile : http://120.76.62.132:8080/photos/default.jpg
     * style : cool
     * url : http://120.76.62.132/users/17
     * username : may233
     */

    private String email;
    private int id;
    private String phone;
    private String profile;
    private String style;
    private String url;
    private String username;
    private String password;

    public users(){
        this.email = "";
        this.password = "";
        this.id = 1;
        this.phone = "";
        this.style = "casual";
        this.username = "user";
        this.profile = "";
    }

    public void setPassword(String password){this.password = password; }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
