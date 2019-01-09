package com.example.q.myapplication.HttpUtils;

import java.util.List;

public class Collections {

    /**
     * count : 3
     * next : null
     * previous : null
     * results : [{"id":1,"snapshot":"http://120.76.62.132:8080/photos/default.jpg","user":6,"match":1},{"id":2,"snapshot":"http://120.76.62.132:8080/photos/default.jpg","user":11,"match":2},{"id":10,"snapshot":"http://120.76.62.132:8080/photos/default.jpg","user":11,"match":1}]
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
         * snapshot : http://120.76.62.132:8080/photos/default.jpg
         * user : 6
         * match : 1
         */

        private int id;
        private String snapshot;
        private int user;
        private int match;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getSnapshot() {
            return snapshot;
        }

        public void setSnapshot(String snapshot) {
            this.snapshot = snapshot;
        }

        public int getUser() {
            return user;
        }

        public void setUser(int user) {
            this.user = user;
        }

        public int getMatch() {
            return match;
        }

        public void setMatch(int match) {
            this.match = match;
        }
    }
}
