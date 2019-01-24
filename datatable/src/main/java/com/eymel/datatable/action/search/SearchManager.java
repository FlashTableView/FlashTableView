package com.eymel.datatable.action.search;

import android.widget.EditText;

import com.eymel.datatable.action.sort.SortingOrder;
import com.eymel.datatable.model.Column;
import com.eymel.datatable.model.Row;
import com.eymel.datatable.view.FlashTableView;

import java.util.ArrayList;
import java.util.List;

public class SearchManager implements ISearchable {
    FlashTableView flashTableView;
    List<Row> rows = new ArrayList<>();

    public SearchManager(FlashTableView flashTableView) {
        this.flashTableView = flashTableView;
    }

    @Override
    public void onEditTextChange(List<Row> rows, List<SearchModel> searchModels) {
        Search(rows, searchModels);
    }

    public  void Search(List<Row> rows, List<SearchModel> searchModels) {
        List<Column> columns = flashTableView.getTableModel().getHeaderColumns();
        for (Column column: columns
                ) {
                column.setSortingOrder(SortingOrder.NONE);
        }
        this.rows.clear();
        rows = cloneList(rows);

        for (SearchModel searchModel : searchModels
                ) {
            EditText editText = searchModel.getSearchEditText().getEditText();

            if (!editText.getText().toString().equals("")) {
                for (int i = 0; i < rows.size(); i++) {
                    Row item = rows.get(i);
                    if (!item.getCells().get(searchModel.getColumnIndex()).getText().toLowerCase().contains(editText.getText().toString().toLowerCase())) {
                        rows.remove(item);
                        i--;
                    }
                }

            }


        }
        flashTableView.setCurrentList(rows);
        flashTableView.UpdateList(rows);
    }

    public static List<Row> cloneList(final List<Row> list) {
        final ArrayList<Row> result = new ArrayList<Row>(list.size());
        for (final Row entry : list) {
            result.add(entry.clone());
        }
        return result;
    }


}

