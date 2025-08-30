package com.example.wish122;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;

public class SignUpController {

    private double xOffset = 0;
    private double yOffset = 0;
    public TextField realnameInput;
    public PasswordField passwordInput;
    public TextField usernameInput;
    private String userDataDirectory = System.getProperty("user.dir") + "\\src\\main\\resources\\UserData\\";

    private String filename = userDataDirectory + "user.txt";

    private LinkedList<User> userList;

    private User userAccessed;

    public void setUserList(LinkedList<User> userList) {
        this.userList = userList;
    }

    public void signUp(ActionEvent event) throws IOException {
        String realName = realnameInput.getText();
        String password = passwordInput.getText();
        String userName = usernameInput.getText();

        boolean duplicate = false;

        for (User currUser : userList) {
            if(currUser.getUserName().equals(userName)) {
                duplicate = true;
                break;
            }
        }

        if(realName.equals("") || password.equals("") || userName.equals("")) {
            System.out.println("One or more fields are missing!");
        } else if(duplicate) {
            System.out.println("User already exists!");
        } else {
            User newUser = new User(realName, password, userName);
            addUser(newUser, filename);
            userList.add(newUser);
            userAccessed = newUser;

            userAccessed.addSong(new Song("Biscuit",  "lukrembo", "N/A", "Lofi", "lukrembo_biscuit.mp3", false));

            createSongFile(userAccessed.getSongList());

            FXMLLoader loader = new FXMLLoader(getClass().getResource("homegui.fxml"));
            Pane root = loader.load();
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);

            MenuController menuController = loader.getController();
            menuController.setUserAccessed(userAccessed);
            menuController.setUserList(userList);

            root.setOnMousePressed(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    xOffset = mouseEvent.getSceneX();
                    yOffset = mouseEvent.getSceneY();
                }
            });

            root.setOnMouseDragged(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    stage.setX(mouseEvent.getScreenX() - xOffset);
                    stage.setY(mouseEvent.getScreenY() - yOffset);
                }
            });

            stage.setScene(scene);
            stage.show();
        }
    }

    private void addUser(User user, String filename) {
        String folderPath = userDataDirectory + user.getUserName();
        File folder = new File(folderPath);
        String likedSongFilePath = folderPath + "\\songs.txt";
        File songTextFile = new File(likedSongFilePath);
        String songFolderPath = folderPath + "\\songs";
        File songFolder = new File(songFolderPath);
        String songImgFolderPath = folderPath + "\\img";
        File imgFolder = new File(songImgFolderPath);

        user.setSongFileLocation(likedSongFilePath);

        try(FileWriter fileWriter = new FileWriter(filename, true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)) {
            if(userList.size() == 0) {
                bufferedWriter.write(user.getRealName() + "," + user.getPassword() + "," + user.getUserName());
            } else {
                bufferedWriter.write("\n" + user.getRealName() + "," + user.getPassword() + "," + user.getUserName());
            }
        } catch (IOException e) {
            System.out.println("An error occurred while appending the text to the file: " + e.getMessage());
        }


        // Check if the folder already exists
        if (!folder.exists()) {
            // Attempt to create the folder
            boolean success = folder.mkdirs(); // This will create any necessary parent directories
            if (success) {
                System.out.println("Folder created successfully: " + folder.getAbsolutePath());
            } else {
                System.err.println("Failed to create the folder.");
            }
        } else {
            System.out.println("Folder already exists: " + folder.getAbsolutePath());
        }

        if (!imgFolder.exists()) {
            // Attempt to create the folder
            boolean success = imgFolder.mkdirs(); // This will create any necessary parent directories
            if (success) {
                System.out.println("Folder created successfully: " + imgFolder.getAbsolutePath());
            } else {
                System.err.println("Failed to create the folder.");
            }
        } else {
            System.out.println("Folder already exists: " + folder.getAbsolutePath());
        }

        if (!songFolder.exists()) {
            // Attempt to create the folder
            boolean success = songFolder.mkdirs(); // This will create any necessary parent directories
            if (success) {
                System.out.println("Folder created successfully: " + songFolder.getAbsolutePath());
            } else {
                System.err.println("Failed to create the folder.");
            }
        } else {
            System.out.println("Folder already exists: " + folder.getAbsolutePath());
        }

        try {
            // Check if the file already exists
            if (songTextFile.createNewFile()) {
                System.out.println("Text file created successfully: " + songTextFile.getAbsolutePath());
            } else {
                System.out.println("Text file already exists: " + songTextFile.getAbsolutePath());
            }
        } catch (IOException e) {
            System.err.println("An error occurred while creating the text file: " + e.getMessage());
        }
    }

    public void toSignIn(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("user-login.fxml"));
        Pane root = loader.load();
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);

        LoginController loginController = loader.getController();
        loginController.setUserList(userList);

        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void exit(ActionEvent event) {
        System.exit(0);
    }

    private void createSongFile(LinkedList<Song> songList) {

        String songFile = userDataDirectory + userAccessed.getUserName() + "\\songs.txt";

        try {
            FileWriter fileWriter = new FileWriter(songFile);

            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            for(int i=0; i<songList.size(); i++) {
                Song currSong = songList.get(i);
                if(i == 0) {
                    bufferedWriter.write(currSong.getName() + "," + currSong.getArtists() + "," + currSong.getAlbum() + "," + currSong.getGenre() + "," + currSong.getFilename() + "," + "false");
                } else {
                    bufferedWriter.write("\n" + currSong.getName() + "," + currSong.getArtists() + "," + currSong.getAlbum() + "," + currSong.getGenre() + "," + currSong.getFilename() + "," + "false");
                }
            }
            bufferedWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred while overwriting the file.");
            e.printStackTrace();
        }
    }

}
