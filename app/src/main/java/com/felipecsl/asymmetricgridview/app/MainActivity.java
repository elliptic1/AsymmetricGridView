package com.felipecsl.asymmetricgridview.app;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.*;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.felipecsl.asymmetricgridview.app.model.DemoItem;
import com.felipecsl.asymmetricgridview.app.widget.ListAdapter;
import com.felipecsl.asymmetricgridview.library.Utils;
import com.felipecsl.asymmetricgridview.library.widget.AsymmetricGridView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private static final String TAG = "MainActivity";
    private AsymmetricGridView listView;
    private ListAdapter adapter;
    private int currentOffset = 0;

    @Override
    @SuppressWarnings("unchecked")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = findViewById(R.id.listView);

        adapter = new ListAdapter(this, listView, new ArrayList<DemoItem>());
        adapter.appendItems(getMoreItems(50));

        listView.setRequestedColumnCount(3);
        listView.setAdapter(adapter);
        listView.setDebugging(true);

        listView.setOnItemClickListener(this);
    }

    private List<DemoItem> getMoreItems(int qty) {
        final List<DemoItem> items = new ArrayList<>();

        for (int i = 0; i < qty; i++) {
            int colSpan = Math.random() < 0.2f ? 2 : 1;
            // Swap the next 2 lines to have items with variable
            // column/row span.
            // int rowSpan = Math.random() < 0.2f ? 2 : 1;
            int rowSpan = colSpan;
            final DemoItem item = new DemoItem(colSpan, rowSpan, currentOffset + i);
            items.add(item);
        }

        currentOffset += qty;

        return items;
    }

    @Override
    protected void onSaveInstanceState(final Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("currentOffset", currentOffset);
    }

    @Override
    protected void onRestoreInstanceState(@NotNull final Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        currentOffset = savedInstanceState.getInt("currentOffset");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.one_column) {
            listView.setRequestedColumnCount(1);
            listView.determineColumns();
            listView.setAdapter(adapter);
        } else if (id == R.id.two_columnns) {
            listView.setRequestedColumnCount(2);
            listView.determineColumns();
            listView.setAdapter(adapter);
        } else if (id == R.id.three_columns) {
            listView.setRequestedColumnCount(3);
            listView.determineColumns();
            listView.setAdapter(adapter);
        } else if (id == R.id.four_columns) {
            listView.setRequestedColumnCount(4);
            listView.determineColumns();
            listView.setAdapter(adapter);
        } else if (id == R.id.five_columns) {
            listView.setRequestedColumnCount(5);
            listView.determineColumns();
            listView.setAdapter(adapter);
        } else if (id == R.id.onetwenty_dp_columns) {
            listView.setRequestedColumnWidth(Utils.dpToPx(this, 120));
            listView.determineColumns();
            listView.setAdapter(adapter);
        } else if (id == R.id.twoforty_dp_columns) {
            listView.setRequestedColumnWidth(Utils.dpToPx(this, 240));
            listView.determineColumns();
            listView.setAdapter(adapter);
        } else if (id == R.id.append_items) {
            adapter.appendItems(getMoreItems(50));
        } else if (id == R.id.reset_items) {
            currentOffset = 0;
            adapter.setItems(getMoreItems(50));
        } else if (id == R.id.reordering) {
            listView.setAllowReordering(!listView.isAllowReordering());
            item.setTitle(listView.isAllowReordering() ? "Prevent reordering" : "Allow reordering");
        } else if (id == R.id.debugging) {
            int index = listView.getFirstVisiblePosition();
            View v = listView.getChildAt(0);
            int top = (v == null) ? 0 : v.getTop();

            listView.setDebugging(!listView.isDebugging());
            item.setTitle(listView.isDebugging() ? "Disable debugging" : "Enable debugging");
            listView.setAdapter(adapter);

            if (Build.VERSION.SDK_INT >= 21) {
                listView.setSelectionFromTop(index, top);
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(final AdapterView<?> parent, final View view, final int position, final long id) {
        Toast.makeText(this, "Item " + position + " clicked", Toast.LENGTH_SHORT).show();
    }
}
