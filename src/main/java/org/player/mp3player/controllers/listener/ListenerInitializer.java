package org.player.mp3player.controllers.listener;

import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ListenerInitializer implements Runnable {
  private final MediaPlayer mediaPlayer;
  private final Slider songProgressSlider;
  private final Label songTimeLabel;


  @Override
  public void run() {
    Duration total = mediaPlayer.getMedia().getDuration();
    songProgressSlider.setMax(total.toSeconds());

    ProgressSliderListener progressSliderListener = new ProgressSliderListener(mediaPlayer, songProgressSlider);
    mediaPlayer.currentTimeProperty().addListener(progressSliderListener);
    songProgressSlider.setOnMousePressed(progressSliderListener);
    songProgressSlider.setOnMouseDragged(progressSliderListener);

    TimeLabelChangeListener timeLabelChangeListener = new TimeLabelChangeListener(songTimeLabel, total);
    mediaPlayer.currentTimeProperty().addListener(timeLabelChangeListener);
  }
}
