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
import org.player.mp3player.controllers.table.StyleChangingRowFactory;
import org.player.mp3player.controllers.table.Table;
import org.player.mp3player.model.Music;
import org.player.mp3player.model.MusicItem;

import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class PlayListWindow implements Initializable {
    private String initialPath = "org/player/mp3player/";

    @FXML
    private BorderPane borderPane;


    private Table<MusicItem> tableView;

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
        setTableViewMethods();


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
        tableView = new Table<MusicItem>();

        tableView.setId("tableView");

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

//        tableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
//        button.disableProperty().bind(Bindings.isEmpty(tableView.getSelectionModel().getSelectedIndices()));
        button.setDisable(true);
    }

    public void highlightSelected() {
//        rowFactory.getStyledRowIndices().setAll(tableView.getSelectionModel().getSelectedIndices());
    }

    public void highlightPlayed(int songPlayed) {
        ObservableList<Integer> rowsToHighlight = FXCollections.observableList(Arrays.asList(songPlayed));
        rowFactory.getStyledRowIndices().clear();
        rowFactory.getStyledRowIndices().setAll(rowsToHighlight);
    }

    private void setTableViewMethods(){

        tableView.setOnSort(event -> {
            System.out.println("-----Set On Sort Start");

            tableView.setHighlightedRows(rowFactory.getStyledRowIndices());

            if (tableView.getHighlightedRows().size() == 0) {
                tableView.setMusicItemHighlighted(null);
                System.out.println("-----Set On Sort interrupted");
                return;
            }

            tableView.setMusicItemHighlighted((MusicItem) tableView.getItems().get(tableView.getHighlightedRows().get(0)));

            if (tableView.getMusicItemHighlighted() == null){
                System.out.println("-----Set On Sort interrupted");
                return;
            }
//            showHighlightedRows(tableView.getHighlightedRows());
//            System.out.println("Table before sort");
//            System.out.println(printTableItems((ObservableList<MusicItem>)tableView.getItems()));

            System.out.println("-----Set On Sort End");
        });

        tableView.setAfterSort(() -> {
            System.out.println("After Sort is working");
            System.out.println("-----After Sort  start");

            if (tableView.getMusicItemHighlighted() == null){
                System.out.println("-----After Sort interrupted");
                return;
            }

            rowFactory.getStyledRowIndices().clear();

            tableView.getHighlightedRows().add(tableView.getItems().indexOf(tableView.getMusicItemHighlighted()));

            final StyleChangingRowFactory<MusicItem> rowFactoryLocal = new StyleChangingRowFactory<>("highlightedRow");
            tableView.setRowFactory(rowFactoryLocal);

            rowFactoryLocal.getStyledRowIndices().setAll(tableView.getHighlightedRows());

            tableView.setHighlightedRows(rowFactoryLocal.getStyledRowIndices());


//            showHighlightedRows(tableView.getHighlightedRows());
//
//            System.out.println("Table after sort");
//            System.out.println(printTableItems((ObservableList<MusicItem>)tableView.getItems()));

            //Select right current song number
            mainWindowController.setSongNumber(tableView.getItems().indexOf(tableView.getMusicItemHighlighted()));

            System.out.println("-----After Sort end");
        });

    }

    private void showHighlightedRows(List<Integer> highlightedRows) {
        if (highlightedRows.size() == 0) {
            return;
        }
        for (Integer rowNumber :
                highlightedRows) {
            MusicItem musicItem = (MusicItem) tableView.getItems().get(rowNumber);
            System.out.println("Highlighted person: " + musicItem);
            System.out.println("Highlighted person index: " + tableView.getItems().indexOf(musicItem));

        }
    }

    public String printTableItems(ObservableList<MusicItem> tableItems) {
        StringBuilder stringResult = new StringBuilder();
        for (MusicItem musicItem:
                tableItems) {
            stringResult.append("Index :").append(tableItems.indexOf(musicItem)).append(" ")
                    .append(musicItem.toString()).append("\n");
        }
        stringResult.append("----------------------");
        return stringResult.toString();
    }

}
