package com.eymel.datatable.adapter.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.eymel.datatable.R;
import com.eymel.datatable.action.search.SearchModel;
import com.eymel.datatable.adapter.SearchAdapter;
import com.eymel.datatable.model.Column;
import com.eymel.datatable.view.FlashTableView;
import com.eymel.datatable.view.customviews.SearchEditText;

public class SearchHolder extends RecyclerView.ViewHolder {
    SearchEditText view;


    public SearchHolder(View itemView) {
        super(itemView);
        view = (SearchEditText) itemView;
    }

    public void bind(SearchAdapter adapter, FlashTableView flashTableView, Column column, int position) {
        int width = flashTableView.getDefaultWidth();
        if (width==0)
           width = (adapter.getTableWidth() - flashTableView.calculateSizedColumnWidth()) / flashTableView.calculateNotSizedColumn();
        if (column.getColumnWidth() != 0)
            view.setLayoutParams(new RecyclerView.LayoutParams(column.getColumnWidth(), ViewGroup.LayoutParams.MATCH_PARENT));
        else {

            view.setLayoutParams(new RecyclerView.LayoutParams(width, ViewGroup.LayoutParams.MATCH_PARENT));
        }


        if (adapter.getItemCount() != position + 1)
            view.setSearchCompenentBackgroundRes(view.getResources().getDrawable(R.drawable.cell_right_line));

        if (column.isCanSearchable()) {
            view.setSearchText("Ara");
        } else {
            view.hideSearchItems();
        }

        SearchModel searchModel = new SearchModel();
        searchModel.setColumnIndex(column.getId());
        searchModel.setSearchEditText(view);
        adapter.addSearchItem(searchModel);
    }
}
