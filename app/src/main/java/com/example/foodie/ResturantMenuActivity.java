package com.example.foodie;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodie.adapter.MenuListAdapter;
import com.example.foodie.model.Menus;
import com.example.foodie.model.ResturantModel;

import java.util.ArrayList;
import java.util.List;

public class ResturantMenuActivity extends AppCompatActivity implements MenuListAdapter.MenuListListener{
    private List<Menus> menusList=null;
    private MenuListAdapter menuListAdapter;
    private List<Menus> itemsInCartList;
    private int totalInCart=0;
    private TextView buttonCheckout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resturant_menu);
        ResturantModel resturantModel=getIntent().getParcelableExtra("ResturantModel");
        ActionBar actionBar=getSupportActionBar();
        actionBar.setTitle(resturantModel.getName());
        actionBar.setSubtitle(resturantModel.getAddress());
        actionBar.setDisplayHomeAsUpEnabled(true);


        menusList=resturantModel.getMenus();
        initRecyclerView();

        buttonCheckout=findViewById(R.id.buttonCheckout);
        buttonCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(itemsInCartList==null || itemsInCartList.size()<=0)
                {
                    Toast.makeText(view.getContext(), "Please Add some item in Cart",Toast.LENGTH_SHORT).show();
                    return;
                }
                resturantModel.setMenus(itemsInCartList);
                Intent i=new Intent(ResturantMenuActivity.this,PlaceYourOrderActivity.class);
                i.putExtra("ResturantModel",resturantModel);
                startActivityForResult(i,1000);

            }
        });
    }
    private void initRecyclerView()
    {
        RecyclerView recyclerView=findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        menuListAdapter=new MenuListAdapter(menusList,this);
        recyclerView.setAdapter(menuListAdapter);
    }

    @Override
    public void onAddtoCartClick(Menus menus) {

        if(itemsInCartList==null)
            itemsInCartList=new ArrayList<>();
        itemsInCartList.add(menus);
        totalInCart=0;
        for(Menus m:itemsInCartList)
        {
            totalInCart+=m.getTotalInCart();
        }
        buttonCheckout.setText("CheckOut ( "+totalInCart+" ) Items");
    }

    @Override
    public void onUpdateCartClick(Menus menus) {
        if(itemsInCartList.contains(menus))
        {
            int index=itemsInCartList.indexOf(menus);
            itemsInCartList.remove(index);
            itemsInCartList.add(index,menus);

            totalInCart=0;
            for(Menus m:itemsInCartList)
            {
                totalInCart+=m.getTotalInCart();
            }
            buttonCheckout.setText("CheckOut ( "+totalInCart+" ) Items");

        }

    }

    @Override
    public void onRemoveFromCartClick(Menus menus) {
        if(itemsInCartList.contains(menus))
        {
            itemsInCartList.remove(menus);
            totalInCart=0;
            for(Menus m:itemsInCartList)
            {
                totalInCart+=m.getTotalInCart();
            }
            buttonCheckout.setText("CheckOut ( "+totalInCart+" ) Items");
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 1000 && resultCode == Activity.RESULT_OK) {
            //
            finish();
        }
    }
}