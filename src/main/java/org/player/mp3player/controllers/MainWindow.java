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
import javafx.util.Duration;
import lombok.AccessLevel;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.player.mp3player.controllers.listener.ListenerInitializer;
import org.player.mp3player.controllers.title.SongTitleController;
import org.player.mp3player.model.Music;
import org.player.mp3player.model.MusicItem;
import org.player.mp3player.model.exception.NotImplementedYetException;
import org.player.mp3player.repositories.FileDataLoader;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import java.util.ResourceBundle;

@Slf4j
public class MainWindow implements Initializable {

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private Label songTimeLabel;

    @FXML
    private Label songTitleLabel;

    @FXML
    private Button playButton;
    @FXML
    private Button pauseButton;
    @FXML
    private Button stopButton;
    @FXML
    private Button previousButton;
    @FXML
    private Button nextButton;
    @FXML
    private Button openButton;

    @FXML
    private Button repeatButton;
    @FXML
    private Button shuffleButton;
    @FXML
    private Button playlistButton;

    @FXML
    private Slider songProgressSlider;

    private Media media;
    private MediaPlayer mediaPlayer;

    private ArrayList<File> songs;

    @Setter(AccessLevel.PUBLIC)
    private int songNumber;
    private int sizeSongsRandom;
    private Music music;
    private SongTitleController songTitleController;

    private Stage playListStage;

    private FXMLLoader loader;

    private PlayListWindow playListWindowController;
    private Duration pausedTime = Duration.seconds(0);
    private boolean playing;
    private ResourceBundle resourceBundle;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        resourceBundle = resources;
        playing = false;
        initButtons();

        songs = new ArrayList<>();
        File directory = new File("music");

        if (!directory.exists()) {
            directory.mkdir();
        }

        File[] files = directory.listFiles();


        if (files != null) {
            for (File file : files) {
                songs.add(file);
                log.debug(file.toString());
            }
        }

        FileDataLoader fileDataLoader = new FileDataLoader();
        ArrayList<MusicItem> musicItems = fileDataLoader.loadData(directory);

        music = Music.getInstance(musicItems);
        songTitleController = new SongTitleController(songTitleLabel);

    }

    public void playMedia() {

        if (playing) {
            return;
        }

        if (pausedTime.toSeconds() > 0 && mediaPlayer != null) {
            mediaPlayer.seek(pausedTime);
        } else {
            media = new Media(music.getSongPath(songNumber));
            mediaPlayer = new MediaPlayer(media);
        }

        songTitleController.setCurrentSongTitle(music.getSongTitle(songNumber));
        mediaPlayer.play();
        playing = true;
        mediaPlayer.setOnReady(new ListenerInitializer(mediaPlayer, songProgressSlider, songTimeLabel));

        if (playListWindowController != null) {
            playListWindowController.highlightPlayed(songNumber);
        }

        pausedTime = Duration.seconds(0);

    }

    public void pauseMedia() {
        mediaPlayer.pause();
        pausedTime = mediaPlayer.getCurrentTime();
        playing = false;
    }

    public void stopMedia() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            playing = false;
        }
    }

    public void previousMedia() {
        if (songNumber > 0) {
            songNumber--;
        } else {
            songNumber = music.getPlaylist().size() - 1;
        }
        stopMedia();
        media = new Media(music.getSongPath(songNumber));
        mediaPlayer = new MediaPlayer(media);
        songTitleController.setCurrentSongTitle(music.getSongTitle(songNumber));
        playMedia();
    }

    public void nextMedia() {

        if (songNumber < songs.size() - 1) {
            songNumber++;
        } else {
            songNumber = 0;
        }
        stopMedia();
        media = new Media(music.getSongPath(songNumber));
        mediaPlayer = new MediaPlayer(media);
        songTitleController.setCurrentSongTitle(music.getSongTitle(songNumber));
        playMedia();
    }

    public void openMedia() {
        throw new NotImplementedYetException();
    }

    public void repeatPlayList(ActionEvent event) {
        throw new NotImplementedYetException();
    }

    public void shufflePlayList(ActionEvent event) {
        stopMedia();
        Random rand = new Random();
        sizeSongsRandom = rand.nextInt(songs.size());
        media = new Media(music.getSongPath(sizeSongsRandom));
        mediaPlayer = new MediaPlayer(media);
        songTitleController.setCurrentSongTitle(music.getSongTitle(sizeSongsRandom));
        mediaPlayer.play();
        mediaPlayer.setOnReady(new ListenerInitializer(mediaPlayer, songProgressSlider, songTimeLabel));
    }

    public void openPlayList(ActionEvent event) throws Exception {

        loader = new FXMLLoader(getClass().getResource("../fxml/PlayListWindow.fxml"));
        loader.setResources(resourceBundle);
        Parent playListWindow = loader.load();

        Scene playListScene = new Scene(playListWindow);

        playListWindowController = loader.getController();

        playListWindowController.highlightPlayed(songNumber);
        playListWindowController.setMainWindowController(this);

        playListScene.getStylesheets().add(getClass().getResource("../css/PlayListWindow.css").toExternalForm());

        if (playListStage == null) {
            playListStage = new Stage();
        }

        Stage thisStage = (Stage) anchorPane.getScene().getWindow();
        playListStage.setScene(playListScene);
        playListStage.setX(thisStage.getX() + 300);
        playListStage.setY(thisStage.getY());
        playListStage.show();
    }

    private void initButtons() {

        //Set Prev Button Icon
        setButtonGraphicsAndTooltip(previousButton, "../icons/previous.png", resourceBundle.getString("tooltip.previous"));
        //Set Play Button Icon
        setButtonGraphicsAndTooltip(playButton, "../icons/play.png", resourceBundle.getString("tooltip.play"));
        //Set Pause Button Icon
        setButtonGraphicsAndTooltip(pauseButton, "../icons/pause.png", resourceBundle.getString("tooltip.pause"));
        //Set Stop Button Icon
        setButtonGraphicsAndTooltip(stopButton, "../icons/stop.png", resourceBundle.getString("tooltip.stop"));
        //Set Next Button Icon
        setButtonGraphicsAndTooltip(nextButton, "../icons/next.png", resourceBundle.getString("tooltip.next"));
        //Set Open Button Icon
        setButtonGraphicsAndTooltip(openButton, "../icons/eject.png", resourceBundle.getString("tooltip.open"));

    }

    private void setButtonGraphicsAndTooltip(Button button, String iconPath, String toolTip) {
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
