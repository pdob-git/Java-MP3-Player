package org.player.mp3player.repositories;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import lombok.extern.slf4j.Slf4j;
import org.player.mp3player.model.MusicItem;
import org.player.mp3player.services.DataLoader;

import java.io.File;

import java.util.ArrayList;


@Slf4j
public class FileDataLoader implements DataLoader {

    private MusicItem musicItem;
    ArrayList<MusicItem> playList = new ArrayList<>();

    public FileDataLoader(){

    }


    @Override
    public ArrayList<MusicItem> loadData(File directory) {

        File[] files = directory.listFiles();

//        System.out.println("In File DataLoader");

        if (files != null){
            int id = 1;
            for (File file : files){
                String path = file.getPath();
                String substring = path.substring(path.length() - 4);
                if( substring.equals(".mp3")){

                    addMusicItemToPlaylist(id, file);
                    id++;
                }

                //To comment out
//                System.out.println("LoadData");
//                System.out.println(file.getName());
            }
        }
        log.debug("Loaded files : {}", playList.size());
        return playList;
    }

    private void addMusicItemToPlaylist(int id,File file){

        Media media = new Media(file.toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(media);

        mediaPlayer.setOnReady(new Runnable() {

            @Override
            public void run() {
                log.debug("createMusicItem");
                log.debug("File " + file.getName());
                log.debug("Duration: " + calculateSongTime(media.getDuration()));
                log.debug("Artist: " + media.getMetadata().get("artist"));
                log.debug("Title: " + media.getMetadata().get("title"));

                Integer musicId = id;
                String artist = (String)media.getMetadata().get("artist");
                String title = (String)media.getMetadata().get("title");
                String time = calculateSongTime(media.getDuration());
                String path = file.toURI().toString();

                if(title == null || title.isEmpty()){
                    title = file.getName().substring(0,file.getName().length()-4);
                }

                musicItem = new MusicItem(musicId, artist, title, time, path);
                playList.add(musicItem);
            }
        });
    }

    private String calculateSongTime(Duration duration){

        int timeSec = (int)duration.toSeconds();
        //round down
        int timeMin = (int)duration.toMinutes();

        timeSec = timeSec - 60 * timeMin;
        String time = String.valueOf(timeMin);
        if (timeSec < 10){
            time = time + ":0" + timeSec;
        }
        else{
            time = time + ":" + timeSec;
        }

        return time;
    }
}
