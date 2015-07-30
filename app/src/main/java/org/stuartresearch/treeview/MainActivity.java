package org.stuartresearch.treeview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    public RecyclerView recyclerView;
    public String[] countries;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.main_recyclerview);

        ArrayList<TreeEntry> data = buildData();

        CardAdapter adapter = new CardAdapter(data);

        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public ArrayList<TreeEntry> buildData() {
        countries = getResources().getStringArray(R.array.countries_array);

        ArrayList<TreeEntry> list = new ArrayList<>(countries.length);

        TreeEntry parent = new TreeEntry(countries[0]);
        list.add(parent);

        for (int i = 1; i < countries.length; i++) {
            TreeEntry entry = new TreeEntry(countries[i]);

            if (i % 5 == 0)
                parent = null;

            entry.parent = parent;
            int numParents = incrementChildrenCounts(entry);
            entry.indent = numParents;

            list.add(entry);
            parent = entry;
        }

        return list;
    }

    public static int incrementChildrenCounts (TreeEntry entry) {
        int count = 0;
        TreeEntry cursor = entry;

        while (cursor.parent != null) {
            cursor = cursor.parent;
            cursor.children.add(entry);
            count++;
        }

        return count;
    }
}
