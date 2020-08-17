package fyp.cms;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.preference.PreferenceManager;

import com.google.gson.Gson;

import fyp.cms.Models.Orders;

public class orderDetails extends AppCompatActivity {
    Orders order;
    SharedPreferences prefs;
    TextView customerName,phone,email,address,orderedItems;
    Button confirmBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        prefs= PreferenceManager.getDefaultSharedPreferences(this);
        customerName=findViewById(R.id.customerName);
        phone=findViewById(R.id.phone);
        email=findViewById(R.id.email);
        address=findViewById(R.id.address);
        orderedItems=findViewById(R.id.items);
        confirmBtn=findViewById(R.id.confirm_button);
        order=new Gson().fromJson(getIntent().getStringExtra("orderData"), Orders.class);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }
}