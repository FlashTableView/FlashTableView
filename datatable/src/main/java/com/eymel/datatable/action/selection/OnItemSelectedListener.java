package com.eymel.datatable.action.selection;

import com.eymel.datatable.model.Row;

public interface OnItemSelectedListener {

    void onItemSelected(Row row);
    void onItemCancelSelect(Row row);
}
