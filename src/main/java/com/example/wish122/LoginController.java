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

import java.io.IOException;
import java.util.LinkedList;

public class LoginController {

    private double xOffset = 0;
    private double yOffset = 0;
    public TextField usernameInput;
    public PasswordField passwordInput;

    private LinkedList<User> userList;

    private User userAccessed;

    @FXML
    public void exit(ActionEvent event) {
        System.exit(0);
    }

    public void setUserList(LinkedList<User> userList) {
        this.userList = userList;
    }

    @FXML
    private void login(ActionEvent event) throws IOException {
        Boolean login = false;
        String userName = usernameInput.getText();
        String password = passwordInput.getText();

        if(userName.equals("") || password.equals("")) {
            System.out.println("One or more fields are missing!");
        } else {
            for(int i=0; i< userList.size(); i++) {
                if(userList.get(i).getUserName().equals(userName)) {
                    if(userList.get(i).getPassword().equals(password)) {
                        login = true;
                        userAccessed = userList.get(i);
                        System.out.println("Log In Complete");
                        break;
                    } else {
                        login = false;
                        break;
                    }
                } else {
                    login = false;
                }
            }
            if(!login) {
                System.out.println("Username or Password error!");
            }
        }
        if(login) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("homegui.fxml"));
            Pane root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);

            MenuController menuController = loader.getController();
            menuController.setUserAccessed(userAccessed);
            menuController.setUserList(userList);

            stage.setScene(scene);
            stage.show();

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


    public void toSignUp(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("user-signup.fxml"));
        Pane root = loader.load();
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);

        SignUpController signUpController = loader.getController();
        signUpController.setUserList(userList);

        stage.setScene(scene);
        stage.show();
    }
}