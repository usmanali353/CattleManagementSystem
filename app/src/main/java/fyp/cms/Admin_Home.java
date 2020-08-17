package fyp.cms;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.Gson;
import com.rengwuxian.materialedittext.MaterialEditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.preference.PreferenceManager;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;

import fr.ganfra.materialspinner.MaterialSpinner;
import fyp.cms.Fragments.animals_list_fragment;
import fyp.cms.Fragments.orders_list_fragment;
import fyp.cms.Fragments.user_list_fragment;

public class Admin_Home extends AppCompatActivity {
    ViewPager vp;
    TabLayout tb;
    SharedPreferences prefs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin__home);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        prefs= PreferenceManager.getDefaultSharedPreferences(this);
        vp=(ViewPager) findViewById(R.id.viewpager);
        tb=(TabLayout) findViewById(R.id.tabs);
        setviewpager();
        tb.setupWithViewPager(vp);
    }
    public void setviewpager(){
        viewpageradapter vpa=new viewpageradapter(getSupportFragmentManager());
        vpa.addfragment(new user_list_fragment(),"Users");
        vpa.addfragment(new animals_list_fragment(),"Animals");
        vpa.addfragment(new orders_list_fragment(),"Orders");
        vp.setAdapter(vpa);
        // vp.setOffscreenPageLimit(2);
    }
    class viewpageradapter extends FragmentStatePagerAdapter {
        ArrayList<Fragment> listofFragment;
        ArrayList<String>fragmenttitles;
        public viewpageradapter(FragmentManager fm) {
            super(fm);
            listofFragment=new ArrayList<Fragment>();
            fragmenttitles=new ArrayList<String>();
        }
        @Override
        public Fragment getItem(int position) {
            return listofFragment.get(position);
        }
        @Override
        public int getCount() {
            return listofFragment.size();
        }
        public void addfragment(Fragment f, String name){
            listofFragment.add(f);
            fragmenttitles.add(name);
        }
        @Override
        public CharSequence getPageTitle(int position) {
            return fragmenttitles.get(position);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.admin_page_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.signOut){
            FirebaseAuth.getInstance().signOut();
            prefs.edit().remove("user_role").apply();
            startActivity(new Intent(Admin_Home.this,Selection.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP));
            finish();
        }
        return true;
    }
}
