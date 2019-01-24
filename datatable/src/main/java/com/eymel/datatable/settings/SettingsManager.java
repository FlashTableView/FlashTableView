package com.eymel.datatable.settings;

import com.eymel.datatable.model.TableModel;
import com.eymel.datatable.settings.appearance.AppearanceInterface;
import com.eymel.datatable.settings.appearance.AppearanceModel;
import com.eymel.datatable.util.IListManager;
import com.eymel.datatable.util.ListManager;

public class SettingsManager implements AppearanceInterface, IListManager {
    //Models
    private AppearanceModel appearanceModel;
    private ListManager listManager;


    public SettingsManager() {
        appearanceModel = new AppearanceModel();
        listManager = new ListManager();

    }


    @Override
    public int getSelectedRowBackgroundResource() {
        return appearanceModel.getSelectedRowBackgroundResource();
    }

    @Override
    public int getRowBackgroundResource() {
        return appearanceModel.getRowBackgroundResource();
    }

    @Override
    public int getHeaderColumnTextColor() {
        return appearanceModel.getHeaderColumnTextColor();
    }

    @Override
    public int getHeaderBackgroundResource() {
        return appearanceModel.getHeaderBackgroundResource();
    }

    @Override
    public int getMainBackgroundResource() {
        return appearanceModel.getMainBackgroundResource();
    }

    @Override
    public void setSelectedRowBackgroundResource(int selectedRowBackgroundResource) {
        appearanceModel.setSelectedRowBackgroundResource(selectedRowBackgroundResource);
    }

    @Override
    public void setMainBackgroundResource(int mainBackgroundResource) {
        appearanceModel.setMainBackgroundResource(mainBackgroundResource);
    }

    @Override
    public void setRowBackgroundResource(int rowBackgroundResource) {
        appearanceModel.setRowBackgroundResource(rowBackgroundResource);
    }

    @Override
    public void setHeaderColumnTextColor(int headerColumnTextColor) {
        appearanceModel.setHeaderColumnTextColor(headerColumnTextColor);
    }

    @Override
    public void setHeaderBackgroundResource(int headerBackgroundResource) {
        appearanceModel.setHeaderBackgroundResource(headerBackgroundResource);
    }


    @Override
    public TableModel getTableModel() {
        return getListManager().getTableModel();
    }

    @Override
    public void setTableModel(TableModel tableModel) {
        getListManager().setTableModel(tableModel);
    }


    public ListManager getListManager() {
        return listManager;
    }


}
