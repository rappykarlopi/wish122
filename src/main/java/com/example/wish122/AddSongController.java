package com.example.wish122;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Stack;

public class AddSongController {

    public TextField songName;
    public TextField songArtist;
    public TextField songAlbum;
    public TextField songGenre;
    public TextField songFilename;
    public TextField songImgName;
    private MenuController menuController;
    private boolean duplicate = false;
    private String absPath;
    private String imgPath;
    private String originalImgPath;
    private File originalImgFile;
    private String userDataDirectory = System.getProperty("user.dir") + "\\src\\main\\resources\\UserData\\";

    public void setMenuController(MenuController menuController) {
        this.menuController = menuController;
    }

    @FXML
    public void exit(ActionEvent event) {
        System.exit(0);
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

    public void addSong(ActionEvent event) throws IOException {
        String sName = songName.getText();
        String sArtist = songArtist.getText();
        String sAlbum = songAlbum.getText();
        String sGenre = songGenre.getText();
        String sFilename = songFilename.getText();
        String songDestination = userDataDirectory + menuController.userAccessed.getUserName() + "\\songs\\";
        String imgDestination = userDataDirectory + menuController.userAccessed.getUserName() + "\\img\\";

        if(!sName.equals("") && !sArtist.equals("") && !sAlbum.equals("") && !sGenre.equals("") && !sFilename.equals("")) {
            for(int i=0; i<menuController.userAccessed.getSongList().size(); i++) {
                if(menuController.userAccessed.getSongList().get(i).getName().equals(sName)) {
                    duplicate = true;
                    break;
                }
            }
            if(!duplicate) {
                Song newSong = new Song(sName, sArtist, sAlbum, sGenre, sFilename, false);

                menuController.userAccessed.addSong(newSong);
                updateSongFile(menuController.userAccessed.getSongList());
                transferSong(absPath, songDestination);
                transferImg(imgPath, imgDestination);
                File renamedFile = new File(originalImgPath);
                if (originalImgFile.renameTo(renamedFile)) {
                    System.out.println("Success");
                }

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
                    for(int i=0; i<menuController.songs.size(); i++) {
                        menuController.indices.add(i);
                    }
                    Collections.shuffle(menuController.indices);
                    for(int i=0; i<menuController.indices.size(); i++) {
                        menuController.shuffleSongs.push(menuController.indices.get(i));
                    }
                }
                if(menuController.looping) {
                    menuController.looper = new OurCircularQueue<>(menuController.songs.size());
                    for(int i=menuController.songNumber+1; i<menuController.songs.size(); i++) {
                        menuController.looper.enqueue(i);
                    }
                    for(int i=0; i<menuController.songNumber; i++) {
                        menuController.looper.enqueue(i);
                    }
                }
            } else {
                System.out.println("Song Already Exists!");
            }

            songName.clear();
            songArtist.clear();
            songAlbum.clear();
            songGenre.clear();
            songFilename.clear();

        } else {
            System.out.println("One or more fields are missing!");
        }
    }

    public void chooseFile() {
        if (!songName.getText().trim().isEmpty()) {
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("MP3 Files", "*.mp3"));
            var selectedFile = fileChooser.showOpenDialog(null);
            if (selectedFile != null) {
                System.out.println("Selected MP3 file: " + selectedFile.getAbsolutePath());
                songFilename.setText(selectedFile.getName());
                absPath = selectedFile.getAbsolutePath();
            }
        } else {
            System.out.println("ALERT: Please fill-in first the Song Title before uploading your MP3 file.");
        }
    }

    public void transferSong(String sourceFolderPath, String destinationFolderPath) throws IOException {
        Path sourcePath = Paths.get(sourceFolderPath);
        String fileName = sourcePath.getFileName().toString();
        Path destinationPath = Paths.get(destinationFolderPath, fileName);

        Files.copy(sourcePath, destinationPath, StandardCopyOption.REPLACE_EXISTING);
        menuController.songs.add(new File("src\\main\\resources\\UserData\\" + menuController.userAccessed.getUserName() + "\\songs\\" + fileName));
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


    public void chooseImgFile(ActionEvent event) {
        if (!songName.getText().trim().isEmpty()) {
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg"));
            var selectedFile = fileChooser.showOpenDialog(null);

            if (selectedFile != null) {
                originalImgPath = selectedFile.getAbsolutePath();
                String newFileName = songName.getText().trim().toLowerCase().replaceAll(" ", "-");
                String fileExtension = selectedFile.getName().substring(selectedFile.getName().lastIndexOf("."));
                File newFile = new File(selectedFile.getParent(), newFileName + fileExtension);
                if (selectedFile.renameTo(newFile)) {
                    System.out.println("Selected Image file: " + newFile.getAbsolutePath());
                    songImgName.setText(newFile.getName());
                    imgPath = newFile.getAbsolutePath();
                    originalImgFile = newFile;
                } else {
                    System.out.println("Error renaming the file.");
                }
            }
        } else {
            System.out.println("ALERT: Please fill in the Song Title before uploading any Image file.");
        }
    }

    public void transferImg(String sourceFolderPath, String destinationFolderPath) throws IOException {
        Path sourcePath = Paths.get(sourceFolderPath);
        String fileName = sourcePath.getFileName().toString();
        Path destinationPath = Paths.get(destinationFolderPath, fileName);

        Files.copy(sourcePath, destinationPath, StandardCopyOption.REPLACE_EXISTING);
    }
}
