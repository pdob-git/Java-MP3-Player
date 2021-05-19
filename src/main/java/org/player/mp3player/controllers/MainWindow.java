package org.player.mp3player.controllers;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

public class MainWindow implements Initializable {



    @FXML
    private AnchorPane pane;

    @FXML
    private Label songTimeLabel;

    @FXML
    private Label songTitleLabel;

    @FXML
    private Button playButton, pauseButton, stopButton, previousButton, nextButton, openButton;

    @FXML
    private Button repeatButton, shuffleButton, playlistButton;

    @FXML
    private ProgressBar songProgressBar;

    private Media media;
    private MediaPlayer mediaPlayer;

    private File directory;
    private File[] files;

    private ArrayList<File> songs;

    private int songNumber;

    private Timer timer;
    private TimerTask task;
    private boolean running;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        songs = new ArrayList<File>();
        directory = new File("music");

        files = directory.listFiles();
        if(!directory.exists()){
            directory.mkdir();
        }


        if (files != null){
            for (File file : files){
                songs.add(file);
                System.out.println(file);
            }
        }

        media = new Media(songs.get(songNumber).toURI().toString());
        mediaPlayer = new MediaPlayer(media);

        songTitleLabel.setText(songs.get(songNumber).getName());


    }

    public void playMedia(){

        mediaPlayer.play();

    }

    public void pauseMedia(){

        mediaPlayer.pause();

    }
    public void stopMedia(){
        mediaPlayer.stop();

    }
    public void previousMedia(){


    }

    public void nextMedia(){


    }

    public void openMedia(){


    }


    public void repeatPlayList(ActionEvent event) {
    }

    public void shufflePlayList(ActionEvent event) {
    }

    public void openPlayList(ActionEvent event) {
    }
}
