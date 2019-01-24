package com.eymel.datatable.view.customviews;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.eymel.datatable.R;

public class RowView extends LinearLayout {
    public RowView(Context context) {
        super(context);
        init();
    }

    public RowView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RowView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    public void init() {
        inflate(getContext(), R.layout.item_row, this);
        setOrientation(HORIZONTAL);

        LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 150);


        setLayoutParams(layoutParams);

    }


}
