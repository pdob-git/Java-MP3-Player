package org.player.mp3player.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.player.mp3player.model.Music;
import org.player.mp3player.model.MusicItem;

import java.net.URL;
import java.util.ResourceBundle;

public class PlayListWindow  implements Initializable {

    @FXML
    private TableView<MusicItem> tableView;



    @FXML
    private TableColumn idColumn, artistColumn, titleColumn, timeColumn;

    private ObservableList<MusicItem> playListData;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        setTableViewData();
        tableView.setId("tableView");

    }

    private void setTableViewData(){
        idColumn.setCellValueFactory(
                new PropertyValueFactory<MusicItem,Integer>("id")
        );

        artistColumn.setCellValueFactory(
                new PropertyValueFactory<MusicItem,String>("artist")
        );

        titleColumn.setCellValueFactory(
                new PropertyValueFactory<MusicItem,String>("title")
        );

        timeColumn.setCellValueFactory(
                new PropertyValueFactory<MusicItem,String>("time")
        );

//        playListData = FXCollections.observableList(new ArrayList<MusicItem>());
//        MusicItem testMusicItem = new MusicItem(1,"Cleo","Misie","2:25",null);
//        playListData.add(testMusicItem);

        playListData = FXCollections.observableList(Music.getInstance().getPlaylist());
        tableView.setItems(playListData);
    }
}


//    private Integer id;
//    private String artist;
//    private String title;
//    private Double time;
//    private String path;
