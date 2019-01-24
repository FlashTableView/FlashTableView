package com.eymel.datatable.adapter;


import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.eymel.datatable.R;
import com.eymel.datatable.action.selection.OnItemSelectedListener;
import com.eymel.datatable.action.selection.OnRowClickListener;
import com.eymel.datatable.action.selection.SelectedState;
import com.eymel.datatable.listeners.OnRowSizeChangeListener;
import com.eymel.datatable.model.Cell;
import com.eymel.datatable.model.Column;
import com.eymel.datatable.model.Row;
import com.eymel.datatable.model.ViewType;
import com.eymel.datatable.view.FlashTableView;
import com.eymel.datatable.view.customviews.CellView;
import com.eymel.datatable.view.customviews.RowView;

import java.util.ArrayList;
import java.util.List;

public class TableAdapter extends RecyclerView.Adapter<TableAdapter.RowViewHolder> implements OnRowSizeChangeListener, OnItemSelectedListener, OnRowClickListener {
    private List<Row> rows;
    private FlashTableView flashTableView;
    private ViewGroup.LayoutParams layoutParams;


    public TableAdapter(Context context, FlashTableView flashTableView, List<Row> rows, List<Column> columns) {
        Context context1 = context;
        this.flashTableView = flashTableView;
        this.rows = rows;
        List<Column> columns1 = columns;

    }

    @NonNull
    @Override
    public RowViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RowView rowView = new RowView(parent.getContext());

