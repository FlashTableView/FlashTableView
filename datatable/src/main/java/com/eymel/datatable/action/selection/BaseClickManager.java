package com.eymel.datatable.action.selection;

import android.support.annotation.NonNull;


import com.eymel.datatable.model.Row;

public abstract class BaseClickManager  {
    protected OnRowClickListener onRowClickListener;

    public abstract void toggleRow(@NonNull Row row);

    public abstract boolean isRowSelected(@NonNull Row day);

    public abstract void clearSelections();

    public BaseClickManager() {
    }
}
