package com.example.a305_71p.sqliteHelper;

import android.content.ClipData;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.jar.Attributes;

public class DataBaseHelper extends SQLiteOpenHelper {

    public DataBaseHelper(@Nullable Context context) {
        super(context, Util.DATABASE_NAME, null, Util.DATABASE_VERSION);
    }

    //Create a table with specified format
    @Override
    public void onCreate(@NonNull SQLiteDatabase db) {
        String CREATE_TABLE_COMMAND = "CREATE TABLE "
                + Util.TABLE_NAME + " ("
                + Util.ITEM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + Util.NAME + " TEXT, "
                + Util.TYPE + " TEXT, "
                + Util.LOCATION + " TEXT, "
                + Util.DESCRIPTION + " TEXT, "
                + Util.DATE + " TEXT, "
                + Util.PHONE_NUMBER + " TEXT "
                + " ) ";
        db.execSQL(CREATE_TABLE_COMMAND);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    //Add an item to the table
    public boolean insertItem(@NonNull itemModel item){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(Util.NAME,item.getName());
        contentValues.put(Util.TYPE,item.getType());
        contentValues.put(Util.LOCATION,item.getLocation());
        contentValues.put(Util.DESCRIPTION,item.getDescription());
        contentValues.put(Util.DATE,item.getDate());
        contentValues.put(Util.PHONE_NUMBER,item.getPhoneNumber());

        long rowId = db.insert(Util.TABLE_NAME,null, contentValues);
        if (rowId == -1) return  false;
        else return  true;
    }

    //Read the details of each item from the TABLE_NAME
    public boolean getItems(String item_name, String item_type, String item_location, String item_description, String item_date, String item_phone_number){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query(Util.TABLE_NAME, new String[]{Util.ITEM_ID},
                Util.NAME + " =? and "
        + Util.TYPE + " =? and "
        + Util.LOCATION + " =? and "
        + Util.DESCRIPTION + " =? and "
        + Util.DATE + " =? and "
        + Util.PHONE_NUMBER + " =?",
                new String[]{item_name, item_type,item_location,item_description,item_date,item_phone_number},null,null,null);

        int numOfRows = cursor.getCount();

        if (numOfRows > 0 ) return true;
        else return false;
    }

    //Delete an item from TABLE_NAME, based on its ID
    public boolean deleteItem(@NonNull itemModel item){
        SQLiteDatabase db = this.getWritableDatabase();

        //For deletion, we use delete query in the SQL language, we delete an element in the array based on its id
        String DELETE_QUERY = "DELETE FROM " + Util.TABLE_NAME + " WHERE " + Util.ITEM_ID + " = " + item.getId();

        Cursor cursor = db.rawQuery(DELETE_QUERY,null);
        if (cursor.moveToFirst()) return  true; //If we can find the find cursor(result), we return true, otherwise we return false
        else return  false;
    }

    //Get all items from TABLE_NAME
    public List<itemModel> getAllItems(){
        List<itemModel> items = new ArrayList<>(); // create the items list
        SQLiteDatabase db = this.getReadableDatabase(); // get the db that we have permission to read the details in it

        String SELECT_QUERY = "SELECT * FROM " + Util.TABLE_NAME;
        Cursor cursor = db.rawQuery(SELECT_QUERY,null); // a cursor is like an array of items, it is the result of db rawQuery

        // moveToFirst will return true if there were some items that has been selected, it means return to the first result
        if (cursor.moveToFirst()){
            do {// use a loop to insert an object for each row of the table
                /*int idIndex = cursor.getColumnIndex(Util.ITEM_ID);
                int nameIndex = cursor.getColumnIndex(Util.NAME);
                int typeIndex = cursor.getColumnIndex(Util.TYPE);
                int locationIndex = cursor.getColumnIndex(Util.LOCATION);
                int descriptionIndex = cursor.getColumnIndex(Util.DESCRIPTION);
                int dateIndex = cursor.getColumnIndex(Util.DATE);
                int phoneNumberIndex = cursor.getColumnIndex(Util.PHONE_NUMBER);

                if (idIndex > 0  && nameIndex > 0 && typeIndex > 0 && locationIndex>0 && descriptionIndex>0 && dateIndex>0 && phoneNumberIndex>0)
                {
                    String name = cursor.getString(nameIndex);
                    String type = cursor.getString(typeIndex);
                    String location = cursor.getString(locationIndex);
                    String description = cursor.getString(descriptionIndex);
                    String date = cursor.getString(dateIndex);
                    int phoneNumber = cursor.getInt(phoneNumberIndex);

                    //itemModel item = new itemModel(id,phoneNumber,name,type,description,date,location);
                    //items.add(item);
                }*/

                // this is the format of each row
                int itemID = cursor.getInt(0);
                String name = cursor.getString(1);
                String type = cursor.getString(2);
                String description = cursor.getString(3);
                String date = cursor.getString(4);
                String location = cursor.getString(5);
                String phoneNumber = cursor.getString(6);

                itemModel newItem = new itemModel(itemID,name,type,description,date,location,phoneNumber); // create new instances of the items
                items.add(newItem); // add items to the list
            } while (cursor.moveToNext()); // After we finish the first result. we go the next cursor(result)
        }
        else{

        }

        // After we using the db and the cursor,its better to close them
        cursor.close();
        db.close();

        // return the list
        return  items;
    }

}
