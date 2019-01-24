package com.eymel.datatable.action.swipe;

import com.eymel.datatable.model.Row;

public interface OnRowSwipeListener {
    Row onRowSwipeLeft(Row row);
    Row onRowSwipeRight(Row row);
}
