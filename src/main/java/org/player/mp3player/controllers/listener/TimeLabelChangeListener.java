package org.player.mp3player.controllers.listener;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Label;
import javafx.util.Duration;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class TimeLabelChangeListener implements ChangeListener<Duration> {
  private final Label songTimeLabel;
  private final String songDurationTime;
  private final DateTimeFormatter timeFormatter;

  public TimeLabelChangeListener(Label songTimeLabel, Duration totalSongDuration) {
    this.songTimeLabel = songTimeLabel;
    this.timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
    this.songDurationTime = mapToStringTime(totalSongDuration);
  }

  private String mapToStringTime(Duration totalSongDuration) {
    LocalTime songDurationLocalTime = LocalTime.ofSecondOfDay((long) totalSongDuration.toSeconds());
    return songDurationLocalTime.format(timeFormatter);
  }

  @Override
  public void changed(ObservableValue<? extends Duration> observable, Duration oldValue, Duration newValue) {
    String currentSongTime = mapToStringTime(newValue);
    String songLabelText = buildTimeLabelText(currentSongTime, songDurationTime);
    songTimeLabel.setText(songLabelText);
  }

  private String buildTimeLabelText(String currentSongTime, String songDurationTime) {
    return currentSongTime + System.lineSeparator() + "/ " + songDurationTime;
  }
}
