package fyp.cms;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.auth.FirebaseAuth;
import java.util.ArrayList;

import fyp.cms.Adapters.cart_list_adapter;
import fyp.cms.Models.cartItems;
import fyp.cms.Models.dbhelper;

public class shopping_cart extends AppCompatActivity {
 ArrayList<cartItems> cartItems;
 RecyclerView cartItemsList;
 Button checkout;
// "id","productname","price","image","quantity","userId","productid","weight"
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        cartItems=new ArrayList<>();
        cartItemsList=findViewById(R.id.cartItemsList);
        checkout=findViewById(R.id.checkout);
        Cursor productsList=new dbhelper(this).get_products_in_cart(FirebaseAuth.getInstance().getCurrentUser().getUid());
        if(productsList.getCount()==0){
            Toast.makeText(this,"No Products in Cart", Toast.LENGTH_LONG).show();
        }else{
            while(productsList.moveToNext()){
                cartItems.add(new cartItems(productsList.getString(1),productsList.getString(6),productsList.getString(7),productsList.getBlob(3),productsList.getInt(4),productsList.getInt(2),productsList.getString(8),productsList.getString(9),productsList.getString(10)));
            }
            cartItemsList.setLayoutManager(new LinearLayoutManager(this));
            cartItemsList.setAdapter(new cart_list_adapter(cartItems,this));
            checkout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(shopping_cart.this,billing.class));
                }
            });
        }

    }

}
