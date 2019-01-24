package com.eymel.datatable.action.sort;


import com.eymel.datatable.model.Column;
import com.eymel.datatable.model.Row;
import com.eymel.datatable.view.FlashTableView;

import java.text.Collator;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class SortManager implements TableHeaderClickListener {

    FlashTableView flashTableView;

    public SortManager(FlashTableView flashTableView) {
        this.flashTableView = flashTableView;
    }


    public void Sort(int columnIndex) {
        List<Column> columns = flashTableView.getTableModel().getHeaderColumns();
        for (Column column : columns
                ) {
            if (column.getId() != columnIndex)
                column.setSortingOrder(SortingOrder.NONE);
        }

        Column selectedColumn = columns.get(columnIndex);


        if (selectedColumn.isCanSortable()) {


            List<Row> rows = flashTableView.getCurrentList();
            if (rows.size() > 0) {
                Collections.sort(rows, (o1, o2) -> {

                    Collator collator = Collator.getInstance(new Locale("tr", "TR"));
                    return collator.compare(o1.getCells().get(columnIndex).getText(), o2.getCells().get(columnIndex).getText());
                });


                if (selectedColumn.getSortingOrder() == SortingOrder.ASCENDING || selectedColumn.getSortingOrder() == SortingOrder.NONE) {
                    flashTableView.UpdateList(rows);
                    selectedColumn.setSortingOrder(SortingOrder.DESCENDING);
                } else {
                    Collections.reverse(rows);
                    flashTableView.UpdateList(rows);
                    selectedColumn.setSortingOrder(SortingOrder.ASCENDING);
                }
            }
        }
    }

    @Override
    public void onHeaderClicked(int columnIndex) {
        Sort(columnIndex);
    }
}
