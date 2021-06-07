package org.player.mp3player.controllers.listener;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ProgressSliderListener implements ChangeListener<Duration>, EventHandler<MouseEvent> {
  private final MediaPlayer mediaPlayer;
  private final Slider songProgressSlider;

  @Override
  public void changed(ObservableValue<? extends Duration> observable, Duration oldValue, Duration newValue) {
    songProgressSlider.setValue(newValue.toSeconds());
  }

  @Override
  public void handle(MouseEvent event) {
    mediaPlayer.seek(Duration.seconds(songProgressSlider.getValue()));
  }
}
