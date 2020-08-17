package fyp.cms;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;

import fyp.cms.Firebase_Operations.firebase_operations;
import fyp.cms.Models.user;

public class orders_list_page extends AppCompatActivity {
  RecyclerView orders_list;
  SharedPreferences prefs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders_list_page);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        orders_list=findViewById(R.id.orders_list);
        prefs= PreferenceManager.getDefaultSharedPreferences(this);
        user u=new Gson().fromJson(prefs.getString("user_info",null),user.class);
        orders_list.setLayoutManager(new LinearLayoutManager(this));
        if(u.getRole().equals("Seller")) {
            firebase_operations.getOrdersSeller(this, orders_list);
        }else if(u.getRole().equals("Buyer")) {
            firebase_operations.getOrdersBuyer(this, orders_list);
        }
    }

}
