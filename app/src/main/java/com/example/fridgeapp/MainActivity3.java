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
        TextView wrongDateText = findViewById(R.id.wrongDateText2);
        wrongDateText.setVisibility(View.INVISIBLE);
        Item item = getIntent().getParcelableExtra("item");
        int itemPosition = getIntent().getIntExtra("pos", -1);

        itemNameTop.setText(item.getName());
        itemName.setText(item.getName());
        itemCategory.setSelection(item.getCategoryIndex());
        itemMonth.setText(null);
        itemDay.setText(null);
        itemYear.setText(null);

        if(item.getBrand() != null)
        {
            itemBrand.setText(item.getBrand());
        }

        if(item.getMonth() != 0)
        {
            itemMonth.setText(String.valueOf(item.getMonth()));
            itemDay.setText(String.valueOf(item.getDay()));
            itemYear.setText(String.valueOf(item.getYear()));
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

                String itemMonthStr = itemMonth.getText().toString();
                String itemDayStr = itemDay.getText().toString();
                String itemYearStr = itemYear.getText().toString();

                if(!itemMonthStr.isEmpty() && !itemDayStr.isEmpty() && !itemYearStr.isEmpty())
                {
                        int itemMonthInt = Integer.parseInt(itemMonthStr);
                        int itemDayInt = Integer.parseInt(itemDayStr);
                        int itemYearInt = Integer.parseInt(itemYearStr);

                        if (checkDate(itemMonthInt, itemDayInt, itemYearInt)) {
                            item.setDate(itemMonthInt, itemDayInt, itemYearInt);
                        } else {
                            wrongDateText.setVisibility(View.VISIBLE);
                            return;
                        }


                }
                else if(itemMonthStr.isEmpty() && itemDayStr.isEmpty() && itemYearStr.isEmpty())
                {
                    item.clearDate();
                }
                else
                {
                    wrongDateText.setVisibility(View.VISIBLE);
                    return;
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

    public boolean checkDate(int month, int day, int year)
    {
        if((month > 0 && month < 13) && (day > 0 && day < 32) && (year >= 2023))
            return true;
        else
            return false;
    }

}