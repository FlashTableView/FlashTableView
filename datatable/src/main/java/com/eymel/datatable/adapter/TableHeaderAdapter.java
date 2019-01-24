package com.eymel.datatable.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.eymel.datatable.action.sort.SortManager;
import com.eymel.datatable.action.sort.TableHeaderClickListener;
import com.eymel.datatable.adapter.viewholder.HeaderHolder;
import com.eymel.datatable.model.Column;
import com.eymel.datatable.view.FlashTableView;
import com.eymel.datatable.view.delegate.HeaderDelegate;

import java.util.List;

public class TableHeaderAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements TableHeaderClickListener {
    private List<Column> headers;
    private HeaderDelegate headerDelegate;
    private SortManager sortManager;
    private int tableWitdh;
    private FlashTableView flashTableView;

    public TableHeaderAdapter(Context context, FlashTableView flashTableView, List<Column> headers) {
        this.flashTableView = flashTableView;
        Context context1 = context;
        this.headers = headers;
        this.headerDelegate = new HeaderDelegate();
        this.sortManager = new SortManager(flashTableView);

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        tableWitdh = parent.getMeasuredWidth();
        return headerDelegate.onCreateDayHolder(flashTableView, parent, viewType, getItemCount());
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        headerDelegate.onBindDayHolder(this, (HeaderHolder) holder, position, headers.get(position));
    }

    @Override
    public int getItemCount() {
        return headers.size();
    }

    public FlashTableView getFlashTableView() {
        return flashTableView;
    }


    @Override
    public void onHeaderClicked(int columnIndex) {
        sortManager.onHeaderClicked(columnIndex);
    }

    public int getTableWitdh() {
        return tableWitdh;
    }

    public static class TableHeaderAdapterBuilder {
        private Context context;
        private FlashTableView flashTableView;
        private List<Column> columns;
        private SortManager sortManager;

        public TableHeaderAdapter createTableHeaderAdapter() {
            return new TableHeaderAdapter(context, flashTableView, columns);
        }

        public TableHeaderAdapterBuilder setContext(Context context) {
            this.context = context;
            return this;
        }

        public TableHeaderAdapterBuilder setColumns(List<Column> columns) {
            this.columns = columns;
            return this;
        }

        public TableHeaderAdapterBuilder setFlashTableView(FlashTableView flashTableView) {
            this.flashTableView = flashTableView;
            return this;
        }
    }
}
