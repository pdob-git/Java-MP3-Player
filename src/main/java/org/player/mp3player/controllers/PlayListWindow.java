package org.player.mp3player.controllers;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lombok.AccessLevel;
import lombok.Setter;
import org.player.mp3player.controllers.rowselection.StyleChangingRowFactory;
import org.player.mp3player.model.Music;
import org.player.mp3player.model.MusicItem;

import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;

public class PlayListWindow implements Initializable {

    @FXML
    private TableView<MusicItem> tableView;

    @FXML
    private Button button;

    @FXML
    private TableColumn idColumn, artistColumn, titleColumn, timeColumn;

    private ObservableList<MusicItem> playListData;

    private StyleChangingRowFactory<MusicItem> rowFactory;


    @Setter(AccessLevel.PUBLIC) private MainWindow mainWindowController;



    @Override
    public void initialize(URL location, ResourceBundle resources) {

        setTableViewData();
        setTableViewStyles();
        tableView.setId("tableView");

        tableView.setOnMouseClicked(event -> {
            if ((event.getClickCount() == 2) && (tableView.getSelectionModel().getSelectedItem() != null)) {
                mainWindowController.stopMedia();
                mainWindowController.setSongNumber(tableView.getSelectionModel().getSelectedItem().getId()-1);
                mainWindowController.playMedia();
            }
        });

    }

    private void setTableViewData() {
        idColumn.setCellValueFactory(
                new PropertyValueFactory<MusicItem, Integer>("id")
        );

        artistColumn.setCellValueFactory(
                new PropertyValueFactory<MusicItem, String>("artist")
        );

        titleColumn.setCellValueFactory(
                new PropertyValueFactory<MusicItem, String>("title")
        );

        timeColumn.setCellValueFactory(
                new PropertyValueFactory<MusicItem, String>("time")
        );

        playListData = FXCollections.observableList(Music.getInstance().getPlaylist());
        tableView.setItems(playListData);
    }

    private void setTableViewStyles() {
        rowFactory = new StyleChangingRowFactory<>("highlightedRow");
        tableView.setRowFactory(rowFactory);

        tableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
//        button.disableProperty().bind(Bindings.isEmpty(tableView.getSelectionModel().getSelectedIndices()));
        button.setDisable(true);
    }

    public void highlightSelected() {
//        rowFactory.getStyledRowIndices().setAll(tableView.getSelectionModel().getSelectedIndices());
    }

    public void highlightPlayed(int songPlayed) {
        ObservableList<Integer> rowsToHighlight = FXCollections.observableList(Arrays.asList(songPlayed));
        rowFactory.getStyledRowIndices().setAll(rowsToHighlight);
    }

}
