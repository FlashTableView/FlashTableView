package com.eymel.datatable.model;

import android.view.View;

public class Cell {

    private Column column;
    private String text;
    private View customView;
    private ViewType viewType;

    public Cell(Column column, String data) {
        this.setColumn(column);
        this.setText(data);
        this.setViewType(ViewType.TEXTVIEW);
    }

    public Cell(Column column, String data, View view, ViewType viewType) {
        this.setColumn(column);
        this.setText(data);
        this.setViewType(viewType);
        this.setCustomView(view);
    }


    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Column getColumn() {
        return column;
    }

    public void setColumn(Column column) {
        this.column = column;
    }

    public ViewType getViewType() {
        return viewType;
    }

    public void setViewType(ViewType viewType) {
        this.viewType = viewType;
    }

    public View getCustomView() {
        return customView;
    }

    public void setCustomView(View customView) {
        this.customView = customView;
    }
}
