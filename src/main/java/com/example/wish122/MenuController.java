package com.example.wish122;

import com.mpatric.mp3agic.*;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.*;
import java.util.*;

public class MenuController {
    public TableView<Song> likedSongTable;
    public TableColumn<Song, String> likedSongTitle;
    public TableColumn<Song, String> likedSongArtist;
    public TableColumn<Song, String> likedSongAlbum;
    public TableColumn<Song, String> likedSongGenre;
    public TableView<Song> allSongTable;
    public TableColumn<Song, String> allSongTitle;
    public TableColumn<Song, String> allSongArtist;
    public TableColumn<Song, String> allSongAlbum;
    public TableColumn<Song, String> allSongGenre;
    public ImageView likesongimg;
    public ImageView shuffleImg;
    public ImageView loopImg;
    public ImageView ghostButton;
    public ImageView mephistoButton;
    public ImageView overdoseButton;
    public ImageView yorunopierrotButton;
    public ImageView paletteButton;
    public ImageView rinButton;
    public ImageView kyusiButton;
    public ImageView mapaButton;
    public ImageView nangangambaButton;
    public ImageView pasilyoButton;
    public ImageView playButton;
    ObservableList<Song> defaultAllSongList;
    ObservableList<Song> sortAllSongNameSongList;
    ObservableList<Song> defaultLikedSongList;
    ObservableList<Song> sortLikedSongNameSongList;
    private String userDataDirectory = System.getProperty("user.dir") + "\\src\\main\\resources\\UserData\\";
    @FXML
    private Label usernameLabel;
    @FXML
    public Label songName;
    @FXML
    public Label songArtist;
    @FXML
    private Label currentTimeLabel, durationLabel;
    @FXML
    private Slider slider, volumeSlider;
    @FXML
    private MediaPlayer mediaPlayer;
    @FXML
    private Pane header;
    @FXML
    private ScrollPane stargazeView;
    @FXML
    private VBox searchView;
    @FXML
    private HBox homebox;
    @FXML
    private HBox stargazebox;
    @FXML
    private HBox searchbox;
    @FXML
    private VBox resultBox;
    private double xOffset = 0;
    private double yOffset = 0;
    @FXML
    private Button stargazebutton;
    @FXML
    private ImageView star;
    @FXML
    private Button homebutton;
    @FXML
    private ImageView home;
    @FXML
    private Button searchButton;
    @FXML
    private ImageView search;
    @FXML
    private TextField songSearchField;
    public User userAccessed;
    public ArrayList<File> songs;
    private File[] files;
    public int songNumber;
    private File directory;
    private Media media;
    private Timer timer;
    private TimerTask task;
    private Mp3File mp3File;
    private String readyMusicDirectory = System.getProperty("user.dir") + "\\src\\main\\resources\\com\\example\\wish122\\songs";
    private boolean running;
    private boolean playing = false;
    public boolean shuffling = false;
    public boolean looping = false;
    public boolean allSort = false;
    public boolean likedSort = false;
    public Stack<Integer> shuffleSongs;
    public Stack<Integer> prevShuffleSongs;
    public List<Integer> indices;
    public LinkedList<User> userList;
    public OurCircularQueue<Integer> looper;

    public void initialize() {
        volumeSlider.setVisible(true);
        volumeSlider.setRotate(90);
        songName.setText(" ");
        songArtist.setText(" ");
        header.setVisible(false);
        stargazeView.setVisible(false);
        searchView.setVisible(false);
        likedSongTitle.setCellValueFactory(new PropertyValueFactory<Song, String>("Name"));
        likedSongArtist.setCellValueFactory(new PropertyValueFactory<Song, String>("Artists"));
        likedSongAlbum.setCellValueFactory(new PropertyValueFactory<Song, String>("Album"));
        likedSongGenre.setCellValueFactory(new PropertyValueFactory<Song, String>("Genre"));
        allSongTitle.setCellValueFactory(new PropertyValueFactory<Song, String>("Name"));
        allSongArtist.setCellValueFactory(new PropertyValueFactory<Song, String>("Artists"));
        allSongAlbum.setCellValueFactory(new PropertyValueFactory<Song, String>("Album"));
        allSongGenre.setCellValueFactory(new PropertyValueFactory<Song, String>("Genre"));
        stargazebutton.setTextFill(Paint.valueOf("#a1a1a1"));
        searchButton.setTextFill(Paint.valueOf("#a1a1a1"));

        setImageEventHandler();
    }

