package com.example.foodie;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import com.example.foodie.adapter.ResturantListAdapter;
import com.example.foodie.model.ResturantModel;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;

import android.view.Gravity;
import android.view.MenuInflater;
import android.view.View;

import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodie.databinding.ActivityMainBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.Gson;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ResturantListAdapter.ResturantListClickListener {

    private AppBarConfiguration appBarConfiguration;
    //private ActivityMainBinding binding;
    public DrawerLayout drawerLayout;
    public ActionBarDrawerToggle actionBarDrawerToggle;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*btn=findViewById(R.id.signout);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseAuth=FirebaseAuth.getInstance();
                firebaseAuth.signOut();
                startActivity(new Intent(getApplicationContext(),RegistrationActivity.class));
            }
        });*/
        /*Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawerLayout = findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();*/
        ActionBar actionBar=getSupportActionBar();
        actionBar.setTitle(R.string.app_name);


        List<ResturantModel> resturantModelList = getResturantData();
        initRecyclerView(resturantModelList);
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
            finish();

        }
    }


    private void initRecyclerView(List<ResturantModel> resturantModelList) {
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager((this)));
        recyclerView.setHasFixedSize(true);
        ResturantListAdapter adapter = new ResturantListAdapter(resturantModelList, this::onItemClick);
        recyclerView.setAdapter(adapter);
    }

    private List<ResturantModel> getResturantData() {
        InputStream is = getResources().openRawResource(R.raw.resturant);
        Writer writer = new StringWriter();
        char[] buffer = new char[1024];
        try {
            Reader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            int n;
            while ((n = reader.read(buffer)) != -1) {
                writer.write(buffer, 0, n);
            }
        } catch (Exception e) {

        }
        String jsonStr = writer.toString();
        Gson gson = new Gson();
        ResturantModel[] restaurantModels = gson.fromJson(jsonStr, ResturantModel[].class);
        List<ResturantModel> restList = Arrays.asList(restaurantModels);

        return restList;
    }


    @Override
    public void onItemClick(ResturantModel resturantModel) {
        Intent intent = new Intent(MainActivity.this, ResturantMenuActivity.class);
        intent.putExtra("ResturantModel", resturantModel);
        startActivity(intent);
    }


    @Override
    public boolean onCreateOptionsMenu(@NonNull Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.drawer_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId())
        {
            case R.id.nav_logout:
                firebaseAuth=FirebaseAuth.getInstance();
                firebaseAuth.signOut();
                startActivity(new Intent(MainActivity.this,LoginActivity.class));
                return true;
            case R.id.Orderhistory:
                startActivity(new Intent(getApplicationContext(),OrderHistory.class));
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}