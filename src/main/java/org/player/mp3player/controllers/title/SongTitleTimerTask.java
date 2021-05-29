package org.player.mp3player.controllers.title;

import javafx.application.Platform;
import javafx.scene.control.Label;
import lombok.RequiredArgsConstructor;
import org.player.mp3player.model.util.StringRotateShifter;

import java.util.TimerTask;

@RequiredArgsConstructor
public class SongTitleTimerTask extends TimerTask {
    private final Label songTitleLabel;
    private final StringRotateShifter songTitleRoute;
    private final int labelSize;

    @Override
    public void run() {
        Platform.runLater(() -> {
            String titlePartToSet=songTitleRoute.shiftLeftAndGet().substring(0, labelSize);
            songTitleLabel.setText(titlePartToSet);
        });
    }
}