        return new RowViewHolder(rowView);
    }


    @Override
    public void onBindViewHolder(@NonNull RowViewHolder holder, int position) {
        Row row = rows.get(position);
        Resources res = holder.itemView.getContext().getResources();

       List<Row> selectedRows = flashTableView.getSelectedRow();
       for (Row rowSelected : selectedRows
               ) {
           if (rowSelected.getRowId() == row.getRowId())
               row.setSelectedState(rowSelected.getSelectedState());
       }
       if (row.getBackgroundResource() == 0) {
           if (row.getSelectedState() == SelectedState.NONE) {
               if (position % 2 == 0)
                   holder.itemView.setBackgroundResource(R.drawable.default_row_background);
               else
                   holder.itemView.setBackgroundResource(R.drawable.default_row_background_secondly);
           } else {
               onItemSelected(row);
               holder.itemView.setBackgroundResource(flashTableView.getSelectedRowBackgroundResource());
           }
       } else {
           holder.itemView.setBackgroundResource(row.getBackgroundResource());

       }
       holder.linearLayout.removeAllViews();
        int counter = 0;

        int width = flashTableView.getDefaultColumnWidth();
        for (Cell cell : row.getCells()) {
            if (cell.getViewType() == ViewType.TEXTVIEW) {

                CellView cellView = new CellView(holder.itemView.getContext());
                cellView.setTextCell(cell.getText());
                cellView.setTextColor(Color.BLACK);


                counter++;
                if ((row.getCells().size() > counter))
                    cellView.setCellCompenentBackgroundRes(res.getDrawable(R.drawable.cell_right_line));

                if (cell.getColumn().getColumnWidth() != 0)
                    cellView.setLayoutParams(new LinearLayout.LayoutParams(cell.getColumn().getColumnWidth(), ViewGroup.LayoutParams.MATCH_PARENT, 1));
                else {

                    cellView.setLayoutParams(new LinearLayout.LayoutParams(width, ViewGroup.LayoutParams.MATCH_PARENT, 1));
                }

                holder.linearLayout.addView(cellView);
            } else if (cell.getViewType() == ViewType.CHECKBOX) {
                CheckBox checkBox = new CheckBox(holder.itemView.getContext());
                checkBox.setTag(row.getRowId());
                checkBox.setGravity(Gravity.CENTER);
                if (cell.getText().equals("true")) {
                    checkBox.setChecked(true);
                } else {
                    checkBox.setChecked(false);
                }
                checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked) {
                            cell.setText("true");
                        } else {
                            cell.setText("false");
                        }
                        cell.getColumn().getOnCheckedChangeListener().onCheckedChanged(buttonView, isChecked);
                    }
                });
                counter++;
                if ((row.getCells().size() > counter))
                    checkBox.setBackground(res.getDrawable(R.drawable.cell_right_line));

                if (cell.getColumn().getColumnWidth() != 0)
                    checkBox.setLayoutParams(new LinearLayout.LayoutParams(cell.getColumn().getColumnWidth(), ViewGroup.LayoutParams.MATCH_PARENT, 1));
                else {

                    checkBox.setLayoutParams(new LinearLayout.LayoutParams(width, ViewGroup.LayoutParams.MATCH_PARENT, 1));
                }
                holder.linearLayout.addView(checkBox);
            } else if (cell.getViewType() == ViewType.BUTTON) {
                Button button = new Button(holder.itemView.getContext());
                button.setTag(row.getRowId());
                button.setGravity(Gravity.CENTER);
                button.setText(cell.getText());
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        cell.getColumn().getOnClickListener().onClick(v);
                    }
                });


                counter++;
                if ((row.getCells().size() > counter))
                    button.setBackground(res.getDrawable(R.drawable.cell_right_line));

                if (cell.getColumn().getColumnWidth() != 0)
                    button.setLayoutParams(new LinearLayout.LayoutParams(cell.getColumn().getColumnWidth(), ViewGroup.LayoutParams.MATCH_PARENT, 1));
                else {

                    button.setLayoutParams(new LinearLayout.LayoutParams(width, ViewGroup.LayoutParams.MATCH_PARENT, 1));
                }
                holder.linearLayout.addView(button);
            }


        }
        if (flashTableView.isCanSelectMultipleItem()) {
            holder.itemView.setOnClickListener(v -> {
                if (row.getSelectedState() == SelectedState.NONE) {

                    onItemSelected(row);
                    row.setSelectedState(SelectedState.SELECTED);
                    notifyItemChanged(position);


                } else {


                    onItemCancelSelect(row);
                    row.setSelectedState(SelectedState.NONE);
                    notifyItemChanged(position);


                }
            });
        } else {
            holder.itemView.setOnClickListener(v -> onRowClick(rows.get(position)));
        }


    }

    @Override
    public int getItemCount() {
        return rows.size();
    }

    public Row getItem(int position) {
        return rows.get(position);
    }

    @Override
    public void onRowSizeChanged(int size) {
        flashTableView.onRowSizeChanged(size);
    }

    @Override
    public void onItemSelected(Row row) {
        flashTableView.onItemSelected(row);
    }

    @Override
    public void onItemCancelSelect(Row row) {
        flashTableView.onItemCancelSelect(row);
    }

    @Override
    public void onRowClick(Row row) {
        flashTableView.onRowClick(row);
    }

    public class RowViewHolder extends RecyclerView.ViewHolder {
        LinearLayout linearLayout;

        private RowViewHolder(View convertView) {
            super(convertView);
            linearLayout = convertView.findViewById(R.id.cell_container);

        }

    }

    public static class TableAdapterBuilder {
        private Context context;
        private FlashTableView flashTableView;
        private List<Row> rows;
        private List<Column> columns;

        public TableAdapter createTableAdapter() {
            return new TableAdapter(context, flashTableView, rows, columns);
        }

        public TableAdapterBuilder setContext(Context context) {
            this.context = context;
            return this;
        }

        public TableAdapterBuilder setRows(List<Row> rows) {
            this.rows = rows;
            return this;
        }

        public TableAdapterBuilder setFlashTableView(FlashTableView flashTableView) {
            this.flashTableView = flashTableView;
            return this;
        }

        public TableAdapterBuilder setColumns(List<Column> columns) {
            this.columns = columns;
            return this;
        }
    }

    public void UpdateList(List<Row> rowList, int totalCount) {
        rows = new ArrayList<>();
        rows = cloneList(rowList);
        notifyDataSetChanged();
        onRowSizeChanged(totalCount);

    }

    public void addItemList(List<Row> rowList) {
        rows.addAll(rowList);
        notifyDataSetChanged();
    }

    public static List<Row> cloneList(final List<Row> list) {
        final ArrayList<Row> result = new ArrayList<Row>(list.size());
        for (final Row entry : list) {
            result.add(entry.clone());
        }
        return result;
    }

}



