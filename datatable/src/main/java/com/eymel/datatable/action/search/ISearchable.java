package com.eymel.datatable.action.search;

import com.eymel.datatable.model.Row;

import java.util.List;

public interface ISearchable {
    void onEditTextChange(List<Row> rows, List<SearchModel> searchModels);
}
