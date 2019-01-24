package com.eymel.datatable.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextWatcher;
import android.view.ViewGroup;

import com.eymel.datatable.action.search.OnSearchItemCreated;
import com.eymel.datatable.action.search.SearchModel;
import com.eymel.datatable.adapter.viewholder.SearchHolder;
import com.eymel.datatable.model.Column;
import com.eymel.datatable.view.FlashTableView;
import com.eymel.datatable.view.delegate.SearchDelegate;

import java.util.ArrayList;
import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter implements OnSearchItemCreated {
    private Context context;
    private List<Column> headers;
    private SearchDelegate searchDelegate;
    private FlashTableView flashTableView;
    private List<SearchModel> searchModels;
    private int tableWidth;

    public SearchAdapter(Context context, FlashTableView flashTableView, List<Column> headers, TextWatcher textWatcher) {
        this.flashTableView = flashTableView;
        this.context = context;
        this.headers = headers;
        this.searchDelegate = new SearchDelegate();
        TextWatcher textWatcher1 = textWatcher;
        this.searchModels = new ArrayList<>();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        tableWidth = parent.getMeasuredWidth();
        return searchDelegate.onCreateDayHolder(parent, viewType, getItemCount());
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        searchDelegate.onBindDayHolder(this, flashTableView, (SearchHolder) holder, position, headers.get(position));
    }

    @Override
    public int getItemCount() {
        return headers.size();
    }


    public Context getContext() {
        return context;
    }

    public void addSearchItem(SearchModel searchModel) {
        searchModels.add(searchModel);
        if (controlSize())
            flashTableView.onbindSearchItem();

    }

    public void UpdateList(List<Column> rowList, int totalCount) {
        headers = new ArrayList<>();
        headers.addAll(rowList);
        notifyDataSetChanged();


    }


    public boolean controlSize() {
        return searchModels.size() == headers.size();
    }

    public List<SearchModel> getAllSearchModel() {
        return searchModels;
    }

    @Override
    public void onbindSearchItem() {
        flashTableView.onbindSearchItem();
    }

    public FlashTableView getFlashTableView() {
        return flashTableView;
    }

    public int getTableWidth() {
        return tableWidth;
    }


    public static class SearchTableBuilder {
        private Context context;
        private FlashTableView flashTableView;
        private List<Column> columns;
        private TextWatcher textWatcher;

        public SearchAdapter createTableSearchAdapter() {
            return new SearchAdapter(context, flashTableView, columns, textWatcher);
        }

        public SearchTableBuilder setTextWatcher(TextWatcher textWatcher) {
            this.textWatcher = textWatcher;
            return this;
        }

        public SearchTableBuilder setContext(Context context) {
            this.context = context;
            return this;
        }

        public SearchTableBuilder setColumns(List<Column> columns) {
            this.columns = columns;
            return this;
        }

        public SearchTableBuilder setFlashTableView(FlashTableView flashTableView) {
            this.flashTableView = flashTableView;
            return this;
        }
    }
}
