package com.eymel.datatable.view.delegate;

import android.view.View;
import android.view.ViewGroup;

import com.eymel.datatable.adapter.TableHeaderAdapter;
import com.eymel.datatable.adapter.viewholder.HeaderHolder;
import com.eymel.datatable.model.Column;
import com.eymel.datatable.view.FlashTableView;

public class HeaderDelegate {
    public HeaderDelegate() {

    }

    public HeaderHolder onCreateDayHolder(FlashTableView flashTableView, ViewGroup parent, int viewType, int size) {
        final View view = flashTableView.getHeaderColumnView();


        return new HeaderHolder(view);
    }

    public void onBindDayHolder(final TableHeaderAdapter adapter, final HeaderHolder holder, final int position , Column column) {

        holder.bind(adapter,column,position);

        holder.itemView.setOnClickListener(v -> adapter.onHeaderClicked(column.getId()));


    }
}
