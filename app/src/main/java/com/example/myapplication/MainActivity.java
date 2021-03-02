package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.facebook.ads.AudienceNetworkAds;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AudienceNetworkAds.initialize(this);

        recyclerView = findViewById(R.id.recyclerview);

        List<Object> models = new ArrayList<>();
        for (int i = 1; i <= 50; i++) {
            models.add(new DataModel("Title : " + i, "Description : " + i));
        }

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        DataAdapter adapter = new DataAdapter(this, models, models.size());
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        adapter.initNativeAds();
    }
}