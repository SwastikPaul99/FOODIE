package com.example.foodie;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.foodie.model.Order;

public class OrderSummaryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_summary);
        Order order=getIntent().getParcelableExtra("Ordereditem");
    }
}