## Flash Table View
[ ![Download](https://api.bintray.com/packages/flashtableview/maven/FlashTableView/images/download.svg) ](https://bintray.com/flashtableview/maven/FlashTableView/_latestVersion)


> Android Table View
## Table of Contents
1. [Quick Start](#quick-start)
1. [Examples](#examples)
1. [License](#license)


<h2 id="quick-start">Quick Start </h2>
Add the library to your Android project, then check out the examples below!

### Gradle Setup
```gradle

dependencies {
    implementation 'com.eymel.datatable:FlashTableView:1.0.1'
}
```
</br>

<h2 id="examples">Examples :eyes:</h2>

```java

    ArrayList<Column> headers = new ArrayList<>();
    FlashTableView tableView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tableView = findViewById(R.id.flashtableview);
        headers = new ArrayList<>();
        CreateHeader();
        setTable();
    }

    private void CreateHeader() {
        headers.add(new Column(0, "Baslık1", true, true));
        headers.add(new Column(1, "Baslık2", true, true));
        headers.add(new Column(2, "Baslık3", true, true));
        headers.add(new Column(3, "Baslık4", true, true));
        headers.add(new Column(4, "Baslık5", true, true));
    }

    private void setTable() {

        tableView.setCanSwipeRigth(true, "Düzenle");
        tableView.setCanSwipeLeft(false, "");
        tableView.setOnRowSwipeListener(new OnRowSwipeListener() {
            @Override
            public Row onRowSwipeLeft(Row row) {

                return row;
            }

            @Override
            public Row onRowSwipeRight(Row row) {
                return null;
            }
        });


        //region TableModel initialize
        List<Row> rows = new ArrayList<>();
        TableModel tableModel = new TableModel();
        for (int i = 0; i < 100; i++) {

            Row row = new Row();
            row.setRowId(i);
            List<Cell> cells = new ArrayList<>();
            cells.add(new Cell(headers.get(0), "başlık1 satır" + i));
            cells.add(new Cell(headers.get(1), "başlık2 satır" + i));
            cells.add(new Cell(headers.get(2), "başlık3 satır" + i));
            cells.add(new Cell(headers.get(3), "başlık4 satır" + i));
            cells.add(new Cell(headers.get(4), "başlık5 satır" + i));
            row.setCells(cells);
            rows.add(row);
        }


        tableModel.setHeaderColumns(headers);
        tableModel.setRows(rows);
        tableView.setTableModel(tableModel);
        //endregion


        tableView.setOnRowSizeChangeListener(new OnRowSizeChangeListener() {
            @Override
            public void onRowSizeChanged(int size) {

            }
        });

        tableView.show();


    }

```

</br>
<h2 id="license">License</h2>




    Copyright 2019 Eyüp Yıldırım.

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.



