package com.eymel.datatable.model;

import java.util.List;

public class TableModel {
    private List<Column> headerColumns;
    private List<Row> rows;


    public List<Column> getHeaderColumns() {
        return headerColumns;
    }

    public void setHeaderColumns(List<Column> headerColumns) {
        this.headerColumns = headerColumns;
    }

    public List<Row> getRows() {
        return rows;
    }

    public void setRows(List<Row> rows) {
        this.rows = rows;
    }
}
