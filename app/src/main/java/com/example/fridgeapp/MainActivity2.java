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
                String itemMonthStr = itemMonth.getText().toString();
                String itemDayStr = itemDay.getText().toString();
                String itemYearStr = itemYear.getText().toString();

                if (itemNameStr.isEmpty() ){
                    noInfoText.setVisibility(View.VISIBLE); // show the noInfoText
                    return; // exit the method without creating a new Item or returning to MainActivity
                }

                Item newItem = new Item(itemNameStr, itemCategoryStr);

                if(itemMonthStr.isEmpty() && itemDayStr.isEmpty() && itemYearStr.isEmpty())
                {
                    newItem = new Item(itemNameStr, itemBrandStr, itemCategoryStr);

                }
                else if(itemBrandStr.isEmpty())
                {
                    if(checkEmptyDate(itemMonthStr, itemDayStr, itemYearStr)) {
                        int itemMonthInt = Integer.parseInt(itemMonthStr);
                        int itemDayInt = Integer.parseInt(itemDayStr);
                        int itemYearInt = Integer.parseInt(itemYearStr);

                        if (checkDate(itemMonthInt, itemDayInt, itemYearInt))
                        {
                            newItem = new Item(itemNameStr, itemCategoryStr, itemMonthInt, itemDayInt, itemYearInt);
                        }
                        else {
                            wrongDateText.setVisibility(View.VISIBLE);
                            return;
                        }
                    }
                    else
                    {
                        wrongDateText.setVisibility(View.VISIBLE);
                        return;
                    }

                }
                else
                {
                    if(checkEmptyDate(itemMonthStr, itemDayStr, itemYearStr)) {
                        int itemMonthInt = Integer.parseInt(itemMonthStr);
                        int itemDayInt = Integer.parseInt(itemDayStr);
                        int itemYearInt = Integer.parseInt(itemYearStr);
                        if (checkDate(itemMonthInt, itemDayInt, itemYearInt)) {
                            newItem = new Item(itemNameStr, itemBrandStr, itemMonthInt, itemDayInt, itemYearInt, itemCategoryStr);
                        }
                        else {
                            wrongDateText.setVisibility(View.VISIBLE);
                            return;
                        }
                    }
                    else
                    {
                        wrongDateText.setVisibility(View.VISIBLE);
                        return;
                    }
                }
                Intent returnIntent = new Intent();
                returnIntent.putExtra("newItem", newItem);
                setResult(RESULT_OK, returnIntent);
                finish();
            }
        });

    }

    public boolean checkDate(int month, int day, int year)
    {
        if((month > 0 && month < 13) && (day > 0 && day < 32) && (year >= 2023))
            return true;
        else
            return false;

    }

    public boolean checkEmptyDate(String month, String day, String year)
    {
        int emptyCount = 0;

        if(month.isEmpty())
            ++emptyCount;

        if(day.isEmpty())
            ++emptyCount;

        if(year.isEmpty())
            ++emptyCount;

        if(emptyCount > 0)
            return false;
        else
            return true;
    }

}