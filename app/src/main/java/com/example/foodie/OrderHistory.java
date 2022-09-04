package com.example.foodie;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodie.adapter.OrderHistoryAdapter;
import com.example.foodie.data.DBHandler;
import com.example.foodie.model.Order;
import com.example.foodie.model.ResturantModel;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class OrderHistory extends AppCompatActivity implements OrderHistoryAdapter.OrderHistoryListClickListener {

    List<Order> OrderList=new ArrayList<>();
    RecyclerView recyclerView;
    OrderHistoryAdapter orderHistoryAdapter;
    DBHandler dbHandler=new DBHandler(this);
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_history);

        dbHandler=new DBHandler(this);
        OrderList=dbHandler.getdata();
        recyclerView=findViewById(R.id.order_history_recycle);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        orderHistoryAdapter=new OrderHistoryAdapter(this,OrderList,this::onItemClick);

        recyclerView.setAdapter(orderHistoryAdapter);

    }
    @Override
    public void onItemClick(Order order) {
        Intent intent = new Intent(OrderHistory.this, OrderSummaryActivity.class);
        intent.putExtra("Ordereditem", order);
        startActivity(intent);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        setResult(Activity.RESULT_CANCELED);
        finish();
    }
}
