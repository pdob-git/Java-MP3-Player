package org.player.mp3player.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import org.player.mp3player.controllers.listener.ListenerInitializer;
import org.player.mp3player.controllers.title.SongTitleController;
import org.player.mp3player.model.Music;
import org.player.mp3player.model.MusicItem;
import org.player.mp3player.model.exception.NotImplementedYetException;
import org.player.mp3player.repositories.FileDataLoader;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

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
    private Slider songProgressSlider;

    private Media media;
    private MediaPlayer mediaPlayer;

    private ArrayList<File> songs;

    private int songNumber;
    private Music music;
    private SongTitleController songTitleController;

    Stage playListStage;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        initButtons();

        songs = new ArrayList<>();
        File directory = new File("music");

        if(!directory.exists()){
            directory.mkdir();
        }

        File[] files = directory.listFiles();


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
        mediaPlayer.setOnReady(new ListenerInitializer(mediaPlayer, songProgressSlider, songTimeLabel));

    }

    public void pauseMedia(){
        mediaPlayer.pause();
    }
    public void stopMedia(){
        mediaPlayer.stop();
    }
    public void previousMedia(){
        throw new NotImplementedYetException();
    }

    public void nextMedia(){

        if(songNumber < songs.size() - 1) {
            songNumber++;
            mediaPlayer.stop();

            media = new Media(music.getSongPath(songNumber));
            mediaPlayer = new MediaPlayer(media);

            songTitleController.setCurrentSongTitle(music.getSongTitle(songNumber));
            playMedia();
        } else {

            songNumber = 0;
            mediaPlayer.stop();

            media = new Media(music.getSongPath(songNumber));
            mediaPlayer = new MediaPlayer(media);

            songTitleController.setCurrentSongTitle(music.getSongTitle(songNumber));
            playMedia();
        }


    }

    public void openMedia(){
        throw new NotImplementedYetException();
    }

    public void repeatPlayList(ActionEvent event) {
        throw new NotImplementedYetException();
    }

    public void shufflePlayList(ActionEvent event) {
        throw new NotImplementedYetException();
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
        setButtonGraphicsAndTooltip(previousButton, "../icons/previous.png", "Previous");
        //Set Play Button Icon
        setButtonGraphicsAndTooltip(playButton, "../icons/play.png", "Play");
        //Set Pause Button Icon
        setButtonGraphicsAndTooltip(pauseButton, "../icons/pause.png", "Pause");
        //Set Stop Button Icon
        setButtonGraphicsAndTooltip(stopButton, "../icons/stop.png", "Stop");
        //Set Next Button Icon
        setButtonGraphicsAndTooltip(nextButton, "../icons/next.png", "Next");
        //Set Open Button Icon
        setButtonGraphicsAndTooltip(openButton, "../icons/eject.png", "Open");

    }

    private void setButtonGraphicsAndTooltip(Button button, String iconPath, String toolTip){
        //Set Button graphic and tooltip
        Image image = new Image(this.getClass().getResourceAsStream(iconPath));
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(20);
        imageView.setPreserveRatio(true);
        button.setGraphic(imageView);
        button.setText(null);
        Tooltip openTooltip = new Tooltip(toolTip);
        openTooltip.setFont(Font.font(9));
        button.setTooltip(openTooltip);
    }

}