    public void setUserList(LinkedList<User> userList) {
        this.userList = userList;
    }

    private void setImageEventHandler() {
        ghostButton.setOnMouseClicked(event -> {
            songNumber = 1;
            if(shuffling) {
                onShuffleStargaze();
            } else if (looping) {
                onLoopStargaze();
            }
            mediaPlayer.stop();

            if(running) {
                cancelTimer();
            }
            mediaPlayer.stop();
            initializeMediaPlayer();
            System.out.println("Current song changed to \"" + songs.get(songNumber).getName() + "\"");
            playing = false;
            onPlayPause();
        });

        mephistoButton.setOnMouseClicked(event -> {
            songNumber = 3;
            if(shuffling) {
                onShuffleStargaze();
            } else if (looping) {
                onLoopStargaze();
            }
            mediaPlayer.stop();

            if(running) {
                cancelTimer();
            }
            mediaPlayer.stop();
            initializeMediaPlayer();
            System.out.println("Current song changed to \"" + songs.get(songNumber).getName() + "\"");
            playing = false;
            onPlayPause();
        });

        overdoseButton.setOnMouseClicked(event -> {
            songNumber = 4;
            if(shuffling) {
                onShuffleStargaze();
            } else if (looping) {
                onLoopStargaze();
            }
            mediaPlayer.stop();

            if(running) {
                cancelTimer();
            }
            mediaPlayer.stop();
            initializeMediaPlayer();
            System.out.println("Current song changed to \"" + songs.get(songNumber).getName() + "\"");
            playing = false;
            onPlayPause();
        });

        yorunopierrotButton.setOnMouseClicked(event -> {
            songNumber = 0;
            if(shuffling) {
                onShuffleStargaze();
            } else if (looping) {
                onLoopStargaze();
            }
            mediaPlayer.stop();

            if(running) {
                cancelTimer();
            }
            mediaPlayer.stop();
            initializeMediaPlayer();
            System.out.println("Current song changed to \"" + songs.get(songNumber).getName() + "\"");
            playing = false;
            onPlayPause();
        });

        paletteButton.setOnMouseClicked(event -> {
            songNumber = 5;
            if(shuffling) {
                onShuffleStargaze();
            } else if (looping) {
                onLoopStargaze();
            }
            mediaPlayer.stop();

            if(running) {
                cancelTimer();
            }
            mediaPlayer.stop();
            initializeMediaPlayer();
            System.out.println("Current song changed to \"" + songs.get(songNumber).getName() + "\"");
            playing = false;
            onPlayPause();
        });

        rinButton.setOnMouseClicked(event -> {
            songNumber = 2;
            if(shuffling) {
                onShuffleStargaze();
            } else if (looping) {
                onLoopStargaze();
            }
            mediaPlayer.stop();

            if(running) {
                cancelTimer();
            }
            mediaPlayer.stop();
            initializeMediaPlayer();
            System.out.println("Current song changed to \"" + songs.get(songNumber).getName() + "\"");
            playing = false;
            onPlayPause();
        });

        kyusiButton.setOnMouseClicked(event -> {
            songNumber = 9;
            if(shuffling) {
                onShuffleStargaze();
            } else if (looping) {
                onLoopStargaze();
            }
            mediaPlayer.stop();

            if(running) {
                cancelTimer();
            }
            mediaPlayer.stop();
            initializeMediaPlayer();
            System.out.println("Current song changed to \"" + songs.get(songNumber).getName() + "\"");
            playing = false;
            onPlayPause();
        });

        mapaButton.setOnMouseClicked(event -> {
            songNumber = 6;
            if(shuffling) {
                onShuffleStargaze();
            } else if (looping) {
                onLoopStargaze();
            }
            mediaPlayer.stop();

            if(running) {
                cancelTimer();
            }
            mediaPlayer.stop();
            initializeMediaPlayer();
            System.out.println("Current song changed to \"" + songs.get(songNumber).getName() + "\"");
            playing = false;
            onPlayPause();
        });

        nangangambaButton.setOnMouseClicked(event -> {
            songNumber = 8;
            if(shuffling) {
                onShuffleStargaze();
            } else if (looping) {
                onLoopStargaze();
            }
            mediaPlayer.stop();

            if(running) {
                cancelTimer();
            }
            mediaPlayer.stop();
            initializeMediaPlayer();
            System.out.println("Current song changed to \"" + songs.get(songNumber).getName() + "\"");
            playing = false;
            onPlayPause();
        });

        pasilyoButton.setOnMouseClicked(event -> {
            songNumber = 7;
            if(shuffling) {
                onShuffleStargaze();
            } else if (looping) {
                onLoopStargaze();
            }
            mediaPlayer.stop();

            if(running) {
                cancelTimer();
            }
            mediaPlayer.stop();
            initializeMediaPlayer();
            System.out.println("Current song changed to \"" + songs.get(songNumber).getName() + "\"");
            playing = false;
            onPlayPause();
        });

    }

