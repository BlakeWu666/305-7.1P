package com.example.a305_71p;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a305_71p.sqliteHelper.DataBaseHelper;
import com.example.a305_71p.sqliteHelper.Util;
import com.example.a305_71p.sqliteHelper.itemModel;
import com.google.android.datatransport.runtime.BuildConfig;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.net.PlacesClient;

public class    createNewAdvert extends AppCompatActivity { ;
    Button saveBtn, backBtn;
    TextView type, name, phone, description, location, date;
    EditText typeInput, nameInput, descriptionInput,locationInput,dateInput, phoneInput;
    DataBaseHelper dataBaseHelper;

    String getNameInput, getTypeInput, getDescriptionInput,getDateInput,getLocationInput,getPhoneNumberInput;
    itemModel item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_advert);

        type = findViewById(R.id.type);
        name = findViewById(R.id.name);
        phone = findViewById(R.id.phone);
        description = findViewById(R.id.description);
        location = findViewById(R.id.location);
        date = findViewById(R.id.date);
        typeInput = findViewById(R.id.typeInput);
        nameInput = findViewById(R.id.nameInput);
        descriptionInput = findViewById(R.id.descriptionInput);
        locationInput = findViewById(R.id.locationInput);
        dateInput = findViewById(R.id.dateInput);
        phoneInput = findViewById(R.id.phoneInput);

        saveBtn = findViewById(R.id.save);
        backBtn = findViewById(R.id.back);

        getNameInput = nameInput.getText().toString();
        getTypeInput = typeInput.getText().toString();
        getDescriptionInput = descriptionInput.getText().toString();
        getDateInput = dateInput.getText().toString();
        getLocationInput = locationInput.getText().toString();
        getPhoneNumberInput = phoneInput.getText().toString();

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent backToHome = new Intent(createNewAdvert.this,MainActivity.class);
                startActivity(backToHome);
            }
        });

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    item = new itemModel(1,getNameInput,getTypeInput,getDescriptionInput,getDateInput,getLocationInput,getPhoneNumberInput);
                    Toast.makeText(createNewAdvert.this, item.toString(), Toast.LENGTH_SHORT).show();
                }
                catch (Exception e){
                    Toast.makeText(createNewAdvert.this, "Error creating item", Toast.LENGTH_SHORT).show();
                    item = new itemModel(-1, "0", "0", "0", "0", "0", "0");
                }

                dataBaseHelper = new DataBaseHelper(createNewAdvert.this);
                boolean isItSuccessful = dataBaseHelper.insertItem(item);

                //Check the status of insertion
                Toast.makeText(createNewAdvert.this, "Success = " + isItSuccessful, Toast.LENGTH_SHORT).show();

            }
        });

    }
}