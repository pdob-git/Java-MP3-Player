package org.player.mp3player.controllers;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Font;

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

    private Image imagePrev;
    private ImageView imageViewPrev;
    private Image imagePlay;
    private ImageView imageViewPlay;
    private Image imageNext;
    private ImageView imageViewNext;
    private Image imagePause;
    private ImageView imageViewPause;
    private Image imageStop;
    private ImageView imageViewStop;
    private Image imageOpen;
    private ImageView imageViewOpen;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        initButtons();

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

    private void initButtons(){
        //Set Prev Button Icon
        imagePrev = new Image(this.getClass().getResourceAsStream("../icons/previous.png"));
        imageViewPrev = new ImageView(imagePrev);
        imageViewPrev.setFitHeight(20);
        imageViewPrev.setPreserveRatio(true);
        previousButton.setGraphic(imageViewPrev);
        previousButton.setText(null);
        Tooltip previousTooltip = new Tooltip("Previous");
        previousTooltip.setFont(Font.font(9));
        previousButton.setTooltip(previousTooltip);

        //Set Play Button Icon
        imagePlay = new Image(this.getClass().getResourceAsStream("../icons/play.png"));
        imageViewPlay = new ImageView(imagePlay);
        imageViewPlay.setFitHeight(20);
        imageViewPlay.setPreserveRatio(true);
        playButton.setGraphic(imageViewPlay);
        playButton.setText(null);
        Tooltip playTooltip = new Tooltip("Play");
        playTooltip.setFont(Font.font(9));
        playButton.setTooltip(playTooltip);

        //Set Pause Button Icon
        imagePause = new Image(this.getClass().getResourceAsStream("../icons/pause.png"));
        imageViewPause = new ImageView(imagePause);
        imageViewPause.setFitHeight(20);
        imageViewPause.setPreserveRatio(true);
        pauseButton.setGraphic(imageViewPause);
        pauseButton.setText(null);
        Tooltip pauseTooltip = new Tooltip("Pause");
        pauseTooltip.setFont(Font.font(9));
        pauseButton.setTooltip(pauseTooltip);


        //Set Stop Button Icon
        imageStop = new Image(this.getClass().getResourceAsStream("../icons/stop.png"));
        imageViewStop = new ImageView(imageStop);
        imageViewStop.setFitHeight(20);
        imageViewStop.setPreserveRatio(true);
        stopButton.setGraphic(imageViewStop);
        stopButton.setText(null);
        Tooltip stopTooltip = new Tooltip("Stop");
        stopTooltip.setFont(Font.font(9));
        stopButton.setTooltip(stopTooltip);

        //Set Next Button Icon
        imageNext = new Image(this.getClass().getResourceAsStream("../icons/next.png"));
        imageViewNext = new ImageView(imageNext);
        imageViewNext.setFitHeight(20);
        imageViewNext.setPreserveRatio(true);
        nextButton.setGraphic(imageViewNext);
        nextButton.setText(null);
        Tooltip nextTooltip = new Tooltip("Next");
        nextTooltip.setFont(Font.font(9));
        nextButton.setTooltip(nextTooltip);

        //Set Open Button Icon
        imageOpen = new Image(this.getClass().getResourceAsStream("../icons/eject.png"));
        imageViewOpen = new ImageView(imageOpen);
        imageViewOpen.setFitHeight(20);
        imageViewOpen.setPreserveRatio(true);
        openButton.setGraphic(imageViewOpen);
        openButton.setText(null);
        Tooltip openTooltip = new Tooltip("Open");
        openTooltip.setFont(Font.font(9));
        openButton.setTooltip(openTooltip);

    }
}
