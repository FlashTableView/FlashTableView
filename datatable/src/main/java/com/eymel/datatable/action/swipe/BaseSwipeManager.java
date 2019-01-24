package com.eymel.datatable.action.swipe;

import android.support.annotation.NonNull;

import com.eymel.datatable.model.Row;

public abstract class BaseSwipeManager {
    protected OnRowSwipeListener onRowSwipeListener;

    public abstract void onRowSwipeLeft(@NonNull Row row);
    public abstract void onRowSwipeRight(@NonNull Row row);

}
