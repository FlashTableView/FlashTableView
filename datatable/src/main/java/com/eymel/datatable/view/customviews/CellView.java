package com.eymel.datatable.view.customviews;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.eymel.datatable.R;

import static android.view.Gravity.CENTER_VERTICAL;

public class CellView extends LinearLayout {
    TextView textCell;
    LinearLayout llCell ;

    public CellView(Context context) {
        super(context);
        init();
    }

    public CellView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public CellView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public void init() {
        inflate(getContext(), R.layout.item_cell, this);
        textCell = findViewById(R.id.txt_cell);
        llCell=findViewById(R.id.ll_cell);

        setOrientation(HORIZONTAL);
        textCell.setGravity(CENTER_VERTICAL);




    }

    public void setTextCell(String string) {
        textCell.setText(string);
    }

    public void setTextColor(int color) {
        textCell.setTextColor(color);
    }
public void setCellCompenentBackgroundRes(Drawable backgroundRes){
llCell.setBackground(backgroundRes);
}
}
