package fyp.cms;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import fyp.cms.Firebase_Operations.firebase_operations;

public class sellar_home_page extends AppCompatActivity {
RecyclerView animalList;
SharedPreferences prefs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sellar_home_page);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        prefs= PreferenceManager.getDefaultSharedPreferences(this);
        animalList=findViewById(R.id.animals_list);
        animalList.setLayoutManager(new LinearLayoutManager(this));
        firebase_operations.getAnimalsForSeller(this,animalList);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.seller_page_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.add_animal){
           startActivity(new Intent(sellar_home_page.this,add_animals.class));
        }else if(item.getItemId()==R.id.signOut){
            FirebaseAuth.getInstance().signOut();
            prefs.edit().remove("user_info").apply();
            startActivity(new Intent(sellar_home_page.this,Selection.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP));
            finish();
        }else if(item.getItemId()==R.id.myOrders){
            startActivity(new Intent(sellar_home_page.this,orders_list_page.class));
        }
        return super.onOptionsItemSelected(item);
    }
}
