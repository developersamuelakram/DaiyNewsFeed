package com.example.newsfeed.Adapters;

public class Model {

    String  url, urlToImage, title, source, description, time;

    public Model(String url, String urlToImage, String title, String source, String description, String time) {

        this.url = url;
        this.urlToImage = urlToImage;
        this.title = title;
        this.source = source;
        this.time = time;
        this.description = description;

    }

        public String getUrl () {
            return url;
        }

        public void setUrl (String url){
            this.url = url;
        }

        public String getUrlToImage () {
            return urlToImage;
        }

        public void setUrlToImage (String urlToImage){
            this.urlToImage = urlToImage;
        }

        public String getTitle () {
            return title;
        }

        public void setTitle (String title){
            this.title = title;
        }

        public String getSource () {
            return source;
        }

        public void setSource (String source){
            this.source = source;
        }

        public String getDescription () {
            return description;
        }

        public void setDescription (String description){
            this.description = description;
        }

        public String getTime () {
            return time;
        }

        public void setTime (String time){
            this.time = time;
        }


    }