package patrick.notes;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * SavesActivity for selecting data fom database / deleting data / Restore data
 *
 * Created by Patrick on 24.08.2017.
 */

public class SavesActivity extends AppCompatActivity {

    DatabaseHelper mDatabaseHelper;
    private ListView LVSaves;

    /**
     * btnDelete delets the entety with the selected id from the listview
     * LVSave gets the id and the text from the selected row
     *
     * @param savedInstanceState;
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saves);
        Button btnBack = (Button) findViewById(R.id.btnBack);
        Button btnDelete = (Button) findViewById(R.id.btnDelete);
        mDatabaseHelper = new DatabaseHelper(this);
        LVSaves = (ListView) findViewById(R.id.listView);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SavesActivity.this, NotesActivity.class);
                startActivity(intent);
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDelete();
            }
        });

        LVSaves.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Background.text = (LVSaves.getItemAtPosition(position).toString().trim());
                mDatabaseHelper.getData3();
            }
        });

        populateListView();
    }

    /**
     * for showing all database enetys
     */
    private void populateListView() {
        Cursor data = mDatabaseHelper.getData();
        ArrayList<String> listData1 = new ArrayList<>();
        while (data.moveToNext()) {
            listData1.add(data.getString(1));
        }
        ListAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listData1);
        LVSaves.setAdapter(adapter);
    }

    /**
     * for deleting the selected row in the database
     */
    private void onDelete() {
        mDatabaseHelper.deleteDate();
        Background.ids = 0;
        populateListView();
    }
}
