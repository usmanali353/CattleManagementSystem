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

import fyp.cms.Firebase_Operations.firebase_operations;
import fyp.cms.Models.Orders;
import fyp.cms.Models.user;

public class orderDetails extends AppCompatActivity {
    Orders order;
    SharedPreferences prefs;
    TextView customerName,phone,email,address,orderedItems;
    Button confirmBtn;
    user u;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        prefs= PreferenceManager.getDefaultSharedPreferences(this);
        u=new Gson().fromJson(prefs.getString("user_info",null),user.class);
        customerName=findViewById(R.id.customerName);
        phone=findViewById(R.id.phone);
        email=findViewById(R.id.email);
        address=findViewById(R.id.address);
        orderedItems=findViewById(R.id.items);
        confirmBtn=findViewById(R.id.confirm_button);
        if(prefs.getString("user_role",null)!=null){
            confirmBtn.setText("Process Payment");
        }else if(u!=null&&u.getRole().equals("Seller")){
            confirmBtn.setText("Change Status");
        }
        order=new Gson().fromJson(getIntent().getStringExtra("orderData"), Orders.class);
        customerName.setText(order.getCustomerName());
        phone.setText(order.getCustomerPhone());
        email.setText(order.getCustomerEmail());
        address.setText(order.getLocation());
        StringBuilder sb=new StringBuilder();
        for(int i=0;i<order.getCartItems().size();i++){
            sb.append(order.getCartItems().get(i).getName()+" "+"("+order.getCartItems().get(i).getGender()+")"+" "+"x"+order.getCartItems().get(i).getQuantity());
            sb.append("\n");
        }
        orderedItems.setText(sb.toString());
        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(prefs.getString("user_role",null)!=null){
                    firebase_operations.changeStatus(orderDetails.this,getIntent().getStringExtra("orderId"),"Payment Processed");
                    confirmBtn.setVisibility(View.GONE);
                }else if(u!=null&&u.getRole().equals("Seller")){
                    firebase_operations.changeStatus(orderDetails.this,getIntent().getStringExtra("orderId"),"Delivered");
                    confirmBtn.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }
}
