package com.eymel.datatable.settings.appearance;

public interface AppearanceInterface {

    int getSelectedRowBackgroundResource();

    int getRowBackgroundResource();

    int getHeaderColumnTextColor();

    int getHeaderBackgroundResource();

    int getMainBackgroundResource();


    void setSelectedRowBackgroundResource(int selectedRowBackgroundResource);

    void setMainBackgroundResource(int mainBackgroundResource);

    void setRowBackgroundResource(int rowBackgroundResource);

    void setHeaderColumnTextColor(int headerColumnTextColor);

    void setHeaderBackgroundResource(int headerBackgroundResource);
}
