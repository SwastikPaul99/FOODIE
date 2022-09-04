package com.example.foodie.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import com.example.foodie.Params.Params;
import com.example.foodie.model.Order;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DBHandler extends SQLiteOpenHelper {

    List<Order> Orderlist=new ArrayList<>();

    public DBHandler(Context context)
    {
        super(context, Params.DB_NAME,null,Params.DB_VERSION);
    }

    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String create="CREATE TABLE IF NOT EXISTS "+Params.TABLE_NAME+"("+Params.KEY_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+Params.KEY_ORDER_TYPE+
                " TEXT, "+Params.KEY_DATE+" TEXT, "+Params.KEY_RESTURANT+" TEXT, "+Params.KEY_RESTURANT_ADDR+" TEXT, "+Params.KEY_NAME+" TEXT, "+Params.KEY_ADDR+
                " TEXT, "+Params.KEY_CITY+" TEXT, "+Params.KEY_STATE+" TEXT, "+Params.KEY_PIN+" TEXT, "+
                Params.KEY_ORDER_ITEMS+" TEXT, "+Params.KEY_PRICE_TOTAL+" FLOAT, "+Params.KEY_IMAGE+" TEXT, "+Params.KEY_TIME+" FLOAT)";
        sqLiteDatabase.execSQL(create);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS "+Params.TABLE_NAME);
        onCreate(db);
    }

    public void addOrders(Order order)
    {
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(Params.KEY_ID,order.getID());
        values.put(Params.KEY_ORDER_TYPE,order.getOrderType());
        values.put(Params.KEY_DATE,order.getOrderDate());
        values.put(Params.KEY_RESTURANT,order.getResturantName());
        values.put(Params.KEY_RESTURANT_ADDR,order.getResturantAddress());
        values.put(Params.KEY_NAME,order.getName());
        values.put(Params.KEY_ADDR,order.getAddress());
        values.put(Params.KEY_CITY,order.getCity());
        values.put(Params.KEY_STATE,order.getState());
        values.put(Params.KEY_PIN,order.getPincode());
        values.put(Params.KEY_ORDER_ITEMS,order.getOrderItems());
        values.put(Params.KEY_PRICE_TOTAL,order.getTotalPrice());
        values.put(Params.KEY_IMAGE,order.getImage());


        try {
            sqLiteDatabase.insert(Params.TABLE_NAME, null, values);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        Log.d("swastik","Successfully inserted");
        sqLiteDatabase.close();
    }

    public List<Order> getdata()
    {
        SQLiteDatabase liteDatabase=this.getWritableDatabase();
        /*Cursor cursor=liteDatabase.rawQuery("Select * from "+Params.TABLE_NAME,null);
        return cursor;*/
        String columns[]={Params.KEY_ID,Params.KEY_RESTURANT,Params.KEY_RESTURANT_ADDR,Params.KEY_ORDER_TYPE,Params.KEY_DATE,Params.KEY_IMAGE,Params.KEY_NAME,Params.KEY_ORDER_ITEMS};
        Cursor cursor=liteDatabase.query(Params.TABLE_NAME,columns,null,null,null,null,null);
        while(cursor.moveToNext()){
            int c0=cursor.getColumnIndex(Params.KEY_ID);
            int ID= cursor.getInt(c0);
            int c1=cursor.getColumnIndex(Params.KEY_RESTURANT);
            String ResName= cursor.getString(c1);
            int c2=cursor.getColumnIndex(Params.KEY_RESTURANT_ADDR);
            String ResAddr= cursor.getString(c2);
            int c3=cursor.getColumnIndex(Params.KEY_ORDER_TYPE);
            String Type= cursor.getString(c3);
            int c4=cursor.getColumnIndex(Params.KEY_DATE);
            String Date= cursor.getString(c4);
            int c5=cursor.getColumnIndex(Params.KEY_IMAGE);
            String Image= cursor.getString(c5);
            int c6=cursor.getColumnIndex(Params.KEY_NAME);
            String Name=cursor.getString(c6);
            int c7=cursor.getColumnIndex(Params.KEY_ORDER_ITEMS);
            String Items=cursor.getString(c7);
            Order order=new Order();
            order.setImage(Image);
            order.setID(ID);
            order.setOrderDate(Date);
            order.setResturantName(ResName);
            order.setResturantAddress(ResAddr);
            order.setOrderType(Type);
            order.setName(Name);
            order.setOrderItems(Items);
            Orderlist.add(order);


        }
        Collections.reverse(Orderlist);
        return Orderlist;

    }

}
