package com.example.q.myapplication.HttpUtils;

import java.util.List;

public class Clothes {

    /**
     * count : 19
     * next : http://0.0.0.0:8000/clothes/?format=json&limit=8&offset=8
     * previous : null
     * results : [{"url":"http://0.0.0.0:8000/clothes/2/?format=json","id":2,"user_id":6,"type":"Tshirt","subtype":"other","color":"black","season":"summer","pattern":"pure","photo":"http://120.76.62.132:8080/photos/default.jpg","note":""},{"url":"http://0.0.0.0:8000/clothes/4/?format=json","id":4,"user_id":6,"type":"Tshirt","subtype":"other","color":"black","season":"winter","pattern":"pure","photo":"http://120.76.62.132:8080/photos/default.jpg","note":""},{"url":"http://0.0.0.0:8000/clothes/5/?format=json","id":5,"user_id":6,"type":"suit","subtype":"other","color":"black","season":"summer","pattern":"pure","photo":"http://120.76.62.132:8080/photos/default.jpg","note":null},{"url":"http://0.0.0.0:8000/clothes/6/?format=json","id":6,"user_id":14,"type":"Tshirt","subtype":"other","color":"black","season":"summer","pattern":"pure","photo":"http://120.76.62.132:8080/photos/default.jpg","note":null},{"url":"http://0.0.0.0:8000/clothes/7/?format=json","id":7,"user_id":14,"type":"Tshirt","subtype":"other","color":"black","season":"summer","pattern":"pure","photo":"http://120.76.62.132:8080/photos/default.jpg","note":""},{"url":"http://0.0.0.0:8000/clothes/8/?format=json","id":8,"user_id":6,"type":"coat","subtype":"jeans","color":"#ff00ff","season":"summer","pattern":"pure","photo":"http://120.76.62.132/img/default.png","note":""},{"url":"http://0.0.0.0:8000/clothes/9/?format=json","id":9,"user_id":11,"type":"other","subtype":"other","color":"black","season":"summer","pattern":"pure","photo":"http://120.76.62.132:8080/photos/default.jpg","note":null},{"url":"http://0.0.0.0:8000/clothes/10/?format=json","id":10,"user_id":11,"type":"other","subtype":"other","color":"black","season":"summer","pattern":"pure","photo":"http://120.76.62.132:8080/photos/default.jpg","note":null}]
     */

    private int count;
    private String next;
    private Object previous;
    private List<ResultsBean> results;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getNext() {
        return next;
    }

    public void setNext(String next) {
        this.next = next;
    }

    public Object getPrevious() {
        return previous;

    }

    public void setPrevious(Object previous) {
        this.previous = previous;
    }

    public List<ResultsBean> getResults() {
        return results;
    }

    public void setResults(List<ResultsBean> results) {
        this.results = results;
    }

    public static class ResultsBean {
        /**
         * url : http://0.0.0.0:8000/clothes/2/?format=json
         * id : 2
         * user_id : 6
         * type : Tshirt
         * subtype : other
         * color : black
         * season : summer
         * pattern : pure
         * photo : http://120.76.62.132:8080/photos/default.jpg
         * note :
         */

        private String url;
        private int id;
        private int user_id;
        private String type;
        private String subtype;
        private String color;
        private String season;
        private String pattern;
        private String photo;
        private String note;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getSubtype() {
            return subtype;
        }

        public void setSubtype(String subtype) {
            this.subtype = subtype;
        }

        public String getColor() {
            return color;
        }

        public void setColor(String color) {
            this.color = color;
        }

        public String getSeason() {
            return season;
        }

        public void setSeason(String season) {
            this.season = season;
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

        public String getNote() {
            return note;
        }

        public void setNote(String note) {
            this.note = note;
        }
    }
}
