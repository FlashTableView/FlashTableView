package com.eymel.flashtableview.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.eymel.datatable.action.swipe.OnRowSwipeListener;
import com.eymel.datatable.listeners.OnRowSizeChangeListener;
import com.eymel.datatable.model.Cell;
import com.eymel.datatable.model.Column;
import com.eymel.datatable.model.Row;
import com.eymel.datatable.model.TableModel;
import com.eymel.datatable.view.FlashTableView;
import com.eymel.flashtableview.R;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ArrayList<Column> headers = new ArrayList<>();
    FlashTableView tableView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tableView = findViewById(R.id.flashtableview);
        headers = new ArrayList<>();
        CreateHeader();
        setTable();
    }

    private void CreateHeader() {
        headers.add(new Column(0, "Baslık1", true, true));
        headers.add(new Column(1, "Baslık2", true, true));
        headers.add(new Column(2, "Baslık3", true, true));
        headers.add(new Column(3, "Baslık4", true, true));
        headers.add(new Column(4, "Baslık5", true, true));
    }

    private void setTable() {

        tableView.setCanSwipeRigth(true, "Düzenle");
        tableView.setCanSwipeLeft(false, "");
        tableView.setOnRowSwipeListener(new OnRowSwipeListener() {
            @Override
            public Row onRowSwipeLeft(Row row) {

                return row;
            }

            @Override
            public Row onRowSwipeRight(Row row) {
                return null;
            }
        });


        //region TableModel initialize
        List<Row> rows = new ArrayList<>();
        TableModel tableModel = new TableModel();
        for (int i = 0; i < 100; i++) {

            Row row = new Row();
            row.setRowId(i);
            List<Cell> cells = new ArrayList<>();
            cells.add(new Cell(headers.get(0), "başlık1 satır" + i));
            cells.add(new Cell(headers.get(1), "başlık2 satır" + i));
            cells.add(new Cell(headers.get(2), "başlık3 satır" + i));
            cells.add(new Cell(headers.get(3), "başlık4 satır" + i));
            cells.add(new Cell(headers.get(4), "başlık5 satır" + i));
            row.setCells(cells);
            rows.add(row);
        }


        tableModel.setHeaderColumns(headers);
        tableModel.setRows(rows);
        tableView.setTableModel(tableModel);
        //endregion


        tableView.setOnRowSizeChangeListener(new OnRowSizeChangeListener() {
            @Override
            public void onRowSizeChanged(int size) {

            }
        });

        tableView.show();


    }
}
