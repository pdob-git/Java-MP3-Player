package org.player.mp3player.controllers.table;

import javafx.scene.control.TableView;
import org.player.mp3player.model.MusicItem;

import java.util.List;

public class Table<S> extends TableView {

    private StyleChangingRowFactory<MusicItem> rowFactory;

    private List<Integer> HighlightedRows;
    private MusicItem musicItemHighlighted;

    private AfterSort afterSort;


    @Override
    public void sort() {
        super.sort();
        afterSort.afterSortAction();
    }

    public List<Integer> getHighlightedRows() {
        return HighlightedRows;
    }

    public void setHighlightedRows(List<Integer> highlightedRows) {
        HighlightedRows = highlightedRows;
    }

    public MusicItem getMusicItemHighlighted() {
        return musicItemHighlighted;
    }

    public void setMusicItemHighlighted(MusicItem personHighlighted) {
        this.musicItemHighlighted = personHighlighted;
    }

    public void setAfterSort(AfterSort afterSort) {
        this.afterSort = afterSort;
    }


}

