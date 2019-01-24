package com.eymel.datatable.view.customviews;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.eymel.datatable.R;

import static android.view.Gravity.CENTER_VERTICAL;

public class SearchEditText extends LinearLayout {
    EditText editSearch;
    private LinearLayout llSearch, llSearchItem;

    public SearchEditText(Context context) {
        super(context);
        init();
    }

    public SearchEditText(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SearchEditText(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void init() {

        inflate(getContext(), R.layout.item_search, this);
        editSearch = findViewById(R.id.et_search);
        llSearch = findViewById(R.id.ll_search);
        llSearchItem = findViewById(R.id.ll_search_items);
        setOrientation(VERTICAL);
        editSearch.setGravity(CENTER_VERTICAL);
        setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 1));
        editSearch.setOnFocusChangeListener((v, hasFocus) -> {

            if (hasFocus) {
                editSearch.requestFocus();
                editSearch.post(() -> {
                    InputMethodManager imm = (InputMethodManager) getContext().getSystemService(
                            Context.INPUT_METHOD_SERVICE);
                    imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
                });

            } else {
                editSearch.post(() -> {
                    InputMethodManager imm = (InputMethodManager) getContext().getSystemService(
                            Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromInputMethod(editSearch.getWindowToken(), 0);
                });

            }
        });

    }

    public void setSearchText(String string) {
        editSearch.setHint(string);
        editSearch.setHintTextColor(Color.GRAY);
    }

    public EditText getEditText() {
        return editSearch;
    }

    public void setSearchCompenentBackgroundRes(Drawable backgroundRes) {
        llSearch.setBackground(backgroundRes);
    }

    public void hideSearchItems() {
        llSearchItem.setVisibility(INVISIBLE);
    }
}
