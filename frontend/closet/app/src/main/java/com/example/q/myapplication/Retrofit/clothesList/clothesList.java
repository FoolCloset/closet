package com.example.q.myapplication.Retrofit.clothesList;

import java.util.List;

public class clothesList {
    /**
     * count : 19
     * next : http://120.76.62.132/clothes/?limit=8&offset=8
     * previous : null
     * results : [{"color":"black","id":2,"note":"","pattern":"pure","photo":"http://120.76.62.132:8080/photos/default.jpg","season":"summer","subtype":"other","type":"Tshirt","url":"http://120.76.62.132/clothes/2/","user_id":6},{"color":"black","id":4,"note":"","pattern":"pure","photo":"http://120.76.62.132:8080/photos/default.jpg","season":"winter","subtype":"other","type":"Tshirt","url":"http://120.76.62.132/clothes/4/","user_id":6},{"color":"black","id":5,"note":null,"pattern":"pure","photo":"http://120.76.62.132:8080/photos/default.jpg","season":"summer","subtype":"other","type":"suit","url":"http://120.76.62.132/clothes/5/","user_id":6},{"color":"black","id":6,"note":null,"pattern":"pure","photo":"http://120.76.62.132:8080/photos/default.jpg","season":"summer","subtype":"other","type":"Tshirt","url":"http://120.76.62.132/clothes/6/","user_id":14},{"color":"black","id":7,"note":"","pattern":"pure","photo":"http://120.76.62.132:8080/photos/default.jpg","season":"summer","subtype":"other","type":"Tshirt","url":"http://120.76.62.132/clothes/7/","user_id":14},{"color":"#ff00ff","id":8,"note":"","pattern":"pure","photo":"http://120.76.62.132/img/default.png","season":"summer","subtype":"jeans","type":"coat","url":"http://120.76.62.132/clothes/8/","user_id":6},{"color":"black","id":9,"note":null,"pattern":"pure","photo":"http://120.76.62.132:8080/photos/default.jpg","season":"summer","subtype":"other","type":"other","url":"http://120.76.62.132/clothes/9/","user_id":11},{"color":"black","id":10,"note":null,"pattern":"pure","photo":"http://120.76.62.132:8080/photos/default.jpg","season":"summer","subtype":"other","type":"other","url":"http://120.76.62.132/clothes/10/","user_id":11}]
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
         * color : black
         * id : 2
         * note :
         * pattern : pure
         * photo : http://120.76.62.132:8080/photos/default.jpg
         * season : summer
         * subtype : other
         * type : Tshirt
         * url : http://120.76.62.132/clothes/2/
         * user_id : 6
         */

        private String color;
        private int id;
        private String note;
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

        public String getNote() {
            return note;
        }

        public void setNote(String note) {
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
    }
}
