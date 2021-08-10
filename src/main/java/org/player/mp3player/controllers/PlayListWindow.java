package org.player.mp3player.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import lombok.AccessLevel;
import lombok.Setter;
import org.player.mp3player.controllers.rowselection.StyleChangingRowFactory;
import org.player.mp3player.model.Music;
import org.player.mp3player.model.MusicItem;

import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;

public class PlayListWindow implements Initializable {
    private String initialPath = "org/player/mp3player/";

    @FXML
    private BorderPane borderPane;


    private TableView<MusicItem> tableView;

    @FXML
    private Button button;


    private TableColumn<MusicItem, Integer> idColumn;

    private TableColumn<MusicItem, String> artistColumn;

    private TableColumn<MusicItem, String> titleColumn;

    private TableColumn<MusicItem, String> timeColumn;
    private StyleChangingRowFactory<MusicItem> rowFactory;

    private ResourceBundle resourceBundle;

    @Setter(AccessLevel.PUBLIC)
    private MainWindow mainWindowController;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        resourceBundle = resources;
        initTable();
        setTableViewData();
        setTableViewStyles();
        tableView.setId("tableView");

        tableView.setOnMouseClicked(event -> {
            if ((event.getClickCount() == 2) && (tableView.getSelectionModel().getSelectedItem() != null)) {
                mainWindowController.stopMedia();
                mainWindowController.setSongNumber(tableView.getSelectionModel().getFocusedIndex());
                mainWindowController.playMedia();
            }
        });

    }

    private void initTable() {
        // Create tableview
        tableView = new TableView<>();

        // Create columns
        idColumn = new TableColumn<>(resourceBundle.getString("playlist.id"));
        artistColumn = new TableColumn<>(resourceBundle.getString("playlist.artist"));
        titleColumn = new TableColumn<>(resourceBundle.getString("playlist.title"));
        timeColumn = new TableColumn<>(resourceBundle.getString("playlist.time"));

        // Set columns widths
        idColumn.setMaxWidth(800.0);
        idColumn.setPrefWidth(25.0);

        artistColumn.setMaxWidth(3500.0);
        artistColumn.setPrefWidth(110.0);

        timeColumn.setMaxWidth(800.0);
        titleColumn.setPrefWidth(210.0);

        timeColumn.setMaxWidth(1200.0);
        timeColumn.setPrefWidth(40.0);

        tableView.getColumns().setAll(idColumn, artistColumn, titleColumn, timeColumn);

        tableView.setColumnResizePolicy(tableView.CONSTRAINED_RESIZE_POLICY);
        tableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        //Add to main pain of controller borderPane
        borderPane.setCenter(tableView);

    }

    private void setTableViewData() {
        idColumn.setCellValueFactory(
                new PropertyValueFactory<>("id")
        );

        artistColumn.setCellValueFactory(
                new PropertyValueFactory<>("artist")
        );

        titleColumn.setCellValueFactory(
                new PropertyValueFactory<>("title")
        );

        timeColumn.setCellValueFactory(
                new PropertyValueFactory<>("time")
        );

        ObservableList<MusicItem> playListData = FXCollections.observableList(Music.getInstance().getPlaylist());
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
