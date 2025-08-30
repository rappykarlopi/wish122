package com.example.wish122;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Scanner;

public class Wish122 extends Application {

    private double xOffset = 0;
    private double yOffset = 0;

    private static LinkedList<User> userList = new LinkedList<>();

    private static String userDataDirectory = System.getProperty("user.dir") + "\\src\\main\\resources\\UserData\\";

    private static String filename = userDataDirectory + "user.txt";

    @Override
    public void start(Stage stage) throws IOException {

        checkUserDirectory();
        loadAccount(filename);

        FXMLLoader loader = new FXMLLoader(Wish122.class.getResource("user-login.fxml"));

        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.initStyle(StageStyle.DECORATED.UNDECORATED);

        LoginController loginController = loader.getController();
        loginController.setUserList(userList);

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

    public static void main(String[] args) {
        launch();
    }

    private static void loadAccount(String filename) {
        File file = new File(filename);
        if(file.exists()) {
            try {
                FileReader fileReader = new FileReader(filename);
                Scanner text = new Scanner(fileReader);
                String[] user = null;
                while (text.hasNext()) {
                    String line = text.nextLine();
                    user = line.split(",");
                    User loadUser = new User(user[0], user[1], user[2]);
                    loadSongs(loadUser);
                    userList.add(loadUser);
                }
            } catch (java.io.FileNotFoundException e) {
                System.out.println("File does not Exist");
            }
        } else {
            try {
                FileWriter fileWriter = new FileWriter(filename);

                fileWriter.close();
            } catch (IOException e) {
                System.out.println("An error occurred while creating the file.");
                e.printStackTrace();
            }
        }
    }

    private static void loadSongs(User user) {
        String songFile = userDataDirectory + user.getUserName() + "\\songs.txt";
        try {
            FileReader fileReader = new FileReader(songFile);
            Scanner text = new Scanner(fileReader);
            String[] song = null;
            while (text.hasNext()) {
                String line = text.nextLine();
                song = line.split(",");
                if(song[5].equals("true")) {
                    user.addSong(new Song(song[0], song[1], song[2], song[3], song[4], true));
                    user.addLikedSongs(new Song(song[0], song[1], song[2], song[3], song[4], true));
                } else if(song[5].equals("false")) {
                    user.addSong(new Song(song[0], song[1], song[2], song[3], song[4], false));
                }
            }
        } catch (java.io.FileNotFoundException e) {
            System.out.println("File does not Exist");
        }
    }

    private static void loadLikedSongs(User user) {
        String songFile = userDataDirectory + user.getUserName() + "\\likedsongs.txt";
        try {
            FileReader fileReader = new FileReader(songFile);
            Scanner text = new Scanner(fileReader);
            String[] song = null;
            while (text.hasNext()) {
                String line = text.nextLine();
                song = line.split(",");
                if(song[5].equals("true")) {
                    user.addLikedSongs(new Song(song[0], song[1], song[2], song[3], song[4], true));
                }
            }
        } catch (java.io.FileNotFoundException e) {
            System.out.println("File does not Exist");
        }
    }

    private static void checkUserDirectory() {
        File folder = new File(userDataDirectory);
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

    }
}