package com.example.foodie;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodie.adapter.PlaceYourOrderAdapter;
import com.example.foodie.data.DBHandler;
import com.example.foodie.model.Menus;
import com.example.foodie.model.Order;
import com.example.foodie.model.ResturantModel;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class PlaceYourOrderActivity extends AppCompatActivity{

    private EditText inputName, inputAddress, inputCity, inputState, inputZip,inputCardNumber, inputCardExpiry, inputCardPin ;
    private RecyclerView cartItemsRecyclerView;
    private TextView tvSubtotalAmount, tvDeliveryChargeAmount, tvDeliveryCharge, tvTotalAmount, buttonPlaceYourOrder;
    private SwitchCompat switchDelivery;
    private boolean isDeliveryOn;
    private PlaceYourOrderAdapter placeYourOrderAdapter;
    DBHandler dbHandler=new DBHandler(this);
    Order order=new Order();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_your_order);

        ResturantModel resturantModel = getIntent().getParcelableExtra("ResturantModel");
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(resturantModel.getName());
        actionBar.setSubtitle(resturantModel.getAddress());
        actionBar.setDisplayHomeAsUpEnabled(true);


        inputName = findViewById(R.id.inputName);
        inputAddress = findViewById(R.id.inputAddress);
        inputCity = findViewById(R.id.inputCity);
        inputState = findViewById(R.id.inputState);
        inputZip = findViewById(R.id.inputZip);
        inputCardNumber = findViewById(R.id.inputCardNumber);
        inputCardExpiry = findViewById(R.id.inputCardExpiry);
        inputCardPin = findViewById(R.id.inputCardPin);
        tvSubtotalAmount = findViewById(R.id.tvSubtotalAmount);
        tvDeliveryChargeAmount = findViewById(R.id.tvDeliveryChargeAmount);
        tvDeliveryCharge = findViewById(R.id.tvDeliveryCharge);
        tvTotalAmount = findViewById(R.id.tvTotalAmount);
        buttonPlaceYourOrder = findViewById(R.id.buttonPlaceYourOrder);
        switchDelivery = findViewById(R.id.switchDelivery);

        cartItemsRecyclerView = findViewById(R.id.cartItemsRecyclerView);

        buttonPlaceYourOrder.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                onPlaceOrderButtonClick(resturantModel);
            }
        });

        switchDelivery.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    inputAddress.setVisibility(View.VISIBLE);
                    inputCity.setVisibility(View.VISIBLE);
                    inputState.setVisibility(View.VISIBLE);
                    inputZip.setVisibility(View.VISIBLE);
                    tvDeliveryChargeAmount.setVisibility(View.VISIBLE);
                    tvDeliveryCharge.setVisibility(View.VISIBLE);
                    isDeliveryOn = true;
                    calculateTotalAmount(resturantModel);
                } else {
                    inputAddress.setVisibility(View.GONE);
                    inputCity.setVisibility(View.GONE);
                    inputState.setVisibility(View.GONE);
                    inputZip.setVisibility(View.GONE);
                    tvDeliveryChargeAmount.setVisibility(View.VISIBLE);
                    tvDeliveryCharge.setVisibility(View.VISIBLE);
                    tvDeliveryChargeAmount.setText("$0.00");
                    isDeliveryOn = false;
                    calculateTotalAmount(resturantModel);
                }
            }
        });
        initRecyclerView(resturantModel);
        calculateTotalAmount(resturantModel);
    }

    private void calculateTotalAmount(ResturantModel resturantModel) {
        float subTotalAmount = 0f;
        String s="";

        for(Menus m : resturantModel.getMenus()) {
            subTotalAmount += m.getPrice() * m.getTotalInCart();
            s+=m.getName()+" Qty. "+m.getTotalInCart()+"\n";
        }

        order.setOrderItems(s);

        tvSubtotalAmount.setText("$"+String.format("%.2f", subTotalAmount));
        if(isDeliveryOn) {
            tvDeliveryChargeAmount.setText("$"+String.format("%.2f", resturantModel.getDelivery_charge()));
            subTotalAmount += resturantModel.getDelivery_charge();
        }

        tvTotalAmount.setText("$"+String.format("%.2f", subTotalAmount));
        order.setTotalPrice(subTotalAmount);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void onPlaceOrderButtonClick(ResturantModel resturantModel) {
        if(TextUtils.isEmpty(inputName.getText().toString())) {
            inputName.setError("Please enter name ");
            return;
        } else if(isDeliveryOn && TextUtils.isEmpty(inputAddress.getText().toString())) {
            inputAddress.setError("Please enter address ");
            return;
        }else if(isDeliveryOn && TextUtils.isEmpty(inputCity.getText().toString())) {
            inputCity.setError("Please enter city ");
            return;
        }else if(isDeliveryOn && TextUtils.isEmpty(inputState.getText().toString())) {
            inputState.setError("Please enter state ");
            return;
        }else if(isDeliveryOn && TextUtils.isEmpty(inputZip.getText().toString())) {
            inputState.setError("Please enter zip ");
            return;
        }else if( TextUtils.isEmpty(inputCardNumber.getText().toString())) {
            inputCardNumber.setError("Please enter card number ");
            return;
        }else if( TextUtils.isEmpty(inputCardExpiry.getText().toString())) {
            inputCardExpiry.setError("Please enter card expiry ");
            return;
        }else if( TextUtils.isEmpty(inputCardPin.getText().toString())) {
            inputCardPin.setError("Please enter card pin/cvv ");
            return;
        }
        order.setResturantName(resturantModel.getName());
        order.setImage(resturantModel.getImage());
        order.setResturantAddress(resturantModel.getAddress());
        order.setName(inputName.getText().toString());
        order.setID((int)(Math.random()*(1000000-100+1))+100);

        if(isDeliveryOn)
        {
            order.setOrderType("Delivery");
            order.setAddress(inputCity.getText().toString());
            order.setCity(inputCity.getText().toString());
            order.setState(inputState.getText().toString());
            order.setPincode(inputZip.getText().toString());
        }
        else
        {
            order.setOrderType("Pickup");
        }
        String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        order.setOrderDate(date);
        //Calendar calendar=Calendar.getInstance();
        //Date date1=calendar.getTime();

        //order.setTime(date1);
        /*SQLiteDatabase db=dbHandler.getWritableDatabase();
        dbHandler.onUpgrade(db,4,4);*/
        dbHandler.addOrders(order);



        //start success activity..
        Intent i = new Intent(PlaceYourOrderActivity.this, OrderSuccessActivity.class);
        i.putExtra("ResturantModel", resturantModel);
        startActivityForResult(i, 1000);

    }

    private void initRecyclerView(ResturantModel resturantModel) {

        cartItemsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        placeYourOrderAdapter = new PlaceYourOrderAdapter(resturantModel.getMenus());
        //resturantModel.getMenus().clear();
        //placeYourOrderAdapter.notifyDataSetChanged();
        cartItemsRecyclerView.setAdapter(placeYourOrderAdapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if(requestCode == 1000) {
            setResult(Activity.RESULT_OK);
            finish();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home :
                finish();
            default:
                //do nothing
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        setResult(Activity.RESULT_CANCELED);
        finish();
    }
}