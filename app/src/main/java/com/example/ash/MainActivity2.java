package com.example.ash;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity2 extends AppCompatActivity {
    ListView listView;
    String arr[] = { "Founder of college","your name"," college principal name"," Faculty of Computer Department"," Faculty"," Created By"," Fees"," WEBSITE"," ADDRESS"," CONTACT"," CONTACT INFO"," Placements"," Placement"," Course"," learn more"," College Information"," Courses"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        listView = (ListView) findViewById(R.id.list_view);
        ArrayAdapter arrayAdapter = new ArrayAdapter<String>(MainActivity2.this, android.R.layout.simple_list_item_1,arr);
        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String str = listView.getAdapter().getItem(i).toString();
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                intent.putExtra("name",str);
                startActivity(intent);
            }
        });

    }
}