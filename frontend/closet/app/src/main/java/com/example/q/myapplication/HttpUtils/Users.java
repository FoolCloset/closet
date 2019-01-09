package com.example.q.myapplication.HttpUtils;

import java.util.List;

public class Users {

    /**
     * count : 7
     * next : null
     * previous : null
     * results : [{"id":6,"username":"may4","clothes":[2,4,5,8,16]},{"id":9,"username":"luomei","clothes":[]},{"id":10,"username":"maym","clothes":[]},{"id":11,"username":"xue","clothes":[9,10,11,12,13,14,15,17,18,19,20,21]},{"id":12,"username":"mayma","clothes":[]},{"id":14,"username":"may3","clothes":[6,7]},{"id":17,"username":"may233","clothes":[]}]
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
         * id : 6
         * username : may4
         * clothes : [2,4,5,8,16]
         */

        private int id;
        private String username;
        private List<Integer> clothes;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public List<Integer> getClothes() {
            return clothes;
        }

        public void setClothes(List<Integer> clothes) {
            this.clothes = clothes;
        }
    }
}
