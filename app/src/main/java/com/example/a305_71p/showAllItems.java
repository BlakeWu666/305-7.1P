package com.example.a305_71p;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.a305_71p.sqliteHelper.DataBaseHelper;
import com.example.a305_71p.sqliteHelper.itemModel;

import java.util.List;

public class showAllItems extends AppCompatActivity {
    ListView itemsList;
    DataBaseHelper dataBaseHelper;

    ArrayAdapter aa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_all_items);

        itemsList = findViewById(R.id.itemsList);

        // display all the data in the table and choose some data to delete

        //create a new databaseHelper instance for this activity and also we need to get the information in the list
        dataBaseHelper = new DataBaseHelper(showAllItems.this);

        //Print out the data with arrayAdapter
        // context, format, values of the list
        showItemsOnListView(dataBaseHelper);

        //If somebody click on an item, we delete the item using setOnItemClickListener
        itemsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Create a new instance for the item that has been clicked
                //Since the getItemAtPosition returns an integer, but the data type of clickedItem is itemModel, so we need to cast the data type
                itemModel clickedItem = (itemModel) parent.getItemAtPosition(position);

                //We use dataBaseHelper to delete the item that has been clicked
                dataBaseHelper.deleteItem(clickedItem);

                //After we deleting the item, we need to refresh the list
                showItemsOnListView(dataBaseHelper);

                //Use toast to show who has been deleted
                Toast.makeText(showAllItems.this, clickedItem.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showItemsOnListView(DataBaseHelper dbH) {
        aa = new ArrayAdapter<itemModel>(showAllItems.this,android.R.layout.simple_dropdown_item_1line, dbH.getAllItems());
        itemsList.setAdapter(aa);// set adapter for the ListView
    }
}