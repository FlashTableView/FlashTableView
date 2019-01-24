package com.eymel.datatable.adapter.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.eymel.datatable.adapter.TableHeaderAdapter;
import com.eymel.datatable.model.Column;
import com.eymel.datatable.view.customviews.HeaderColumnView;

public class HeaderHolder extends RecyclerView.ViewHolder {
    HeaderColumnView view;

    public HeaderHolder(View itemView) {
        super(itemView);
        view = (HeaderColumnView) itemView;
    }

    public void bind(TableHeaderAdapter adapter, Column column, int position) {
        int width = adapter.getFlashTableView().getDefaultWidth();
        if (width==0)
          width = (adapter.getTableWitdh() - adapter.getFlashTableView().calculateSizedColumnWidth()) / adapter.getFlashTableView().calculateNotSizedColumn();
        if (column.getColumnWidth() != 0)
            view.setLayoutParams(new LinearLayout.LayoutParams(column.getColumnWidth(), ViewGroup.LayoutParams.MATCH_PARENT, 1));
        else {

            view.setLayoutParams(new LinearLayout.LayoutParams(width, ViewGroup.LayoutParams.MATCH_PARENT, 1));
        }

        if (column.isCanSortable())
        view.setSortIcon(column.getSortingOrder());

        view.setTextHeader(column.getTitle());
    }
}
