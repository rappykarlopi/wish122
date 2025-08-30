package com.example.wish122;

import java.util.LinkedList;

public class User {

    private String realName, password, userName, songFileLocation;

    private LinkedList<Song> songList = new LinkedList<>();
    private LinkedList<Song> likedSongs = new LinkedList<>();

    private boolean admin = false;

    public User(String realName, String password, String userName) {
        this.realName = realName;
        this.userName = userName;
        this.password = password;
    }

    public LinkedList<Song> getLikedSongs() {
        return likedSongs;
    }

    public String getSongFileLocation() {
        return songFileLocation;
    }

    public void setSongFileLocation(String songFileLocation) {
        this.songFileLocation = songFileLocation;
    }

    public void addSong(Song song) {
        songList.add(song);
    }

    public void resetSongList() {
        songList = new LinkedList<>();
    }

    public void addLikedSongs(Song song) {
        likedSongs.add(song);
    }

    public void removeLikedSong(String name) {
        for(int i=0; i<likedSongs.size(); i++) {
            if(likedSongs.get(i).getName().equals(name)) {
                likedSongs.remove(i);
            }
        }
    }

    public void removeSong(String name) {
        for(int i=0; i<songList.size(); i++) {
            if(songList.get(i).getName().equals(name)) {
                songList.remove(i);
            }
        }
    }

    public LinkedList<Song> getSongList() {
        return songList;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public boolean getAdmin() {
        return admin;
    }

}
