package com.example.fridgeapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity implements RecyclerViewInterface
{
    ArrayList<Item> items;

    Spinner sortBy;
    MyAdapter adapter1;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sortBy = findViewById(R.id.sortBy);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.sortbyOptions, R.layout.custom_spinner_layout);

        adapter.setDropDownViewResource(R.layout.custom_dropdown_layout);

        sortBy.setAdapter(adapter);

        items = new ArrayList<>();
        adapter1 = new MyAdapter(this, items, this);

        RecyclerView recyclerView = findViewById(R.id.itemList);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter1);

        Button addItem = findViewById(R.id.addItem);


        Intent secondIntent = new Intent(this, MainActivity2.class);

        addItem.setOnClickListener(v ->
                {
                    startActivityForResult(secondIntent, 1);
                }

        );

        sortBy.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sortItems();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // do nothing
            }
        });


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            Item newItem = data.getParcelableExtra("newItem");
            items.add(newItem);
            sortItems();
            adapter1.notifyDataSetChanged();
        }

        if(requestCode == 2 && resultCode == RESULT_OK)
        {
            Item updatedItem = data.getParcelableExtra("updatedItem");
            int itemPosition = data.getIntExtra("itemPosition", -1);
            if(itemPosition != -1)
            {
                items.set(itemPosition, updatedItem);
                adapter1.notifyDataSetChanged();

            }
        }

    }



    private void sortItems()
    {
        String selectedItem = sortBy.getSelectedItem().toString();

        switch (selectedItem)
        {
            case "Name":
                Collections.sort(items, Item.ItemNameComparator);
                adapter1.notifyDataSetChanged();
                break;
            case "Expiration Date":
                Collections.sort(items, Item.ItemDateComparator);
                adapter1.notifyDataSetChanged();
                break;
            case "Category":
                Collections.sort(items, Item.ItemCategoryComparator);
                adapter1.notifyDataSetChanged();
                break;
            default:
                break;
        }
    }

    @Override
    public void onItemClick(int pos)
    {
        Intent intent = new Intent(MainActivity.this, MainActivity3.class);

        Item selectedItem = items.get(pos);
        intent.putExtra("item", selectedItem);
        intent.putExtra("pos", pos);
        startActivityForResult(intent, 2);
    }
}