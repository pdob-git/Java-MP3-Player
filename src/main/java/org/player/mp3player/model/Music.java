/*
    Singleton to store Music data. Ony one instance per application.
 */

package org.player.mp3player.model;

import java.util.List;

public class Music {
    private static Music instance;
    private List<MusicItem> playList;

    private Music() {

    }

    public static Music getInstance(List<MusicItem> playList) {
        Music.getInstance().playList = playList;
        return instance;
    }

    public static Music getInstance() {
        if (instance == null) {
            instance = new Music();
        }
        return instance;
    }

    public String getSongPath(int songNumber) {
       return playList.get(songNumber).getPath();
    }

    public String getSongTitle(int songNumber) {
        return playList.get(songNumber).getTitle();
    }

    public List<MusicItem> getPlaylist() {
        return playList;
    }
}