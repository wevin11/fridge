package com.example.fridgeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import java.util.Calendar;

public class MainActivity3 extends AppCompatActivity
{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        Spinner itemCategory = findViewById(R.id.categoryOptions);
        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.categoryOptionsList, R.layout.custom_spinner_layout);

        spinnerAdapter.setDropDownViewResource(R.layout.custom_dropdown_layout);

        itemCategory.setAdapter(spinnerAdapter);
        TextView itemNameTop = findViewById(R.id.itemNameTop);
        EditText itemName = findViewById(R.id.nameInput);
        EditText itemBrand = findViewById(R.id.brandInput);
        EditText itemMonth = findViewById(R.id.monthInput);
        EditText itemDay = findViewById(R.id.dayInput);
        EditText itemYear = findViewById(R.id.yearInput);
        Button saveButton = findViewById(R.id.saveButton);
        Button addToListButton = findViewById(R.id.addListButton);
        Button deleteButton = findViewById(R.id.deleteButton);

        Item item = getIntent().getParcelableExtra("item");

        itemNameTop.setText(item.getName());
        itemName.setText(item.getName());
        itemCategory.setSelection(item.getCategoryIndex());

        if(item.getBrand() != null)
        {
            itemBrand.setText(item.getBrand());
        }

        if(item.getCalDate() != null)
        {
            itemMonth.setText(String.valueOf(item.getMonth()));
            itemDay.setText(String.valueOf(item.getDay()));
            itemYear.setText(String.valueOf(item.getYear()));
        }


    }
}