package com.example.q.myapplication.Retrofit.users;

import org.json.JSONException;
import org.json.JSONObject;

public class sign {


    /**
     * data : {"password":"may123","username":"luomei","id":9,"email":"23324456@qq.com","phone":null,"style":"casual","profile":"http://120.76.62.132:8080/photos/40padded.jpg"}
     */

    private DataBean data;
    private String msg = "";

    public void setMsg(String msg){this.msg = msg;}

    public String getMsg(){return this.msg;}

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * password : may123
         * username : luomei
         * id : 9
         * email : 23324456@qq.com
         * phone : null
         * style : casual
         * profile : http://120.76.62.132:8080/photos/40padded.jpg
         */

        private String password;
        private String username;
        private int id;
        private String email;
        private Object phone;
        private String style;
        private String profile;

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public Object getPhone() {
            return phone;
        }

        public void setPhone(Object phone) {
            this.phone = phone;
        }

        public String getStyle() {
            return style;
        }

        public void setStyle(String style) {
            this.style = style;
        }

        public String getProfile() {
            return profile;
        }

        public void setProfile(String profile) {
            this.profile = profile;
        }
    }

    public void show(){
        System.out.println(this.msg);
    }

    public String getDataString(){
        try {
            DataBean data = getData();
            JSONObject object = new JSONObject();
            object.put("email", this.data.email);
            object.put("username", this.data.username);
            object.put("profile", this.data.profile);
            object.put("style", this.data.style);
            object.put("password", this.data.password);
            object.put("phone", this.data.phone);
            object.put("id", this.data.id);
            return object.toString();
        }catch (JSONException e){
            e.printStackTrace();
            return "";
        }

    }
}
