package com.example.wish122;

public class Song {

    private String name, artists, album, genre, filename;
    private boolean liked = false;

    public Song(String name, String artists, String album, String genre, String filename, boolean liked) {
        this.name = name;
        this.artists = artists;
        this.album = album;
        this.genre = genre;
        this.filename = filename;
        if(liked) {
            this.liked = true;
        } else {
            this.liked = false;
        }
    }

    public boolean isLiked() {
        return liked;
    }

    public void setLiked(boolean liked) {
        this.liked = liked;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getArtists() {
        return artists;
    }

    public void setArtists(String artists) {
        this.artists = artists;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }
}
