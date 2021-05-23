package org.player.mp3player.model;

import lombok.AllArgsConstructor;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MusicItem {

    private Integer id;
    private String artist;
    private String title;
    private String time;
    private String path;

}
