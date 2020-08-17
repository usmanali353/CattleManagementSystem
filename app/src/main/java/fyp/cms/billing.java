package fyp.cms;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.auth.FirebaseAuth;
import java.util.ArrayList;
import fyp.cms.Adapters.purchased_item_adapter;
import fyp.cms.Models.cartItems;
import fyp.cms.Models.dbhelper;
public class billing extends AppCompatActivity {
 RecyclerView checkOutItems;
    ArrayList<cartItems> cartItems;
    TextView totalNumberOfItems,subTotal,total;
    Button placeOrderBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_billing);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        cartItems=new ArrayList<>();
        checkOutItems=findViewById(R.id.checkout_items_list);
        placeOrderBtn=findViewById(R.id.placeorderbtn);
        placeOrderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               startActivity(new Intent(billing.this,MapsActivity.class));
            }
        });
        totalNumberOfItems=findViewById(R.id.order_item_count);
        subTotal=findViewById(R.id.order_total_amount);
        total=findViewById(R.id.order_full_amounts);
        Cursor productsList=new dbhelper(this).get_products_in_cart(FirebaseAuth.getInstance().getCurrentUser().getUid());
        if(productsList.getCount()==0){
            Toast.makeText(this,"No Products in Cart", Toast.LENGTH_LONG).show();
        }else{
            while(productsList.moveToNext()){
                cartItems.add(new cartItems(productsList.getString(1),productsList.getString(6),productsList.getString(7),productsList.getInt(4),productsList.getInt(2)));
            }
            checkOutItems.setLayoutManager(new LinearLayoutManager(this));
            checkOutItems.setAdapter(new purchased_item_adapter(cartItems,this));
            int totalAmount=new dbhelper(this).getTotalOfAmount(FirebaseAuth.getInstance().getCurrentUser().getUid());
            int totalItems=new dbhelper(this).get_num_of_rows(FirebaseAuth.getInstance().getCurrentUser().getUid());
            subTotal.setText("Rs "+ String.valueOf(totalAmount));
            total.setText("Rs "+ String.valueOf(totalAmount));
            totalNumberOfItems.setText(String.valueOf(totalItems));
        }
    }
}
