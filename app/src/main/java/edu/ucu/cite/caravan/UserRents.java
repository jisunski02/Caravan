package edu.ucu.cite.caravan;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

import edu.ucu.cite.caravan.Adapters.PagerAdapter;

public class UserRents extends AppCompatActivity implements AdapterView.OnItemSelectedListener, NavigationView.OnNavigationItemSelectedListener {

    private long backPressedTime;
    private Toast backToast;

    Toolbar toolbar;

    //ViewPager
    ViewPager viewPager;
    TabLayout tabLayoutRents;
    TabItem pendingTab, approveTab, completedTab;
    PagerAdapter pagerAdapter;

    FloatingActionButton fbAddRents;

    //Dialog Rent Widgets and Strings
    Button BTN_next;
    String Location="default_value",SpickupDate,Spickuptime,SreturnDate,Sreturntime;
    Long RentDays;

    private ImageButton pickupDate;
    private ImageButton returnDate;

    //for displaying time
    String displaypickupTime,displaypickupHour,displaypickupMinute,displaypickupAMPM;
    String displayreturnTime,displayreturnHour,displayreturnMinute,displayreturnAMPM;

    //para macheck kung pwede na iclick yung return date
    boolean click=false;


    //dates //Initialize variable
    TextView tv_pickupdate,tv_returndate,tv_pickuptime,tv_returntime;

    //Searchable Spinner
    TextView textView;
    ArrayList<String> arrayList = new ArrayList<>();
    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_rents);

        if(!SharedPrefManager.getInstance(this).isLoggedIn()){
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }

        //ViewPager and Tablayout
        viewPager = findViewById(R.id.viewpager);
        tabLayoutRents = findViewById(R.id.tablayout);
        pendingTab = findViewById(R.id.pendingtab);
        approveTab = findViewById(R.id.approvetab);
        //completedTab = findViewById(R.id.completedtab);

        //Toolbar Initialization..
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_back_arrow_activity));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserRents.super.onBackPressed();
            }
        });

        //Initialize PagerAdapter
        pagerAdapter = new PagerAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT, tabLayoutRents.getTabCount());
        viewPager.setAdapter(pagerAdapter);

        //Handling TabItem Click
        tabLayoutRents.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayoutRents));


    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();
        Toast.makeText(parent.getContext(),text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.profile:
                Intent a = new Intent(UserRents.this, profile.class);
                startActivity(a);
                overridePendingTransition(R.anim.fadein,R.anim.fadeout);
                break;

          /*  case R.id.pendingrents:
                Intent e = new Intent(Mainscreen.this, Pndng_Rents.class);
                startActivity(e);
                overridePendingTransition(R.anim.fadein,R.anim.fadeout);
                break;

            case R.id.rents:
                Intent f = new Intent(Mainscreen.this, Apprvd_Rents.class);
                startActivity(f);
                overridePendingTransition(R.anim.fadein,R.anim.fadeout);
                break;

            case R.id.history:
                Intent g = new Intent(Mainscreen.this, Cmpltd_Rents.class);
                startActivity(g);
                overridePendingTransition(R.anim.fadein,R.anim.fadeout);
                break; */

            case R.id.repairandservices:
               /* Intent b = new Intent(Mainscreen.this, Repair_Services.class);
                startActivity(b);
                overridePendingTransition(R.anim.fadein,R.anim.fadeout); */
                Toast.makeText(UserRents.this, "Under construction..to be followed", Toast.LENGTH_SHORT).show();
                break;

            case R.id.nav_share:
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_SUBJECT,"Insert Subject here");
                String app_url = " https://play.google.com/store/apps/details?id=com.example.administrator";
                shareIntent.putExtra(Intent.EXTRA_TEXT,app_url);
                startActivity(Intent.createChooser(shareIntent, "Share via"));
                break;
            case R.id.logout:
                SharedPrefManager.getInstance(this).logout();
                finish();
                Intent d = new Intent(UserRents.this, LoginActivity.class);
                startActivity(d);
                break;
        }

        return true;
    }
}