package com.eymel.datatable.view.customviews;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.eymel.datatable.R;
import com.eymel.datatable.action.sort.SortingOrder;

import static android.view.Gravity.CENTER_VERTICAL;

public class HeaderColumnView extends LinearLayout {
    private TextView textHeader;
    private ImageView imgDown, imgUp;

    public HeaderColumnView(Context context) {
        super(context);
        init();
    }


    public HeaderColumnView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public HeaderColumnView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void init() {
        inflate(getContext(), R.layout.item_header, this);
        textHeader = findViewById(R.id.txt_header_column);
        imgDown = findViewById(R.id.imgdown);
        imgUp = findViewById(R.id.img_up);
        setOrientation(VERTICAL);
        textHeader.setGravity(CENTER_VERTICAL);


    }

    public void setTextHeader(String string) {
        textHeader.setText(string);
    }

    public void setTextColor(int color) {
        textHeader.setTextColor(color);
    }

    public void setSortIcon(SortingOrder sortingOrder) {

        switch (sortingOrder) {
            case ASCENDING:
                imgUp.setAlpha(1f);
                imgUp.setVisibility(VISIBLE);
                imgDown.setVisibility(INVISIBLE);
                break;
            case DESCENDING:
                imgDown.setAlpha(1f);
                imgDown.setVisibility(VISIBLE);
                imgUp.setVisibility(INVISIBLE);
                break;
            case NONE:
                imgUp.setVisibility(VISIBLE);
                imgDown.setVisibility(VISIBLE);
                imgUp.setAlpha(0.5f);
                imgDown.setAlpha(0.5f);
                break;
        }

    }

}
