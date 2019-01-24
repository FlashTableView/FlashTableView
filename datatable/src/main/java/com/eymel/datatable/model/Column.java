package com.eymel.datatable.model;

import android.view.View;
import android.widget.CompoundButton;

import com.eymel.datatable.action.sort.SortingOrder;

public class Column {
    private int Id;
    private String Title;
    private boolean isCanSearchable;
    private boolean isCanSortable;
    private SortingOrder sortingOrder;
    private CompoundButton.OnCheckedChangeListener onCheckedChangeListener ;
    private View.OnClickListener onClickListener;
    public Column(int id, String title, boolean isCanSearchable, boolean isCanSortable, int columnWidth) {
        Id = id;
        Title = title;
        this.isCanSearchable = isCanSearchable;
        this.isCanSortable = isCanSortable;
        this.columnWidth = columnWidth;
        this.sortingOrder=SortingOrder.DESCENDING;
    }
    public Column(int id, String title, boolean isCanSearchable, boolean isCanSortable, int columnWidth, CompoundButton.OnCheckedChangeListener onCheckedChangeListener) {
        Id = id;
        Title = title;
        this.isCanSearchable = isCanSearchable;
        this.isCanSortable = isCanSortable;
        this.columnWidth = columnWidth;
        this.sortingOrder=SortingOrder.DESCENDING;
        this.onCheckedChangeListener = onCheckedChangeListener;
    }

    private int columnWidth;

    public Column(int id, String title, boolean isCanSearchable, boolean isCanSortable) {
        Id = id;
        Title = title;
        this.isCanSearchable = isCanSearchable;
        this.isCanSortable = isCanSortable;
        this.sortingOrder=SortingOrder.DESCENDING;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public boolean isCanSearchable() {
        return isCanSearchable;
    }

    public void setCanSearchable(boolean canSearchable) {
        isCanSearchable = canSearchable;
    }

    public boolean isCanSortable() {
        return isCanSortable;
    }

    public void setCanSortable(boolean canSortable) {
        isCanSortable = canSortable;
    }

    public SortingOrder getSortingOrder() {
        return sortingOrder;
    }

    public void setSortingOrder(SortingOrder sortingOrder) {
        this.sortingOrder = sortingOrder;
    }

    public int getColumnWidth() {
        return columnWidth;
    }

    public void setColumnWidth(int columnWidth) {
        this.columnWidth = columnWidth;
    }

    public CompoundButton.OnCheckedChangeListener getOnCheckedChangeListener() {
        return onCheckedChangeListener;
    }

    public void setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener onCheckedChangeListener) {
        this.onCheckedChangeListener = onCheckedChangeListener;
    }

    public View.OnClickListener getOnClickListener() {
        return onClickListener;
    }

    public void setOnClickListener(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }
}
