package org.player.mp3player.services;

import org.player.mp3player.model.MusicItem;

import java.io.File;
import java.util.ArrayList;

public interface DataLoader {

    ArrayList<MusicItem> loadData(File directory);

}
