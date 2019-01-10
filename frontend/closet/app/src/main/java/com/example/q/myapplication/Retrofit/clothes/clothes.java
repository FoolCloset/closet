package com.example.q.myapplication.Retrofit.clothes;

public class clothes {
    /**
     * color : black
     * id : 10
     * note : null
     * pattern : pure
     * photo : http://120.76.62.132:8080/photos/default.jpg
     * season : summer
     * subtype : other
     * type : other
     * url : http://120.76.62.132/clothes/10/
     * user_id : 11
     */

    private String color;
    private int id;
    private Object note;
    private String pattern;
    private String photo;
    private String season;
    private String subtype;
    private String type;
    private String url;
    private int user_id;

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Object getNote() {
        return note;
    }

    public void setNote(Object note) {
        this.note = note;
    }

    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getSeason() {
        return season;
    }

    public void setSeason(String season) {
        this.season = season;
    }

    public String getSubtype() {
        return subtype;
    }

    public void setSubtype(String subtype) {
        this.subtype = subtype;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public void show(){
//        life is so dramatic, bug is so terrific
        System.out.println(" test ");

    }
}
