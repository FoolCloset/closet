package com.example.q.myapplication.HttpUtils;

import java.util.List;

public class Match {

    /**
     * count : 4
     * next : null
     * previous : null
     * results : [{"id":1,"user":"http://0.0.0.0:8000/users/6?format=json","clothes_list":"1,3,4","like":false,"occasion":"daily"},{"id":2,"user":"http://0.0.0.0:8000/users/6?format=json","clothes_list":"1,2,3,","like":false,"occasion":"daily"},{"id":5,"user":"http://0.0.0.0:8000/users/6?format=json","clothes_list":"2,4,5,","like":false,"occasion":"daily"},{"id":6,"user":"http://0.0.0.0:8000/users/6?format=json","clothes_list":"-1","like":false,"occasion":"daily"}]
     */

    private int count;
    private Object next;
    private Object previous;
    private List<ResultsBean> results;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Object getNext() {
        return next;
    }

    public void setNext(Object next) {
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
         * id : 1
         * user : http://0.0.0.0:8000/users/6?format=json
         * clothes_list : 1,3,4
         * like : false
         * occasion : daily
         */

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
    }
}
