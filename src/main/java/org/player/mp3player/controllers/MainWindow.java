package org.player.mp3player.controllers;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
import javafx.stage.Stage;
import org.player.mp3player.controllers.title.SongTitleController;
import org.player.mp3player.model.Music;
import org.player.mp3player.model.MusicItem;
import org.player.mp3player.repositories.FileDataLoader;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

public class MainWindow implements Initializable {



    @FXML
    private AnchorPane anchorPane;

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
    private Music music;
    private SongTitleController songTitleController;

    Stage playListStage;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        initButtons();

        songs = new ArrayList<File>();
        directory = new File("music");

        if(!directory.exists()){
            directory.mkdir();
        }

        files = directory.listFiles();


        if (files != null){
            for (File file : files){
                songs.add(file);
                System.out.println(file);
            }
        }

        FileDataLoader fileDataLoader = new FileDataLoader();
        ArrayList<MusicItem> musicItems = fileDataLoader.loadData(directory);

        music = Music.getInstance(musicItems);
        songTitleController =new SongTitleController(songTitleLabel);
    }

    public void playMedia(){
      
        media = new Media(music.getSongPath(songNumber));
        mediaPlayer = new MediaPlayer(media);
        songTitleController.setCurrentSongTitle(music.getSongTitle(songNumber));
        mediaPlayer.play();
//        Double timeSec = mediaPlayer.getTotalDuration().toSeconds();
//        Double timeMin = mediaPlayer.getTotalDuration().toMinutes();
//        Long timeMinLong = Math.round(timeMin);
//        Long timeSecLong = Math.round(timeSec) - 60 * timeMinLong;
//        String time = timeMinLong.toString() + ":" + timeSecLong.toString();
//
//        System.out.println(time);

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

        if(songNumber < songs.size() - 1) {
            songNumber++;
            mediaPlayer.stop();

            media = new Media(music.playList.get(songNumber).getPath());
            mediaPlayer = new MediaPlayer(media);

            songTitleController.setCurrentSongTitle(music.getSongTitle(songNumber));
            playMedia();
        } else {

            songNumber = 0;
            mediaPlayer.stop();

            media = new Media(music.playList.get(songNumber).getPath());
            mediaPlayer = new MediaPlayer(media);

            songTitleController.setCurrentSongTitle(music.getSongTitle(songNumber));
            playMedia();
        }


    }

    public void openMedia(){


    }


    public void repeatPlayList(ActionEvent event) {
    }

    public void shufflePlayList(ActionEvent event) {
    }

    public void openPlayList(ActionEvent event) throws Exception  {
        Parent playListWindow = FXMLLoader.load(getClass().getResource("../fxml/PlayListWindow.fxml"));

        Scene playListScene = new Scene(playListWindow);

        playListScene.getStylesheets().add(getClass().getResource("../css/PlayListWindow.css").toExternalForm());

        if(playListStage == null){
            playListStage = new Stage();
        }

        Stage thisStage = (Stage) anchorPane.getScene().getWindow();
        playListStage.setScene(playListScene);
        playListStage.setX(thisStage.getX() + 300);
        playListStage.setY(thisStage.getY());
        playListStage.show();
    }

    private void initButtons(){

        //Set Prev Button Icon
        setButtonGraphicsAndTooltip(previousButton, "../icons/previous.png", "Previous", imagePrev, imageViewPrev);
        //Set Play Button Icon
        setButtonGraphicsAndTooltip(playButton, "../icons/play.png", "Play", imagePlay, imageViewPlay);
        //Set Pause Button Icon
        setButtonGraphicsAndTooltip(pauseButton, "../icons/pause.png", "Pause", imagePause, imageViewPause);
        //Set Stop Button Icon
        setButtonGraphicsAndTooltip(stopButton, "../icons/stop.png", "Stop", imageStop, imageViewStop);
        //Set Next Button Icon
        setButtonGraphicsAndTooltip(nextButton, "../icons/next.png", "Next", imageNext, imageViewNext);
        //Set Open Button Icon
        setButtonGraphicsAndTooltip(openButton, "../icons/eject.png", "Open", imageOpen, imageViewOpen);

    }

    private void setButtonGraphicsAndTooltip(Button button, String iconPath, String toolTip, Image image, ImageView imageView){
        //Set Button graphic and tooltip
        image = new Image(this.getClass().getResourceAsStream(iconPath));
        imageView = new ImageView(image);
        imageView.setFitHeight(20);
        imageView.setPreserveRatio(true);
        button.setGraphic(imageView);
        button.setText(null);
        Tooltip openTooltip = new Tooltip(toolTip);
        openTooltip.setFont(Font.font(9));
        button.setTooltip(openTooltip);
    }
}
