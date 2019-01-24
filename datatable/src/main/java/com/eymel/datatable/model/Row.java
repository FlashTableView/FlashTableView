package com.eymel.datatable.model;

import com.eymel.datatable.action.selection.SelectedState;

import java.util.List;

public class Row implements IClonable {
    private SelectedState selectedState;
    private int rowId;
    private List<Cell> Cells;
    private int backgroundResource;
    private boolean isCanSwipeLeft=true;
    private boolean isCanSwipeRight=true;

    public Row() {
        this.selectedState = SelectedState.NONE;
    }

    private Row(Row that) {
        this(that.rowId, that.Cells, that.selectedState,that.backgroundResource,that.isCanSwipeRight,that.isCanSwipeLeft);
    }

    public Row(int rowId, List<Cell> cells, SelectedState selectedState,int backgroundResource) {
        this.rowId = rowId;
        this.Cells = cells;
        this.selectedState = selectedState;
        this.backgroundResource =backgroundResource;
    }
    public Row(int rowId, List<Cell> cells, SelectedState selectedState,int backgroundResource,boolean isCanSwipeLeft ,boolean isCanSwipeRight) {
        this.rowId = rowId;
        this.Cells = cells;
        this.selectedState = selectedState;
        this.backgroundResource =backgroundResource;
        this.isCanSwipeLeft=isCanSwipeLeft;
        this.isCanSwipeRight=isCanSwipeRight;
    }

    public Row(int rowId, List<Cell> cells) {
        this.rowId = rowId;
        this.Cells = cells;
        this.selectedState = SelectedState.NONE;
    }

    public int getRowId() {
        return rowId;
    }

    public void setRowId(int rowId) {
        this.rowId = rowId;
    }

    public List<Cell> getCells() {
        return Cells;
    }

    public void setCells(List<Cell> cells) {
        Cells = cells;
    }


    @Override
    public Row clone() {

        return new Row(this.rowId, this.Cells, this.selectedState, this.backgroundResource, this.isCanSwipeLeft, this.isCanSwipeRight);
    }

    public SelectedState getSelectedState() {
        return selectedState;
    }

    public void setSelectedState(SelectedState selectedState) {
        this.selectedState = selectedState;
    }

    public int getBackgroundResource() {
        return backgroundResource;
    }

    public void setBackgroundResource(int backgroundResource) {
        this.backgroundResource = backgroundResource;
    }

    public boolean isCanSwipeRight() {
        return isCanSwipeRight;
    }

    public boolean isCanSwipeLeft() {
        return isCanSwipeLeft;
    }

    public void setCanSwipeLeft(boolean canSwipeLeft) {
        isCanSwipeLeft = canSwipeLeft;
    }

    public void setCanSwipeRight(boolean canSwipeRight) {
        isCanSwipeRight = canSwipeRight;
    }
}
