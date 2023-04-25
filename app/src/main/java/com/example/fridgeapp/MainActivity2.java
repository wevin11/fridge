package com.example.fridgeapp;


import androidx.appcompat.app.AppCompatActivity;

import android.os.Parcelable;
import android.view.View;
import android.content.Intent;
import android.os.Bundle;
import android.widget.*;

import java.util.ArrayList;

public class MainActivity2 extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Spinner categoryOptions = findViewById(R.id.categoryOptions);

        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.categoryOptionsList, R.layout.custom_spinner_layout);

        spinnerAdapter.setDropDownViewResource(R.layout.custom_dropdown_layout);

        categoryOptions.setAdapter(spinnerAdapter);

        Spinner itemCategory = findViewById(R.id.categoryOptions);
        EditText itemName = findViewById(R.id.nameInput);
        EditText itemBrand = findViewById(R.id.brandInput);
        EditText itemMonth = findViewById(R.id.monthInput);
        EditText itemDay = findViewById(R.id.dayInput);
        EditText itemYear = findViewById(R.id.yearInput);
        Button submitButton = findViewById(R.id.submitButton);
        TextView noInfoText = findViewById(R.id.noInfo);
        noInfoText.setVisibility(View.INVISIBLE);
        TextView wrongDateText = findViewById(R.id.wrongDateText);
        wrongDateText.setVisibility(View.INVISIBLE);


        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String itemCategoryStr = itemCategory.getSelectedItem().toString();
                String itemNameStr = itemName.getText().toString();
                String itemBrandStr = itemBrand.getText().toString();
                String itemNumberStr = itemMonth.getText().toString();

                if (itemNameStr.isEmpty() ){
                    noInfoText.setVisibility(View.VISIBLE); // show the noInfoText
                    return; // exit the method without creating a new Item or returning to MainActivity
                }

                Item newItem = new Item(itemNameStr, itemCategoryStr);

                if(itemNumberStr.isEmpty())
                {
                    newItem = new Item(itemNameStr, itemBrandStr, itemCategoryStr);

                }
                else if(itemBrandStr.isEmpty())
                {
                    int itemMonthInt = Integer.parseInt(itemNumberStr);
                    itemNumberStr = itemDay.getText().toString();
                    int itemDayInt = Integer.parseInt(itemNumberStr);
                    itemNumberStr = itemYear.getText().toString();
                    int itemYearInt = Integer.parseInt(itemNumberStr);

                    if(checkDate(itemMonthInt, itemDayInt))
                    {
                        newItem = new Item(itemNameStr, itemCategoryStr, itemMonthInt, itemDayInt, itemYearInt);
                    }

                }
                else
                {
                    int itemMonthInt = Integer.parseInt(itemNumberStr);
                    itemNumberStr = itemDay.getText().toString();
                    int itemDayInt = Integer.parseInt(itemNumberStr);
                    itemNumberStr = itemYear.getText().toString();
                    int itemYearInt = Integer.parseInt(itemNumberStr);
                    if(checkDate(itemMonthInt, itemDayInt))
                    {
                        newItem = new Item(itemNameStr, itemBrandStr, itemMonthInt, itemDayInt, itemYearInt, itemCategoryStr);
                    }

                }
                Intent returnIntent = new Intent();
                returnIntent.putExtra("newItem", newItem);
                setResult(RESULT_OK, returnIntent);
                finish();
            }
        });

    }

    public boolean checkDate(int month, int day)
    {

        if(month < 1 || month > 12)
            return false;


        if(day < 1 || day > 31 )
            return false;

        return true;
    }
}