package com.example.wish122;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;

public class EditSongController {

    public ListView listOfSongs;
    public TextField songName;
    public TextField songArtist;
    public TextField songAlbum;
    public TextField songGenre;
    public TextField searchInput;
    private MenuController menuController;
    private String currSong;
    private ObservableList<String> listOfSongNames = FXCollections.observableArrayList();
    private ObservableList<Song> defaultAllSongList;
    private ObservableList<Song> sortAllSongNameSongList;

    private String userDataDirectory = System.getProperty("user.dir") + "\\src\\main\\resources\\UserData\\";

    public void setMenuController(MenuController menuController) {
        this.menuController = menuController;
    }

    public void initialize() {
        listOfSongs.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                for(int i=0; i<menuController.userAccessed.getSongList().size(); i++) {
                    if(menuController.userAccessed.getSongList().get(i).getName().equals(newValue)) {
                        currSong = menuController.userAccessed.getSongList().get(i).getName();
                        songName.setText(menuController.userAccessed.getSongList().get(i).getName());
                        songArtist.setText(menuController.userAccessed.getSongList().get(i).getArtists());
                        songAlbum.setText(menuController.userAccessed.getSongList().get(i).getAlbum());
                        songGenre.setText(menuController.userAccessed.getSongList().get(i).getGenre());
                        break;
                    }
                }
            }
        });
    }


    public void search(ActionEvent event) {
        if(!searchInput.getText().isEmpty()) {
            String[] inputtedSearch = searchInput.getText().split(" ");
            listOfSongNames = FXCollections.observableArrayList();
            for(int i=0; i<menuController.userAccessed.getSongList().size(); i++) {
                String[] keywords = menuController.userAccessed.getSongList().get(i).getName().split(" ");
                for(int j=0; j< keywords.length; j++) {
                    for(int k=0; k<inputtedSearch.length; k++) {
                        if(keywords[j].toLowerCase().contains(inputtedSearch[k].toLowerCase())) {
                            listOfSongNames.add(menuController.userAccessed.getSongList().get(i).getName());
                            break;
                        }
                    }
                }
            }
        }
        listOfSongs.setItems(listOfSongNames);
    }

    public void saveChanges(ActionEvent event) {
        String sName = songName.getText();
        String sArtist = songArtist.getText();
        String sAlbum = songAlbum.getText();
        String sGenre = songGenre.getText();
        for(int i=0; i<menuController.userAccessed.getSongList().size(); i++) {
            if(menuController.userAccessed.getSongList().get(i).getName().equals(currSong)) {
                menuController.userAccessed.getSongList().get(i).setName(sName);
                menuController.userAccessed.getSongList().get(i).setArtists(sArtist);
                menuController.userAccessed.getSongList().get(i).setAlbum(sAlbum);
                menuController.userAccessed.getSongList().get(i).setGenre(sGenre);
                if(menuController.songName.getText().equals(currSong)) {
                    menuController.songName.setText(sName);
                    menuController.songArtist.setText(sArtist);
                }
                updateSongFile(menuController.userAccessed.getSongList());
                break;
            }
        }
        if(menuController.allSort) {
            setSortSongNameAllSongList();
            menuController.allSongTable.refresh();
            menuController.allSongTable.setItems(sortAllSongNameSongList);
        } else {
            setSortSongNameAllSongList();
            setAllSongTable();
        }
    }

    private void setSortSongNameAllSongList() {
        sortAllSongNameSongList = FXCollections.observableArrayList();
        BinaryTreeSearch bst = new BinaryTreeSearch();
        for(int i=0; i<menuController.userAccessed.getSongList().size(); i++) {
            bst.insert(menuController.userAccessed.getSongList().get(i).getName(), menuController.userAccessed.getSongList().get(i));
        }
        bst.inOrderTraversal(sortAllSongNameSongList);
    }
    private void setAllSongTable() {
        defaultAllSongList = FXCollections.observableArrayList();

        for(int i=0; i<menuController.userAccessed.getSongList().size(); i++) {
            defaultAllSongList.add((Song) menuController.userAccessed.getSongList().get(i));
        }
        menuController.allSongTable.refresh();
        menuController.allSongTable.setItems(defaultAllSongList);
    }

    private void updateSongFile(LinkedList<Song> songList) {

        String songFile = userDataDirectory + menuController.userAccessed.getUserName() + "\\songs.txt";

        try {
            FileWriter fileWriter = new FileWriter(songFile);

            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            for(int i=0; i<songList.size(); i++) {
                Song currSong = songList.get(i);
                if(currSong.isLiked()) {
                    if(i == 0) {
                        bufferedWriter.write(currSong.getName() + "," + currSong.getArtists() + "," + currSong.getAlbum() + "," + currSong.getGenre() + "," + currSong.getFilename() + "," + "true");
                    } else {
                        bufferedWriter.write("\n" + currSong.getName() + "," + currSong.getArtists() + "," + currSong.getAlbum() + "," + currSong.getGenre() + "," + currSong.getFilename() + "," + "true");
                    }
                } else {
                    if(i == 0) {
                        bufferedWriter.write(currSong.getName() + "," + currSong.getArtists() + "," + currSong.getAlbum() + "," + currSong.getGenre() + "," + currSong.getFilename() + "," + "false");
                    } else {
                        bufferedWriter.write("\n" + currSong.getName() + "," + currSong.getArtists() + "," + currSong.getAlbum() + "," + currSong.getGenre() + "," + currSong.getFilename() + "," + "false");
                    }
                }
            }
            bufferedWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred while overwriting the file.");
            e.printStackTrace();
        }
    }

}