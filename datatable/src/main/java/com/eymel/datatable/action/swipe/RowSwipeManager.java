package com.eymel.datatable.action.swipe;

import com.eymel.datatable.model.Row;

public class RowSwipeManager extends BaseSwipeManager {

    private Row row;

    public RowSwipeManager(OnRowSwipeListener onRowSwipeListener) {
       this.onRowSwipeListener=onRowSwipeListener;
    }

    @Override
    public void onRowSwipeLeft(Row row) {
        this.row=row;
        this.onRowSwipeLeft(row);
    }

    @Override
    public void onRowSwipeRight(Row row) {
        this.row=row;
        this.onRowSwipeRight(row);
    }
}
