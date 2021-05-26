/*
    Singleton to store Music data. Ony one instance per application.
 */

package org.player.mp3player.model;

import java.util.ArrayList;

public class Music {
    private static Music instance;
    public ArrayList<MusicItem> playList;

    private Music() {

    }

    public static Music getInstance(ArrayList<MusicItem> playList) {
        Music.getInstance().playList = playList;
        return instance;
    }

    public static Music getInstance() {
        if (instance == null) {
            instance = new Music();
        }
        return instance;
    }

}