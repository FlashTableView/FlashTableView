package com.eymel.datatable.view.delegate;

import android.view.View;
import android.view.ViewGroup;

import com.eymel.datatable.adapter.SearchAdapter;
import com.eymel.datatable.adapter.viewholder.SearchHolder;
import com.eymel.datatable.model.Column;
import com.eymel.datatable.view.FlashTableView;
import com.eymel.datatable.view.customviews.SearchEditText;

public class SearchDelegate {
    public SearchDelegate() {

    }

    public SearchHolder onCreateDayHolder(ViewGroup parent, int viewType, int size) {
        final View view = new SearchEditText(parent.getContext());


        return new SearchHolder(view);
    }

    public void onBindDayHolder(final SearchAdapter adapter,FlashTableView flashTableView, final SearchHolder holder, final int position , Column column) {


        holder.bind(adapter,flashTableView,column,position);



    }
}
