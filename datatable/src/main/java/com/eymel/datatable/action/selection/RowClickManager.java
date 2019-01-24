package com.eymel.datatable.action.selection;

import android.support.annotation.NonNull;


import com.eymel.datatable.model.Row;

public class RowClickManager extends BaseClickManager {
    private Row row;

    public RowClickManager(OnRowClickListener onRowClickListener) {
        this.onRowClickListener = onRowClickListener;
    }

    @Override
    public void toggleRow(@NonNull Row row) {
        this.row = row;
        onRowClickListener.onRowClick(row);
    }

    @Override
    public boolean isRowSelected(@NonNull Row row) {
        return row.equals(this.row);
    }

    @Override
    public void clearSelections() {
        row = null;
    }
}
