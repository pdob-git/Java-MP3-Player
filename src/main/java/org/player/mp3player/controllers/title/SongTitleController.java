package org.player.mp3player.controllers.title;

import javafx.scene.control.Label;
import lombok.extern.slf4j.Slf4j;
import org.player.mp3player.model.util.StringRotateShifter;

import java.util.Objects;
import java.util.Timer;

@Slf4j
public class SongTitleController implements AutoCloseable {
    private static final int TITLE_LABEL_SIZE = 30;
    private static final int TIMER_PERIOD = 200;
    private static final String ONE_ROTATE_SEPARATOR = "***   ";
    private final Label songTitleLabel;
    private final Timer titleChangeTimer;
    private SongTitleTimerTask updateSongTitleTimerTask;


    public SongTitleController(Label songTitleLabel) {
        this.songTitleLabel = songTitleLabel;
        this.titleChangeTimer = new Timer();
    }

    public void setCurrentSongTitle(String currentSongTitle) {
        log.debug("Current song title : {}", currentSongTitle);
        if (Objects.nonNull(updateSongTitleTimerTask)) {
            updateSongTitleTimerTask.cancel();
        }

        if (isToLongSongTitle(currentSongTitle)) {
            setSongTitle(currentSongTitle.substring(0, TITLE_LABEL_SIZE));
            runSongTitleUpdateTask(currentSongTitle);
        } else {
            setSongTitle(currentSongTitle);
        }
    }

    private boolean isToLongSongTitle(String currentSongTitle) {
        return currentSongTitle.length() > TITLE_LABEL_SIZE;
    }

    private void setSongTitle(String preparedSongTitle) {
        songTitleLabel.setText(preparedSongTitle);
    }

    private void runSongTitleUpdateTask(String currentSongTitle) {
        StringRotateShifter currentSongTitleRouteShifter = new StringRotateShifter(currentSongTitle + ONE_ROTATE_SEPARATOR);
        updateSongTitleTimerTask = new SongTitleTimerTask(songTitleLabel, currentSongTitleRouteShifter, TITLE_LABEL_SIZE);
        titleChangeTimer.scheduleAtFixedRate(updateSongTitleTimerTask, TIMER_PERIOD, TIMER_PERIOD);
    }

    @Override
    public void close() {
        updateSongTitleTimerTask.cancel();
        titleChangeTimer.cancel();
    }
}
