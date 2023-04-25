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
        int itemPosition = getIntent().getIntExtra("pos", -1);

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
        else
        {
            itemMonth.setText(null);
            itemDay.setText(null);
            itemYear.setText(null);
        }

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                item.setName(itemName.getText().toString());

                if(!itemBrand.getText().toString().isEmpty())
                {
                    item.setBrand(itemBrand.getText().toString());
                }

                if(checkDate(itemMonth, itemDay, itemYear, Integer.parseInt(itemMonth.getText().toString()),Integer.parseInt(itemDay.getText().toString())))
                {
                  item.setDate(Integer.parseInt(itemMonth.getText().toString()), Integer.parseInt(itemDay.getText().toString()), Integer.parseInt(itemYear.getText().toString()));
                }

                item.setCategory(itemCategory.getSelectedItem().toString());


                Intent returnIntent = new Intent();
                returnIntent.putExtra("updatedItem", item);
                returnIntent.putExtra("itemPosition", itemPosition);
                returnIntent.putExtra("deleteButtonNum", 1);
                setResult(RESULT_OK, returnIntent);
                finish();
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                Intent returnIntent = new Intent();
                returnIntent.putExtra("itemPosition", itemPosition);
                returnIntent.putExtra("deleteButtonNum", -1);
                setResult(RESULT_OK, returnIntent);
                finish();
            }
        });


    }

    public boolean checkDate(EditText inputMonth, EditText inputDay, EditText inputYear, int month, int day)
    {
        if(inputMonth.getText().toString().isEmpty() || inputDay.getText().toString().isEmpty() || inputYear.getText().toString().isEmpty())
            return false;

        if(month < 1 || month > 12)
            return false;


        if(day < 1 || day > 31 )
            return false;

        return true;
    }

}