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

        //createItems(items);


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
            int deleteButtonNum = data.getIntExtra("deleteButtonNum", 1);
            if(itemPosition != -1)
            {
                items.set(itemPosition, updatedItem);
                adapter1.notifyDataSetChanged();

            }

            if(deleteButtonNum == -1)
            {
              items.remove(itemPosition);
              adapter1.notifyDataSetChanged();
            }
            sortItems();
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

    public void createItems(ArrayList<Item> itemList)
    {
        itemList.add(new Item("Milk", "Brand A", 4, 30, 2023, "Dairy"));
        itemList.add(new Item("Cream", "Brand B", 5, 1, 2023, "Dairy"));
        itemList.add(new Item("Cheese", "Brand C", 7, 15, 2023, "Dairy"));
        itemList.add(new Item("Apple", "Brand A", 4, 30, 2023, "Produce"));
        itemList.add(new Item("Banana", "Brand B", 5, 1, 2023, "Produce"));
        itemList.add(new Item("Zucchini", "Brand C", 5, 15, 2023, "Produce"));
        itemList.add(new Item("Chicken", "Brand A", 4, 30, 2023, "Meats"));
        itemList.add(new Item("Steak", "Brand B", 5, 1, 2023, "Meats"));
        itemList.add(new Item("Fish", "Brand C", 5, 15, 2023, "Meats"));
        itemList.add(new Item("Water", "Brand A","Beverages"));
        itemList.add(new Item("Coke", "Brand B", 5, 1, 2023, "Beverages"));
        itemList.add(new Item("Juice", "Brand C","Beverages"));
        itemList.add(new Item("Bread", "Brand A", 4, 30, 2023, "Other"));
        itemList.add(new Item("Peanut Butter", "Brand B", 5, 1, 2023, "Other"));
        itemList.add(new Item("Candy", "Brand C", "Other"));
        itemList.add(new Item("Milk", "Brand A", 4, 30, 2023, "Dairy"));
        itemList.add(new Item("Yogurt", "Dairy", 5, 1, 2023));
        itemList.add(new Item("Cheese", "Brand C", 5, 15, 2023, "Dairy"));
        itemList.add(new Item("Orange Juice", "Brand D", 5, 15, 2023, "Beverages"));
        itemList.add(new Item("Apple Juice", "Brand E", 5, 15, 2023, "Beverages"));
        itemList.add(new Item("Soda", "Brand F", 5, 15, 2023, "Beverages"));
        itemList.add(new Item("Ketchup", "Brand G", 5, 15, 2023, "Other"));
        itemList.add(new Item("Mustard", "Brand H", 5, 15, 2023, "Other"));
        itemList.add(new Item("Mayonnaise", "Brand I", 5, 15, 2023, "Other"));
        itemList.add(new Item("Barbecue Sauce","Other", 5, 15, 2023));
        itemList.add(new Item("Ranch Dressing", "Brand K", 5, 15, 2023, "Other"));
        itemList.add(new Item("Italian Dressing", "Brand L", 5, 15, 2023, "Other"));
        itemList.add(new Item("Honey Mustard", "Brand M", 5, 15, 2023, "Other"));
        itemList.add(new Item("Salmon", "Brand N", 5, 15, 2023, "Meats"));
        itemList.add(new Item("Chicken", "Meats", 5, 15, 2023));
        itemList.add(new Item("Beef", "Brand P", 5, 15, 2023, "Meats"));
        itemList.add(new Item("Pork", "Brand Q", 5, 15, 2023, "Meats"));
        itemList.add(new Item("Bacon", "Brand R", 5, 15, 2023, "Meats"));
        itemList.add(new Item("Sausage", "Brand S", 5, 15, 2023, "Meats"));
        itemList.add(new Item("Eggs", "Brand T", "Produce"));
        itemList.add(new Item("Lettuce","Produce"));
        itemList.add(new Item("Tomatoes", "Brand V", "Produce"));
        itemList.add(new Item("Potatoes", "Brand W", "Produce"));
        itemList.add(new Item("Onions","Produce"));
        itemList.add(new Item("Carrots", "Brand Y", "Produce"));
        itemList.add(new Item("Oranges", "Brand Z", "Produce"));
        itemList.add(new Item("Grapes", "Produce"));
    }

}