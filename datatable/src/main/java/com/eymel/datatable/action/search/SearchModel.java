package com.eymel.datatable.action.search;

import com.eymel.datatable.view.customviews.SearchEditText;

public class SearchModel {
    private int columnIndex;
    private SearchEditText searchEditText;

    public int getColumnIndex() {
        return columnIndex;
    }

    public void setColumnIndex(int columnIndex) {
        this.columnIndex = columnIndex;
    }

    public SearchEditText getSearchEditText() {
        return searchEditText;
    }

    public void setSearchEditText(SearchEditText searchEditText) {
        this.searchEditText = searchEditText;
    }
}