    private final ChangeListener<Number> sliderChangeListener = new ChangeListener<Number>() {
        @Override
        public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
            if (slider.isValueChanging() && newValue.doubleValue() > 0) {
                double seekTime = mediaPlayer.getTotalDuration().toSeconds() * (newValue.doubleValue() / mediaPlayer.getTotalDuration().toSeconds());
                mediaPlayer.seek(Duration.seconds(seekTime));

                currentTimeLabel.setText(formatTime(seekTime));

                Platform.runLater(() -> {
                    slider.setValue(newValue.doubleValue());
                });
            }
        }
    };

    public void onVolumePress() {
        volumeSlider.setVisible(!volumeSlider.isVisible());
    }

    public void onPlayPause() {
        if (!playing) {
            playButton.setOpacity(1);
            beginTimer();
            
            mediaPlayer.setVolume(volumeSlider.getValue() / 100);

            double duration = mediaPlayer.getTotalDuration().toSeconds();
            slider.setMax(duration);
            durationLabel.setText(formatTime(duration));

            slider.valueProperty().removeListener(sliderChangeListener);

            slider.valueProperty().addListener(sliderChangeListener);

            mediaPlayer.currentTimeProperty().addListener((observable, oldValue, newValue) -> {
                if (!slider.isValueChanging()) {
                    slider.setValue(newValue.toSeconds());
                    currentTimeLabel.setText(formatTime(newValue.toSeconds()));
                }
            });
            mediaPlayer.setOnEndOfMedia(() -> onNext());
            System.out.println("Audio File has started playing.");
            playing = true;

            mediaPlayer.play();
        } else if (running) {
            cancelTimer();
            playButton.setOpacity(0.4);
            playing = false;
            System.out.println("Audio File has stopped playing.");
            mediaPlayer.pause();
        }
    }

    public void playSong(ActionEvent event) {

        if(running) {
            cancelTimer();
        }
        mediaPlayer.stop();
        initializeMediaPlayer();
        System.out.println("Current song changed to \"" + songs.get(songNumber).getName() + "\"");
        playing = false;
        onPlayPause();
    }

    public void onPrev() {
        if(!shuffling && !looping) {
            if (songNumber > 0) {
                songNumber--;
                mediaPlayer.stop();

                if(running) {
                    cancelTimer();
                }
                mediaPlayer.stop();
                initializeMediaPlayer();
                System.out.println("Current song changed to \"" + songs.get(songNumber).getName() + "\"");
                playing = false;
                onPlayPause();
            }
            else {
                songNumber = songs.size() - 1;
                mediaPlayer.stop();

                if(running) {
                    cancelTimer();
                }
                mediaPlayer.stop();
                initializeMediaPlayer();
                System.out.println("Current song changed to \"" + songs.get(songNumber).getName() + "\"");
                playing = false;
                onPlayPause();
            }
        } else if(shuffling) {
            if(!prevShuffleSongs.isEmpty()) {
                int prevTemp = prevShuffleSongs.pop();
                songNumber = prevTemp;
                shuffleSongs.push(prevTemp);
                mediaPlayer.stop();

                if(running) {
                    cancelTimer();
                }
                mediaPlayer.stop();
                initializeMediaPlayer();
                System.out.println("Current song changed to \"" + songs.get(songNumber).getName() + "\"");
                playing = false;
                onPlayPause();
            }
        } else if (looping) {
            int prevTemp = 0;
            for(int i=0; i<looper.size(); i++) {
                looper.enqueue(songNumber);
                prevTemp = (int) looper.dequeue();
                songNumber = prevTemp;
            }

            mediaPlayer.stop();

            if(running) {
                cancelTimer();
            }
            mediaPlayer.stop();
            initializeMediaPlayer();
            System.out.println("Current song changed to \"" + songs.get(songNumber).getName() + "\"");
            playing = false;
            onPlayPause();
        }
    }

    public void onNext() {
        System.out.println(songs.size());
        System.out.println(songNumber);
        if(!shuffling && !looping) {
            if (songNumber < songs.size() - 1) {
                songNumber++;
                mediaPlayer.stop();

                if(running) {
                    cancelTimer();
                }
                mediaPlayer.stop();
                initializeMediaPlayer();
                System.out.println("Current song changed to \"" + songs.get(songNumber).getName() + "\"");
                playing = false;
                onPlayPause();
            }
            else {
                mediaPlayer.stop();
                initializeMediaPlayer();
                System.out.println("ALERT: All songs have been played in this playlist, returned to the start of playlist.");
                System.out.println("Current song changed to \"" + songs.get(songNumber).getName() + "\"");
            }
        } else if(shuffling) {
            if(!shuffleSongs.isEmpty()) {
                int prevTemp = shuffleSongs.pop();
                songNumber = prevTemp;
                prevShuffleSongs.push(prevTemp);
                mediaPlayer.stop();

                if(running) {
                    cancelTimer();
                }
                mediaPlayer.stop();
                initializeMediaPlayer();
                System.out.println("Current song changed to \"" + songs.get(songNumber).getName() + "\"");
                playing = false;
                onPlayPause();
            }
        } else if(looping) {
            int prevTemp = (int) looper.dequeue();
            looper.enqueue(songNumber);
            songNumber = prevTemp;
            mediaPlayer.stop();

            if(running) {
                cancelTimer();
            }
            mediaPlayer.stop();
            initializeMediaPlayer();
            System.out.println("Current song changed to \"" + songs.get(songNumber).getName() + "\"");
            playing = false;
            onPlayPause();
        }
    }

    private void initializeMediaPlayer() {
        media = new Media(songs.get(songNumber).toURI().toString());
        System.out.println(songs.get(songNumber).toURI().toString());
        mediaPlayer = new MediaPlayer(media);

        String name = songs.get(songNumber).getName();

        for(int i=0; i<userAccessed.getSongList().size(); i++) {
            if(name.equalsIgnoreCase(userAccessed.getSongList().get(i).getFilename())) {
                songName.setText(userAccessed.getSongList().get(i).getName());
                songArtist.setText(userAccessed.getSongList().get(i).getArtists());
                if(userAccessed.getSongList().get(i).isLiked()) {
                    likesongimg.setImage(new Image(System.getProperty("user.dir").toString() + "\\src\\main\\resources\\com\\example\\wish122\\img\\ic_likeheart.png"));
                } else {
                    likesongimg.setImage(new Image(System.getProperty("user.dir").toString() + "\\src\\main\\resources\\com\\example\\wish122\\img\\ic_love.png"));
                }
                break;
            }
        }

        mediaPlayer.setOnReady(() -> {
            double duration = mediaPlayer.getTotalDuration().toSeconds();
            slider.setMax(duration);
            durationLabel.setText(formatTime(duration));
        });

        mediaPlayer.currentTimeProperty().addListener((observable, oldValue, newValue) -> {
            if (!slider.isValueChanging()) {
                slider.setValue(newValue.toSeconds());
                currentTimeLabel.setText(formatTime(newValue.toSeconds()));
            }
        });
    }

    @FXML
    public void setUserAccessed(User userAccessed) {
        this.userAccessed = userAccessed;
        usernameLabel.setText(userAccessed.getUserName());

        songName.setText("No Song");
        songArtist.setText("N/A");

        setAllSongTable();
        setSortSongNameAllSongList();

        setLikedSongTable();
        setSortSongNameLikedSongList();

        for(int i=0; i<userAccessed.getSongList().size(); i++) {
            for(int j=0; j<userAccessed.getLikedSongs().size(); j++) {
                if(userAccessed.getSongList().get(i).getName().equals(userAccessed.getLikedSongs().get(j).getName())) {
                    userAccessed.getSongList().get(i).setLiked(true);
                    break;
                }
            }
        }

        directory = new File("src\\main\\resources\\com\\example\\wish122\\songs");
        File userDirectory = new File("src\\main\\resources\\UserData\\" + userAccessed.getUserName() + "\\songs");

        songs = new ArrayList<File>();
        files = directory.listFiles();
        File[] userFiles = userDirectory.listFiles();

        if(files != null) {
            for(File file : files) {
                songs.add(file);
            }
        }

        if(userFiles != null) {
            songs.addAll(Arrays.asList(userFiles));
        }

        System.out.println("Song Size: " + songs.size());

        try {
            mp3File = new Mp3File(readyMusicDirectory + "\\" + songs.get(songNumber).getName());
            String name = songs.get(songNumber).getName();
            for(int i=0; i<userAccessed.getSongList().size(); i++) {
                if(name.equals(userAccessed.getSongList().get(i).getFilename())) {
                    songName.setText(userAccessed.getSongList().get(i).getName());
                    songArtist.setText(userAccessed.getSongList().get(i).getArtists());
                    if(userAccessed.getSongList().get(i).isLiked()) {
                        likesongimg.setImage(new Image(System.getProperty("user.dir").toString() + "\\src\\main\\resources\\com\\example\\wish122\\img\\ic_likeheart.png"));
                    } else {
                        likesongimg.setImage(new Image(System.getProperty("user.dir").toString() + "\\src\\main\\resources\\com\\example\\wish122\\img\\ic_love.png"));
                    }
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println(songs.get(songNumber).toURI());

        media = new Media(songs.get(songNumber).toURI().toString());
        mediaPlayer = new MediaPlayer(media);

        mediaPlayer.setOnReady(() -> {
            double duration = mediaPlayer.getTotalDuration().toSeconds();
            slider.setMax(duration);
            durationLabel.setText(formatTime(duration));
        });

        System.out.println("Current song is \"" + songs.get(songNumber).getName() + "\"");

        volumeSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {
                mediaPlayer.setVolume(volumeSlider.getValue() / 100);
            }
        });

        mediaPlayer.currentTimeProperty().addListener((observable, oldValue, newValue) -> {
            if (!slider.isValueChanging()) {
                slider.setValue(newValue.toSeconds());
                currentTimeLabel.setText(formatTime(newValue.toSeconds()));
            }
        });
    }

    private void setSortSongNameAllSongList() {
        sortAllSongNameSongList = FXCollections.observableArrayList();
        BinaryTreeSearch bst = new BinaryTreeSearch();
        for(int i=0; i<userAccessed.getSongList().size(); i++) {
            bst.insert(userAccessed.getSongList().get(i).getName(), userAccessed.getSongList().get(i));
        }
        bst.inOrderTraversal(sortAllSongNameSongList);
    }
    private void setAllSongTable() {
        defaultAllSongList = FXCollections.observableArrayList();

        for(int i=0; i<userAccessed.getSongList().size(); i++) {
            defaultAllSongList.add((Song) userAccessed.getSongList().get(i));
        }
        allSongTable.setItems(defaultAllSongList);
    }

    private void setSortSongNameLikedSongList() {
        sortLikedSongNameSongList = FXCollections.observableArrayList();
        BinaryTreeSearch bst = new BinaryTreeSearch();
        for(int i=0; i<userAccessed.getLikedSongs().size(); i++) {
            bst.insert(userAccessed.getLikedSongs().get(i).getName(), userAccessed.getLikedSongs().get(i));
        }
        bst.inOrderTraversal(sortLikedSongNameSongList);
    }
    private void setLikedSongTable() {
        defaultLikedSongList = FXCollections.observableArrayList();

        for(int i=0; i<userAccessed.getLikedSongs().size(); i++) {
            defaultLikedSongList.add((Song) userAccessed.getLikedSongs().get(i));
        }
        likedSongTable.setItems(defaultLikedSongList);
    }

    public void defaultSongSetTable(ActionEvent event) {
        likedSort = false;
        allSort = false;
        likedSongTable.refresh();
        setLikedSongTable();
        likedSongTable.setItems(defaultLikedSongList);
        allSongTable.refresh();
        allSongTable.setItems(defaultAllSongList);
    }

    public void sortSongNameSetTable(ActionEvent event) {
        likedSort = true;
        allSort = true;
        likedSongTable.refresh();
        setSortSongNameLikedSongList();
        likedSongTable.setItems(sortLikedSongNameSongList);
        allSongTable.refresh();
        allSongTable.setItems(sortAllSongNameSongList);
    }

    @FXML
    public void exit(ActionEvent event) {
        System.exit(0);
    }

    private String formatTime(double seconds) {
        int minutes = (int) seconds / 60;
        int remainingSeconds = (int) seconds % 60;
        return String.format("%02d:%02d", minutes, remainingSeconds);
    }

    public void beginTimer() {
        timer = new Timer();
        task = new TimerTask() {
            public void run() {
                running = true;
                double current = mediaPlayer.getCurrentTime().toSeconds();
                double end = media.getDuration().toSeconds();

                Platform.runLater(() -> {
                    slider.adjustValue(current / end);
                    if (current / end == 1) {
                        cancelTimer();
                    }
                });
            }
        };

        timer.scheduleAtFixedRate(task, 0, 1000);
    }

    public void cancelTimer() {
        running = false;
        timer.cancel();
    }

    public void onShuffleStargaze() {
        shuffleSongs = new Stack<>();
        prevShuffleSongs = new Stack<>();
        for(int i=0; i<userAccessed.getSongList().size(); i++) {
            indices.add(i);
        }
        Collections.shuffle(indices);
        for(int i=0; i<indices.size(); i++) {
            shuffleSongs.push(indices.get(i));
        }
    }

    public void onLoopStargaze() {
        looper = new OurCircularQueue<>(userAccessed.getSongList().size());
        for(int i=songNumber+1; i<userAccessed.getSongList().size(); i++) {
            looper.enqueue(i);
        }
        for(int i=0; i<songNumber; i++) {
            looper.enqueue(i);
        }
    }

    public void onShuffle() {
        if(!shuffling) {
            shuffling = true;
            looping = false;
            shuffleImg.setImage(new Image(System.getProperty("user.dir").toString() + "\\src\\main\\resources\\com\\example\\wish122\\img\\ic_shuffle_enabled.png"));
            shuffleImg.setOpacity(1);
            loopImg.setImage(new Image(System.getProperty("user.dir").toString() + "\\src\\main\\resources\\com\\example\\wish122\\img\\ic_repeat.png"));
            indices = new ArrayList<>();
            shuffleSongs = new Stack<>();
            prevShuffleSongs = new Stack<>();
            for(int i=0; i<songs.size(); i++) {
                indices.add(i);
            }
            Collections.shuffle(indices);
            for(int i=0; i<indices.size(); i++) {
                shuffleSongs.push(indices.get(i));
            }

            System.out.println("Shuffle Enabled!");
        } else if(shuffling){
            shuffling = false;
            shuffleImg.setImage(new Image(System.getProperty("user.dir").toString() + "\\src\\main\\resources\\com\\example\\wish122\\img\\ic_shuffle.png"));
            System.out.println("Shuffle Disabled");
        }
    }

    public void onLoop() {
        if(!looping) {
            shuffling = false;
            looping = true;
            shuffleImg.setImage(new Image(System.getProperty("user.dir").toString() + "\\src\\main\\resources\\com\\example\\wish122\\img\\ic_shuffle.png"));
            loopImg.setOpacity(1);
            loopImg.setImage(new Image(System.getProperty("user.dir").toString() + "\\src\\main\\resources\\com\\example\\wish122\\img\\ic_repeat_enabled.png"));
            looper = new OurCircularQueue<>(songs.size());
            for(int i=songNumber+1; i<songs.size(); i++) {
                looper.enqueue(i);
            }
            for(int i=0; i<songNumber; i++) {
                looper.enqueue(i);
            }
            System.out.println("Repeat Enabled!");
        } else if(looping){
            looping = false;
            loopImg.setImage(new Image(System.getProperty("user.dir").toString() + "\\src\\main\\resources\\com\\example\\wish122\\img\\ic_repeat.png"));
            System.out.println("Repeat Disabled");
        }
    }

    public void likesongsbutton(ActionEvent event) throws IOException {
        for(int i=0; i<userAccessed.getSongList().size(); i++) {
            if(userAccessed.getSongList().get(i).getName().equals(songName.getText())) {
                if(!userAccessed.getSongList().get(i).isLiked()) {
                    userAccessed.getSongList().get(i).setLiked(true);
                    userAccessed.addLikedSongs(userAccessed.getSongList().get(i));
                    updateSongFile(userAccessed.getSongList());
                    if(likedSort) {
                        likedSongTable.refresh();
                        setSortSongNameLikedSongList();
                        likedSongTable.setItems(sortLikedSongNameSongList);
                    } else {
                        likedSongTable.refresh();
                        setLikedSongTable();
                        likedSongTable.setItems(defaultLikedSongList);
                    }
                    likesongimg.setImage(new Image(System.getProperty("user.dir").toString() + "\\src\\main\\resources\\com\\example\\wish122\\img\\ic_likeheart.png"));
                    System.out.println("Liked!");
                    break;
                } else {
                    userAccessed.getSongList().get(i).setLiked(false);
                    userAccessed.removeLikedSong(songName.getText());
                    updateSongFile(userAccessed.getSongList());
                    if(likedSort) {
                        likedSongTable.refresh();
                        setSortSongNameLikedSongList();
                        likedSongTable.setItems(sortLikedSongNameSongList);
                    } else {
                        likedSongTable.refresh();
                        setLikedSongTable();
                        likedSongTable.setItems(defaultLikedSongList);
                    }
                    likesongimg.setImage(new Image(System.getProperty("user.dir").toString() + "\\src\\main\\resources\\com\\example\\wish122\\img\\ic_love.png"));
                    System.out.println("Unliked!");
                    break;
                }
            }
        }
    }

    public void toStargaze (ActionEvent event) throws IOException {
        header.setVisible(true);
        stargazeView.setVisible(true);
        searchView.setVisible(false);

        if (homebutton.getOpacity() == 1 || homebox.getStyleClass().contains("selected")) {
            homebutton.setTextFill(Paint.valueOf("#a1a1a1"));
            home.setOpacity(0.41);
            homebox.getStyleClass().remove("selected");
        }
        if (searchButton.getOpacity() == 1 || searchbox.getStyleClass().contains("selected")) {
            searchButton.setTextFill(Paint.valueOf("#a1a1a1"));
            search.setOpacity(0.41);
            searchbox.getStyleClass().remove("selected");
        }

        stargazebox.getStyleClass().add("selected");
        stargazebutton.setTextFill(Color.WHITE);
        star.setOpacity(1);
    }

    public void toHome (ActionEvent event) throws IOException {
        header.setVisible(false);
        stargazeView.setVisible(false);
        searchView.setVisible(false);

        if (stargazebutton.getOpacity() == 1 || stargazebox.getStyleClass().contains("selected")) {
            stargazebutton.setTextFill(Paint.valueOf("#a1a1a1"));
            star.setOpacity(0.41);
            stargazebox.getStyleClass().remove("selected");
        }
        if (searchButton.getOpacity() == 1 || searchbox.getStyleClass().contains("selected")) {
            searchButton.setTextFill(Paint.valueOf("#a1a1a1"));
            search.setOpacity(0.41);
            searchbox.getStyleClass().remove("selected");
        }

        homebox.getStyleClass().add("selected");
        homebutton.setTextFill(Color.WHITE);
        home.setOpacity(1);
    }

    @FXML
    public void toSearch(ActionEvent event) throws IOException {
        header.setVisible(false);
        stargazeView.setVisible(false);
        searchView.setVisible(true);

        if (stargazebutton.getOpacity() == 1 || stargazebox.getStyleClass().contains("selected")) {
            stargazebutton.setTextFill(Paint.valueOf("#a1a1a1"));
            star.setOpacity(0.41);
            stargazebox.getStyleClass().remove("selected");
        }
        if (homebutton.getOpacity() == 1 || homebox.getStyleClass().contains("selected")) {
            homebutton.setTextFill(Paint.valueOf("#a1a1a1"));
            home.setOpacity(0.41);
            homebox.getStyleClass().remove("selected");
        }

        searchbox.getStyleClass().add("selected");
        searchButton.setTextFill(Color.WHITE);
        search.setOpacity(1);
    }

    public void toLogout (ActionEvent event) throws IOException{
        mediaPlayer.stop();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("user-login.fxml"));
        Pane root = loader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);

        LoginController loginController = loader.getController();
        loginController.setUserList(userList);

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

    public void openAddSongWindow() throws Exception {
        // Load the second window FXML
        FXMLLoader loader = new FXMLLoader(getClass().getResource("add-song.fxml"));
        Parent root = loader.load();

        // Get the controller for the second window
        AddSongController addSongController = loader.getController();

        // Pass the reference to the main window controller
        addSongController.setMenuController(this);

        // Create scene and set it to the new stage
        Stage secondStage = new Stage();
        secondStage.initModality(Modality.APPLICATION_MODAL);
        secondStage.setTitle("Add Song");
        secondStage.setScene(new Scene(root));
        secondStage.showAndWait();
    }

    public void openRemoveSongWindow() throws Exception {
        // Load the second window FXML
        FXMLLoader loader = new FXMLLoader(getClass().getResource("remove-song.fxml"));
        Parent root = loader.load();

        // Get the controller for the third window
        RemoveSongController removeSongController = loader.getController();

        // Pass the reference to the main window controller
        removeSongController.setMenuController(this);

        // Create scene and set it to the new stage
        Stage thirdStage = new Stage();
        thirdStage.initModality(Modality.APPLICATION_MODAL);
        thirdStage.setTitle("Remove Song");
        thirdStage.setScene(new Scene(root));
        thirdStage.showAndWait();
    }

    public void openEditSongWindow() throws Exception {
        // Load the second window FXML
        FXMLLoader loader = new FXMLLoader(getClass().getResource("edit-song.fxml"));
        Parent root = loader.load();

        // Get the controller for the third window
        EditSongController editSongController = loader.getController();

        // Pass the reference to the main window controller
        editSongController.setMenuController(this);

        // Create scene and set it to the new stage
        Stage fourthStage = new Stage();
        fourthStage.initModality(Modality.APPLICATION_MODAL);
        fourthStage.setTitle("Edit Song");
        fourthStage.setScene(new Scene(root));
        fourthStage.showAndWait();
    }


    private void updateSongFile(LinkedList<Song> songList) {
        String songFile = userDataDirectory + userAccessed.getUserName() + "\\songs.txt";

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

    public void onSongSearch(ActionEvent event) {
        resultBox.getChildren().clear();
        resultBox.setSpacing(5);
        String[] query = songSearchField.getText().split(" ");

        for(int i=0; i<userAccessed.getSongList().size(); i++) {
            for(int j=0; j<query.length; j++) {
                if(userAccessed.getSongList().get(i).getName().toLowerCase().contains(query[j].toLowerCase())) {
                    System.out.println(userAccessed.getSongList().get(i).getName());
                    ResultPane resultPane = new ResultPane(userAccessed.getSongList().get(i).getName());
                    resultBox.getChildren().add(resultPane);
                    break;
                }
            }
        }
    }

    private static class ResultPane extends HBox {
        public ResultPane(String text) {
            setSpacing(5);

            String imageName = text.toLowerCase().replaceAll(" ", "-");
            String imagePath = "/com/example/wish122/images/" + imageName;

            String[] extensions = {".png", ".jpg", ".jpeg", ".gif"};

            ImageView imageView = null;

            for (String extension : extensions) {
                InputStream inputStream = getClass().getResourceAsStream(imagePath + extension);

                if (inputStream != null) {
                    imageView = new ImageView(new Image(inputStream));
                    imageView.setFitWidth(100);
                    imageView.setPreserveRatio(true);
                    break;
                }
            }

            if (imageView == null) {
                imageView = new ImageView(new Image(getClass().getResourceAsStream("/com/example/wish122/images/placeholder.png")));
                imageView.setFitWidth(100); // Adjust the width as needed
                imageView.setPreserveRatio(true);
            }

            Label resultLabel = new Label(text);
            resultLabel.setFont(new Font("System", 20));
            resultLabel.setTextFill(Color.WHITE);
            getChildren().addAll(imageView, resultLabel);
        }
    }
}