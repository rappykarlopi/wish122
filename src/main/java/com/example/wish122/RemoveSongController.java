package com.example.wish122;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.TextField;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Stack;

public class RemoveSongController {

    public Label statusLabel;
    public TextField searchInput;
    public ListView listOfSongs;
    private String selectedSong;

    private ObservableList<String> listOfSongNames = FXCollections.observableArrayList();

    private MenuController menuController;
    private String userDataDirectory = System.getProperty("user.dir") + "\\src\\main\\resources\\UserData\\";

    @FXML
    public void exit(ActionEvent event) {
        System.exit(0);
    }

    public void initialize() {
        listOfSongs.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                selectedSong = newValue;
            }
        });
    }

    public void setMenuController(MenuController menuController) {
        this.menuController = menuController;
        for(int i=0; i<menuController.userAccessed.getSongList().size(); i++) {
            listOfSongNames.add(menuController.userAccessed.getSongList().get(i).getName());
        }
        listOfSongs.setItems(listOfSongNames);
    }

    private void setSortSongNameAllSongList() {
        menuController.sortAllSongNameSongList = FXCollections.observableArrayList();
        BinaryTreeSearch bst = new BinaryTreeSearch();
        for(int i=0; i<menuController.userAccessed.getSongList().size(); i++) {
            bst.insert(menuController.userAccessed.getSongList().get(i).getName(), menuController.userAccessed.getSongList().get(i));
        }
        bst.inOrderTraversal(menuController.sortAllSongNameSongList);
    }
    private void setAllSongTable() {
        menuController.defaultAllSongList = FXCollections.observableArrayList();

        for(int i=0; i<menuController.userAccessed.getSongList().size(); i++) {
            menuController.defaultAllSongList.add((Song) menuController.userAccessed.getSongList().get(i));
        }
        menuController.allSongTable.setItems(menuController.defaultAllSongList);
    }

    public void removeSong() {
        Song removeSong = null;
        String[] extensions = {".png", ".jpg", ".jpeg", ".gif"};

        if(!menuController.songName.getText().equals(selectedSong)) {
            for(int i=0; i<menuController.userAccessed.getSongList().size(); i++) {
                if(selectedSong.equalsIgnoreCase(menuController.userAccessed.getSongList().get(i).getName())) {
                    removeSong = menuController.userAccessed.getSongList().get(i);
                    menuController.userAccessed.getSongList().remove(i);
                    for(int j=0; j<menuController.songs.size(); j++) {
                        if(removeSong.getFilename().equals(menuController.songs.get(j).getName())) {
                            File deleteFile = menuController.songs.get(j).getAbsoluteFile();
                            deleteFile.delete();
                            menuController.songs.remove(j);
                            System.out.println(menuController.songs.size());
                            if(!removeSong.getName().trim().toLowerCase().replaceAll(" ", "-").isEmpty()) {
                                String fileName = removeSong.getName().trim().toLowerCase().replaceAll(" ", "-");
                                Path filePath = Paths.get(userDataDirectory, menuController.userAccessed.getUserName(), "songs", fileName);
                                for (String extension : extensions) {
                                    InputStream inputStream = getClass().getResourceAsStream(filePath + extension);
                                    if (inputStream != null) {
                                        Path newFilePath = Paths.get(filePath + extension);
                                        File fileToDel = newFilePath.toFile();
                                        if (fileToDel.exists() && fileToDel.isFile()) {
                                            if (fileToDel.delete()) {
                                                System.out.println("Song file removed: " + fileName);
                                                break;
                                            } else {
                                                System.out.println("Error removing song file: " + fileName);
                                                break;
                                            }
                                        } else {
                                            System.out.println("Song file not found: " + fileName);
                                            break;
                                        }
                                    }
                                }
                            }
                            break;
                        }
                    }
                    menuController.songNumber = menuController.songNumber - 1;
                    if(menuController.allSort) {
                        setSortSongNameAllSongList();
                        menuController.allSongTable.setItems(menuController.sortAllSongNameSongList);
                    } else {
                        setSortSongNameAllSongList();
                        setAllSongTable();
                    }
                    if(menuController.shuffling) {
                        menuController.indices = new ArrayList<>();
                        menuController.shuffleSongs = new Stack<>();
                        menuController.prevShuffleSongs = new Stack<>();
                        for(int k=0; k<menuController.songs.size(); k++) {
                            menuController.indices.add(k);
                        }
                        Collections.shuffle(menuController.indices);
                        for(int l=0; l<menuController.indices.size(); l++) {
                            menuController.shuffleSongs.push(menuController.indices.get(l));
                        }
                    }
                    if(menuController.looping) {
                        menuController.looper = new OurCircularQueue<>(menuController.songs.size());
                        for(int m=menuController.songNumber+1; m<menuController.songs.size(); m++) {
                            menuController.looper.enqueue(m);
                        }
                        for(int n=0; n<menuController.songNumber; n++) {
                            menuController.looper.enqueue(n);
                        }
                    }
                    updateSongFile(menuController.userAccessed.getSongList());
                    updateList();
                    break;
                }
            }
        } else {
            System.out.println("Song is playing!");
        }
    }

    public void search() {
        if(!searchInput.getText().isEmpty()) {
            String[] inputtedSearch = searchInput.getText().split(" ");
            listOfSongNames = FXCollections.observableArrayList();
            for(int i=0; i<menuController.userAccessed.getSongList().size(); i++) {
                String[] keywords = menuController.userAccessed.getSongList().get(i).getName().split(" ");
                for(int j=0; j< keywords.length; j++) {
                    for(int k=0; k<inputtedSearch.length; k++) {
                        if(keywords[j].equalsIgnoreCase(inputtedSearch[k])) {
                            listOfSongNames.add(menuController.userAccessed.getSongList().get(i).getName());
                            break;
                        }
                    }
                }
            }
        } else {
            listOfSongNames = FXCollections.observableArrayList();
            for(int i=0; i<menuController.userAccessed.getSongList().size(); i++) {
                listOfSongNames.add(menuController.userAccessed.getSongList().get(i).getName());
            }
        }
        listOfSongs.setItems(listOfSongNames);
    }

    public void clear() {
        searchInput.clear();
        listOfSongNames = FXCollections.observableArrayList();
        for(int i=0; i<menuController.userAccessed.getSongList().size(); i++) {
            listOfSongNames.add(menuController.userAccessed.getSongList().get(i).getName());
        }
        listOfSongs.setItems(listOfSongNames);
    }

    private void updateList() {
        listOfSongNames = FXCollections.observableArrayList();
        for(int i=0; i<menuController.userAccessed.getSongList().size(); i++) {
            listOfSongNames.add(menuController.userAccessed.getSongList().get(i).getName());
        }
        listOfSongs.setItems(listOfSongNames);
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