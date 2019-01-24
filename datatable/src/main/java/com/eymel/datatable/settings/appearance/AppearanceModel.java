package com.eymel.datatable.settings.appearance;

public class AppearanceModel implements AppearanceInterface {
    private int headerBackgroundResource;
    private int headerColumnTextColor;
    private int rowBackgroundResouce;
    private int mainBackgroundResource;
    private int selectedRowBackgroundResource;

    @Override
    public int getSelectedRowBackgroundResource() {
        return selectedRowBackgroundResource;
    }

    @Override
    public int getRowBackgroundResource() {
        return rowBackgroundResouce;
    }

    @Override
    public int getHeaderColumnTextColor() {
        return headerColumnTextColor;
    }

    @Override
    public int getHeaderBackgroundResource() {
        return headerBackgroundResource;
    }

    @Override
    public int getMainBackgroundResource() {
        return mainBackgroundResource;
    }

    @Override
    public void setSelectedRowBackgroundResource(int selectedRowBackgroundResource) {
        this.selectedRowBackgroundResource = selectedRowBackgroundResource;
    }

    @Override
    public void setMainBackgroundResource(int mainBackgroundResource) {
        this.mainBackgroundResource = mainBackgroundResource;
    }

    @Override
    public void setRowBackgroundResource(int rowBackgroundResource) {
        this.rowBackgroundResouce = rowBackgroundResource;
    }

    @Override
    public void setHeaderColumnTextColor(int headerColumnTextColor) {
        this.headerColumnTextColor = headerColumnTextColor;
    }

    @Override
    public void setHeaderBackgroundResource(int headerBackgroundResource) {
        this.headerBackgroundResource = headerBackgroundResource;
    }
}
