package com.eymel.datatable.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.eymel.datatable.R;
import com.eymel.datatable.action.search.ISearchable;
import com.eymel.datatable.action.search.OnSearchItemCreated;
import com.eymel.datatable.action.search.SearchManager;
import com.eymel.datatable.action.search.SearchModel;
import com.eymel.datatable.action.selection.BaseClickManager;
import com.eymel.datatable.action.selection.OnItemSelectedListener;
import com.eymel.datatable.action.selection.OnRowClickListener;
import com.eymel.datatable.action.selection.SelectedState;
import com.eymel.datatable.action.swipe.BaseSwipeManager;
import com.eymel.datatable.action.swipe.OnRowSwipeListener;
import com.eymel.datatable.adapter.SearchAdapter;
import com.eymel.datatable.adapter.TableAdapter;
import com.eymel.datatable.adapter.TableHeaderAdapter;
import com.eymel.datatable.listeners.OnRowSizeChangeListener;
import com.eymel.datatable.model.Column;
import com.eymel.datatable.model.Row;
import com.eymel.datatable.model.TableModel;
import com.eymel.datatable.settings.SettingsManager;
import com.eymel.datatable.settings.appearance.AppearanceInterface;
import com.eymel.datatable.util.IListManager;
import com.eymel.datatable.view.customviews.HeaderColumnView;
import com.eymel.datatable.view.customviews.HeaderView;
import com.eymel.datatable.view.customviews.RowView;
import com.eymel.datatable.view.customviews.SearchEditText;
import com.eymel.datatable.view.customviews.SearchView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class FlashTableView extends RelativeLayout implements OnRowSizeChangeListener, OnItemSelectedListener, OnRowClickListener, OnRowSwipeListener, AppearanceInterface, IListManager, ISearchable, OnSearchItemCreated {

    TextView txt_totalRow, txt_selectedRow;
    private List<Row> selectedRow;
    boolean canSwipeLeft = false;
    boolean canSwipeRigth = false;
    boolean isVisibleTableHeader = true;
    private boolean canSelectMultipleItem = false;

    //swipe text
    private String swipeLeftText = "";
    private String swipeRightText = "";


    // Action Listener Manager initialize
    private BaseClickManager baseClickManager;
    private BaseSwipeManager baseSwipeManager;
    private OnRowClickListener onRowClickListener;
    private OnRowSwipeListener onRowSwipeListener;
    private OnRowSizeChangeListener onRowSizeChangeListener;

    Timer etChangeTimer;
    //Recycler initialize
    private TableAdapter tableAdapter;
    private TableHeaderAdapter tableHeaderAdapter;
    private SearchAdapter searchAdapter;
    private TableRecyclerView rvTable;

    private HeaderView llTableHeader;
    private RelativeLayout llMain;
    private View bottomView;
    private RowView rowView;
    private LinearLayout notFoundView, llBottom;

    private SearchView ll_searchView;
    private SearchEditText searchEditText;
    LinearLayoutManager linearLayoutManager;
    RecyclerView.OnScrollListener listener;
    //Manager
    SearchManager searchManager;
    SettingsManager settingsManager;

    Paint p = new Paint();
    int pageNumber = 0;
    int pastVisiblesItems, visibleItemCount, totalItemCount;
    private List<List<Row>> listPagination;
    List<Row> rows = new ArrayList<>();
    private List<Row> currentList = new ArrayList<>();

    public FlashTableView(Context context) {
        super(context);

    }

    public FlashTableView(Context context, AttributeSet attrs) {
        super(context, attrs);
        handleAttributes(attrs, 0, 0);
    }

    public FlashTableView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        handleAttributes(attrs, defStyleAttr, 0);
    }

    private void handleAttributes(AttributeSet attrs, int defStyle, int defStyleRes) {
        settingsManager = new SettingsManager();
        final TypedArray typedArray = getContext().getTheme().obtainStyledAttributes(attrs, R.styleable.FlashTableView, defStyle, defStyleRes);
        try {
            handleAttributes(typedArray);
        } finally {
            typedArray.recycle();
        }

    }

    private void handleAttributes(TypedArray typedArray) {
        int mainBackgroundRes = typedArray.getResourceId(R.styleable.FlashTableView_mainBackgroundRes, R.drawable.default_main_background);
        int selectedRowBackgroundRes = typedArray.getResourceId(R.styleable.FlashTableView_selectedRowBackgroundRes, R.drawable.default_selected_row);
        int headerColumnTextColor = typedArray.getColor(R.styleable.FlashTableView_headerColumnTextColor, ContextCompat.getColor(getContext(), R.color.default_header_colums_text_color));
        int headerBackgroundRes = typedArray.getResourceId(R.styleable.FlashTableView_headerBackgroundRes, R.drawable.table_header_blue);
        int rowBackgroundRes = typedArray.getResourceId(R.styleable.FlashTableView_rowBackgroundRes, R.drawable.default_row_background);
        settingsManager.setHeaderBackgroundResource(headerBackgroundRes);
        settingsManager.setHeaderColumnTextColor(headerColumnTextColor);
        settingsManager.setRowBackgroundResource(rowBackgroundRes);
        settingsManager.setMainBackgroundResource(mainBackgroundRes);
        settingsManager.setSelectedRowBackgroundResource(selectedRowBackgroundRes);
    }

    private void init() {
        setSelectedRow(new ArrayList<>());
        rows = cloneList(settingsManager.getTableModel().getRows());
        setCurrentList(rows);
        setSearchManager();

        createHeaderView();
        createMainContent();
        createSearchView();
        createBottomView();

        createHeaderRV();
        setSearhRV();
        createRecylerView();


    }

    private void createBottomView() {
        llBottom = new LinearLayout(getContext());
        llBottom.setOrientation(LinearLayout.HORIZONTAL);
        llBottom.setId(View.generateViewId());
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        params.bottomMargin = 10;
        params.topMargin = 10;
        params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        llMain.addView(llBottom, params);

        llBottom.removeAllViews();
        createBottomCounterView();
        if (bottomView != null) {
            if (bottomView.getParent() != null) {
                ((ViewGroup)bottomView.getParent()).removeView(bottomView);
            }
            LinearLayout.LayoutParams pms = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT,1);


            llBottom.addView(bottomView, pms);
        }
    }

    private void createBottomCounterView() {

        View view = LayoutInflater.from(getContext()).inflate(R.layout.item_bottom_counter, null, false);
        txt_selectedRow = view.findViewById(R.id.txt_row_selected);

        if (canSelectMultipleItem == true) {
            txt_selectedRow.setVisibility(VISIBLE);
        }
        txt_totalRow = view.findViewById(R.id.txt_row_total);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT,2);
        params.gravity = Gravity.START|Gravity.CENTER;
        llBottom.addView(view, params);
    }

    public void setBottomView(View view) {

        this.bottomView = view;

    }

    private void createMainContent() {
        llMain = new RelativeLayout(getContext());
        llMain.setId(View.generateViewId());
        llMain.setBackgroundResource(settingsManager.getMainBackgroundResource());
        addView(llMain);

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        params.addRule(RelativeLayout.BELOW, llTableHeader.getId());

        llMain.setLayoutParams(params);

    }

    private void createSearchView() {
        boolean isTitleAlreadyAdded = ll_searchView != null;
        if (!isTitleAlreadyAdded) {
            ll_searchView = new SearchView(getContext());
            ll_searchView.setId(View.generateViewId());
            ll_searchView.setOrientation(LinearLayout.VERTICAL);
            ll_searchView.setVerticalGravity(Gravity.CENTER_VERTICAL);
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, 70);
            params.topMargin = 10;
            llMain.addView(ll_searchView, params);
        } else {
            ll_searchView.removeAllViews();
        }
    }

    private void createHeaderView() {
        boolean isTitleAlreadyAdded = llTableHeader != null;
        if (!isTitleAlreadyAdded) {
            llTableHeader = new HeaderView(getContext());
            llTableHeader.setId(View.generateViewId());
            llTableHeader.setOrientation(LinearLayout.VERTICAL);
            llTableHeader.setVerticalGravity(Gravity.CENTER_VERTICAL);
            llTableHeader.setBackgroundResource(settingsManager.getHeaderBackgroundResource());
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, 120);
            addView(llTableHeader, params);
            if (!isVisibleTableHeader) {
                llTableHeader.setVisibility(GONE);
            }

        } else {
            llTableHeader.removeAllViews();
        }
    }

    private void setNotFoundView() {
        notFoundView = (LinearLayout) LayoutInflater.from(getContext()).inflate(R.layout.not_found_data_view, null);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        params.topMargin = 10;
        params.bottomMargin = 2;
        params.addRule(RelativeLayout.BELOW, ll_searchView.getId());
        notFoundView.setLayoutParams(params);
        notFoundView.setVisibility(GONE);
        llMain.addView(notFoundView, params);


    }

    private void setProgressbarView() {
        LinearLayout progressbar = (LinearLayout) LayoutInflater.from(getContext()).inflate(R.layout.item_progress_bar, null);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        params.addRule(RelativeLayout.BELOW, ll_searchView.getId());
        params.topMargin = 20;
        progressbar.setLayoutParams(params);
        progressbar.setVisibility(GONE);
        addView(progressbar);

    }


    public static List<Row> cloneList(final List<Row> list) {
        final ArrayList<Row> result = new ArrayList<Row>(list.size());
        for (final Row entry : list) {
            result.add(entry.clone());
        }
        return result;
    }

    private void setSearchManager() {
        searchManager = new SearchManager(this);

    }

    private void setSearhRV() {
        SearchRecylerView rv_searh = new SearchRecylerView(getContext());
        rv_searh.setId(View.generateViewId());
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 1);

        ll_searchView.addView(rv_searh, params);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);


        rv_searh.setLayoutManager(linearLayoutManager);
        searchAdapter = createSearchTableAdapter();

        rv_searh.setAdapter(searchAdapter);


    }

    private void setSearchItem() {
        List<SearchModel> searchModels = searchAdapter.getAllSearchModel();

        for (SearchModel searchModel : searchModels) {

            searchModel.getSearchEditText().getEditText().addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                    if (etChangeTimer != null) {
                        etChangeTimer.cancel();
                    }

                    etChangeTimer = new Timer();
                    etChangeTimer.schedule(new TimerTask() {
                        @Override
                        public void run() {

                            FlashTableView.this.post(() -> onEditTextChange(rows, searchModels));


                        }
                    }, 1500);
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });


        }
    }

    private SearchAdapter createSearchTableAdapter() {
        return new SearchAdapter.SearchTableBuilder().setContext(getContext())
                .setFlashTableView(this)
                .setColumns(settingsManager.getTableModel().getHeaderColumns())
                .createTableSearchAdapter();
    }


    private void createRecylerView() {
        rvTable = new TableRecyclerView(getContext());
        rvTable.setId(View.generateViewId());
        llMain.addView(rvTable);

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        params.topMargin = 10;
        params.addRule(RelativeLayout.BELOW, ll_searchView.getId());
        params.addRule(RelativeLayout.ABOVE, llBottom.getId());
        linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        rvTable.setLayoutParams(params);
        rvTable.setLayoutManager(linearLayoutManager);
        tableAdapter = createTableAdapter();
        rvTable.setAdapter(tableAdapter);

        txt_totalRow.setText("Toplam Kayıt : " + rows.size());
        UpdateList(rows);

        if (getOnRowSwipeListener() != null && (canSwipeRigth || canSwipeLeft))
            initSwipeTable(canSwipeLeft, canSwipeRigth);


    }

    public void setCanSwipeLeft(boolean canSwipeLeft, String text) {
        this.canSwipeLeft = canSwipeLeft;
        this.swipeLeftText = text;

    }

    public void setCanSwipeRigth(boolean canSwipeRigth, String text) {
        this.canSwipeRigth = canSwipeRigth;
        this.swipeRightText = text;

    }

    private void initSwipeTable(boolean canSwipeLeft, boolean canSwipeRigth) {
        int swipeInit = 0;
        if (canSwipeLeft && canSwipeRigth)
            swipeInit = ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT;
        else if (canSwipeLeft)
            swipeInit = ItemTouchHelper.LEFT;
        else if (canSwipeRigth)
            swipeInit = ItemTouchHelper.RIGHT;

        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, swipeInit) {

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();

                Row result;
                if (direction == ItemTouchHelper.RIGHT) {
                    result = onRowSwipeRight(tableAdapter.getItem(position));
                } else {
                    result = onRowSwipeLeft(tableAdapter.getItem(position));

                }

                if (result != null) {
                    for (Row row : currentList) {
                        if (result.getRowId() == row.getRowId()) {
                            row.setBackgroundResource(result.getBackgroundResource());
                            tableAdapter.notifyDataSetChanged();
                            break;
                        }
                    }
                } else {
                    tableAdapter.notifyItemChanged(position);
                }

            }

            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {

                Bitmap icon;
                if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {

                    View itemView = viewHolder.itemView;
                    float height = (float) itemView.getBottom() - (float) itemView.getTop();
                    float width = height / 3;

                    if (dX > 0) {

                        p.setColor(Color.parseColor("#00a84f"));
                        RectF background = new RectF((float) itemView.getLeft(), (float) itemView.getTop(), dX, (float) itemView.getBottom());
                        c.drawRect(background, p);
                        RectF text = new RectF((float) itemView.getLeft(), (float) itemView.getTop(), dX, (float) itemView.getBottom());

                        drawText(swipeRightText, c, text, p, itemView);


                    } else {
                        p.setColor(Color.parseColor("#c3c3c3"));
                        RectF background = new RectF(dX + itemView.getRight(), (float) itemView.getTop(), (float) itemView.getRight(), (float) itemView.getBottom());
                        c.drawRect(background, p);
                        RectF text = new RectF(dX + itemView.getRight(), (float) itemView.getTop(), (float) itemView.getRight(), (float) itemView.getBottom());
                        drawLeftText(swipeLeftText, c, text, p, itemView);

                    }
                }
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);

            }

            @Override
            public int getSwipeDirs(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                final int position = viewHolder.getAdapterPosition();

                Row row = tableAdapter.getItem(position);

                int swipeInit = 0;
                if (row.isCanSwipeLeft() && row.isCanSwipeRight())
                    return ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT;
                else if (row.isCanSwipeLeft())
                    return ItemTouchHelper.LEFT;
                else if (row.isCanSwipeRight())
                    return ItemTouchHelper.RIGHT;
                else {
                    return 0;
                }


            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);

        itemTouchHelper.attachToRecyclerView(rvTable);
    }

    private void drawText(String text, Canvas c, RectF button, Paint p, View itemView) {
        float textSize = 40;
        p.setColor(Color.BLACK);
        p.setAntiAlias(true);
        p.setTextSize(textSize);

        float textWidth = p.measureText(text);
        button.left = itemView.getLeft() - textWidth;
        c.drawText(text, button.centerX() - (textWidth / 2), button.centerY() + (textSize / 2), p);
    }

    private void drawLeftText(String text, Canvas c, RectF button, Paint p, View itemview) {
        float textSize = 40;
        p.setColor(Color.BLACK);
        p.setAntiAlias(true);
        p.setTextSize(textSize);

        float textWidth = p.measureText(text);
        button.right = itemview.getRight() + textWidth;
        c.drawText(text, button.centerX() - (textWidth / 2), button.centerY() + (textSize / 2), p);
    }


    private TableAdapter createTableAdapter() {
        return new TableAdapter.TableAdapterBuilder().setContext(getContext())
                .setFlashTableView(FlashTableView.this).setColumns(settingsManager.getTableModel().getHeaderColumns())
                .createTableAdapter();
    }


    public int getDefaultColumnWidth() {
        return (getMeasuredWidth() - calculateSizedColumnWidth()) / calculateNotSizedColumn();
    }

    public int calculateNotSizedColumn() {
        int counter = 0;
        for (Column column : settingsManager.getTableModel().getHeaderColumns()
                ) {
            if (column.getColumnWidth() == 0)
                counter++;

        }
        return counter;
    }

    public int calculateSizedColumnWidth() {
        int counter = 0;
        for (Column column : settingsManager.getTableModel().getHeaderColumns()
                ) {
            if (column.getColumnWidth() != 0)
                counter += column.getColumnWidth();

        }
        return counter;
    }

    private void createHeaderRV() {

        //Views
        HeaderRecylerView rv_header = new HeaderRecylerView(getContext());
        rv_header.setId(View.generateViewId());
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        llTableHeader.addView(rv_header, params);

        LinearLayoutManager linearHeader = new LinearLayoutManager(getContext());
        linearHeader.setOrientation(LinearLayoutManager.HORIZONTAL);
        rv_header.setLayoutManager(linearHeader);

        tableHeaderAdapter = createTableHeaderAdapter();
        rv_header.setAdapter(tableHeaderAdapter);


    }

    private TableHeaderAdapter createTableHeaderAdapter() {
        return new TableHeaderAdapter.TableHeaderAdapterBuilder().setContext(getContext())
                .setFlashTableView(FlashTableView.this)
                .setColumns(settingsManager.getTableModel().getHeaderColumns())
                .createTableHeaderAdapter();
    }

    public void UpdateList(List<Row> rows) {
        tableHeaderAdapter.notifyDataSetChanged();
        if (listener != null)
            rvTable.removeOnScrollListener(listener);
        boolean isNotFoundAdded = notFoundView == null;
        if (isNotFoundAdded) {
            setNotFoundView();
        }
        if (rows.size() > 0) {
            rvTable.setVisibility(VISIBLE);
            notFoundView.setVisibility(GONE);

        } else {
            rvTable.setVisibility(GONE);
            notFoundView.setVisibility(VISIBLE);
        }
        setPagination(rows);

    }

    private void setPagination(List<Row> rows) {

        listener = new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0) //check for scroll down
                {
                    visibleItemCount = linearLayoutManager.getChildCount();
                    totalItemCount = linearLayoutManager.getItemCount();
                    pastVisiblesItems = linearLayoutManager.findFirstVisibleItemPosition();


                    if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {

                        if (rows.size() > 20) {
                            if (rows.size() > totalItemCount) {
                                pageNumber++;
                                tableAdapter.addItemList(listPagination.get(pageNumber));

                            }
                        }
                    }

                }
            }
        };


        if (rows.size() > 20)

        {
            listPagination = getPages(rows, rows.size() / (rows.size() / 20));
            tableAdapter.UpdateList(listPagination.get(0), rows.size());

        } else {
            listPagination = getPages(rows, 1);
            tableAdapter.UpdateList(rows, rows.size());


        }


        rvTable.addOnScrollListener(listener);
    }

    public static <T> List<List<T>> getPages(Collection<T> c, Integer pageSize) {
        if (c == null)
            return Collections.emptyList();
        List<T> list = new ArrayList<T>(c);
        if (pageSize == null || pageSize <= 0 || pageSize > list.size())
            pageSize = list.size();
        int numPages = (int) Math.ceil((double) list.size() / (double) pageSize);
        List<List<T>> pages = new ArrayList<List<T>>(numPages);
        for (int pageNum = 0; pageNum < numPages; )
            pages.add(list.subList(pageNum * pageSize, Math.min(++pageNum * pageSize, list.size())));
        return pages;
    }

    public void setTableHeaderVisibility(boolean tableHeaderVisibility) {
        isVisibleTableHeader = tableHeaderVisibility;
    }

    @Override
    public void onRowClick(Row row) {
        if (this.onRowClickListener != null)
            this.onRowClickListener.onRowClick(row);
    }

    @Override
    public Row onRowSwipeLeft(Row row) {
        return this.getOnRowSwipeListener().onRowSwipeLeft(row);
    }

    @Override
    public Row onRowSwipeRight(Row row) {
        return this.getOnRowSwipeListener().onRowSwipeRight(row);
    }


    public List<Row> getRowList() {
        return rows;

    }

    @Override
    public int getSelectedRowBackgroundResource() {
        return settingsManager.getSelectedRowBackgroundResource();
    }

    @Override
    public int getRowBackgroundResource() {
        return settingsManager.getRowBackgroundResource();
    }

    @Override
    public int getHeaderColumnTextColor() {
        return settingsManager.getHeaderColumnTextColor();
    }

    @Override
    public int getHeaderBackgroundResource() {
        return settingsManager.getHeaderBackgroundResource();
    }

    @Override
    public int getMainBackgroundResource() {
        return settingsManager.getMainBackgroundResource();
    }

    @Override
    public void setSelectedRowBackgroundResource(int selectedRowBackgroundResource) {
        settingsManager.setSelectedRowBackgroundResource(selectedRowBackgroundResource);
    }

    @Override
    public void setMainBackgroundResource(int mainBackgroundResource) {
        settingsManager.setRowBackgroundResource(mainBackgroundResource);
    }

    @Override
    public void setRowBackgroundResource(int rowBackgroundResource) {
        settingsManager.setRowBackgroundResource(rowBackgroundResource);
    }

    @Override
    public void setHeaderColumnTextColor(int headerColumnTextColor) {
        settingsManager.setHeaderColumnTextColor(headerColumnTextColor);
    }

    @Override
    public void setHeaderBackgroundResource(int headerBackgroundResource) {
        settingsManager.setHeaderBackgroundResource(headerBackgroundResource);
    }


    public void show() {
        init();

    }

    private int defaultWidth = 0;

    public void setDefaultWidth(int defaultWidth) {
        this.defaultWidth = defaultWidth;
    }

    public int getDefaultWidth() {
        return defaultWidth;
    }

    @Override
    public TableModel getTableModel() {
        return settingsManager.getTableModel();
    }

    @Override
    public void setTableModel(TableModel tableModel) {
        settingsManager.setTableModel(tableModel);
    }


    @Override
    public void onbindSearchItem() {
        setSearchItem();
    }

    @Override
    public void onEditTextChange(List<Row> rows, List<SearchModel> searchModels) {
        searchManager.onEditTextChange(rows, searchModels);
    }

    public List<Row> getCurrentList() {
        return currentList;
    }

    public void setCurrentList(List<Row> currentList) {
        this.currentList = currentList;
    }

    public OnRowSwipeListener getOnRowSwipeListener() {
        return onRowSwipeListener;
    }

    public void setOnRowSwipeListener(OnRowSwipeListener onRowSwipeListener) {
        this.onRowSwipeListener = onRowSwipeListener;


    }

    public void setOnRowClickListener(OnRowClickListener onRowClickListener) {
        this.onRowClickListener = onRowClickListener;
    }


    @Override
    public void onRowSizeChanged(int size) {
        this.getOnRowSizeChangeListener().onRowSizeChanged(size);
        txt_totalRow.setText("Toplam Kayıt : " + size);
    }

    public OnRowSizeChangeListener getOnRowSizeChangeListener() {
        return onRowSizeChangeListener;
    }

    public void setOnRowSizeChangeListener(OnRowSizeChangeListener onRowSizeChangeListener) {
        this.onRowSizeChangeListener = onRowSizeChangeListener;
    }

    public HeaderColumnView getHeaderColumnView() {
        HeaderColumnView headerColumnView = new HeaderColumnView(getContext());
        headerColumnView.setTextColor(getHeaderColumnTextColor());

        return headerColumnView;
    }

    public void setHeaderColumnView(HeaderColumnView headerColumnView) {
        HeaderColumnView headerColumnView1 = headerColumnView;
    }

    @Override
    public void onItemSelected(Row row) {
        Boolean isHave = false;
        for (Row item : selectedRow) {
            if (item.getRowId() == row.getRowId()) {
                isHave = true;
                break;
            }

        }
        if (!isHave) {
            this.selectedRow.add(row);
        }
        txt_selectedRow.setText("Seçilen : " + getSelectedRow().size());
    }

    @Override
    public void onItemCancelSelect(Row row) {

        for (Row item : selectedRow) {
            if (item.getRowId() == row.getRowId()) {

                selectedRow.remove(item);
                break;
            }

        }
        txt_selectedRow.setText("Seçilen : " + getSelectedRow().size());
    }

    public void SelectAllRow() {
        selectedRow.clear();
        for (Row row : rows
                ) {
            row.setSelectedState(SelectedState.SELECTED);
            selectedRow.add(row);
        }

        UpdateList(rows);
        txt_selectedRow.setText("Seçilen : " + getSelectedRow().size());


    }

    public void DeSelectAllRow() {
        selectedRow.clear();
        for (Row row : rows
                ) {
            row.setSelectedState(SelectedState.NONE);
        }

        UpdateList(rows);
        txt_selectedRow.setText("Seçilen : " + getSelectedRow().size());

    }

    public boolean isCanSelectMultipleItem() {
        return canSelectMultipleItem;
    }

    public void setCanSelectMultipleItem(boolean canSelectMultipleItem) {
        this.canSelectMultipleItem = canSelectMultipleItem;
    }

    public List<Row> getSelectedRow() {
        return selectedRow;
    }

    public void setSelectedRow(List<Row> selectedRow) {
        this.selectedRow = selectedRow;
    }
}
