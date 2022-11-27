package edu.ucu.cite.caravan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class MainscreenActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, NavigationView.OnNavigationItemSelectedListener{

    private long backPressedTime;
    private Toast backToast;


    //Variables
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainscreenv2);

        if(!SharedPrefManager.getInstance(this).isLoggedIn()){
            finish();
            startActivity(new Intent(this, LoginActivity.class));

            //Initialize and Assign Variable
            drawerLayout = findViewById(R.id.drawer_layout);
            navigationView = findViewById(R.id.nav_view);
            toolbar = findViewById(R.id.toolbar);

            // Toolbar
            setSupportActionBar(toolbar);

            navigationView.bringToFront();
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open,R.string.close);
            drawerLayout.addDrawerListener(toggle);
            toggle.syncState();
            navigationView.setNavigationItemSelectedListener((NavigationView.OnNavigationItemSelectedListener) this);

        }

    }

    @Override
    public void onBackPressed() {

        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else{

            if(backPressedTime + 2000 > System.currentTimeMillis()){
                backToast.cancel();
                super.onBackPressed();
                return;
            }else{
                backToast = Toast.makeText(getBaseContext(),"Press back again to exit", Toast.LENGTH_SHORT);
                backToast.show();
            }

            backPressedTime = System.currentTimeMillis();
        }
    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();
        Toast.makeText(parent.getContext(),text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.profile:
                Intent a = new Intent(MainscreenActivity.this, profile.class);
                startActivity(a);
                overridePendingTransition(R.anim.fadein,R.anim.fadeout);
                break;
            case R.id.repairandservices:
                Intent b = new Intent(MainscreenActivity.this, Repair_Services.class);
                startActivity(b);
                overridePendingTransition(R.anim.fadein,R.anim.fadeout);
                break;

          /*  case R.id.pendingrents:
                Intent e = new Intent(MainscreenActivity.this, Pndng_Rents.class);
                startActivity(e);
                overridePendingTransition(R.anim.fadein,R.anim.fadeout);
                break;

            case R.id.rents:
                Intent f = new Intent(MainscreenActivity.this, Apprvd_Rents.class);
                startActivity(f);
                overridePendingTransition(R.anim.fadein,R.anim.fadeout);
                break;

            case R.id.history:
                Intent g = new Intent(MainscreenActivity.this, Cmpltd_Rents.class);
                startActivity(g);
                overridePendingTransition(R.anim.fadein,R.anim.fadeout);
                break; */

            case R.id.nav_share:
                Intent shareIntent = new Intent(android.content.Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_SUBJECT,"Insert Subject here");
                String app_url = " https://play.google.com/store/apps/details?id=com.example.administrator";
                shareIntent.putExtra(android.content.Intent.EXTRA_TEXT,app_url);
                startActivity(Intent.createChooser(shareIntent, "Share via"));
                break;
            case R.id.logout:
                SharedPrefManager.getInstance(this).logout();
                finish();
                Intent d = new Intent(MainscreenActivity.this, LoginActivity.class);
                startActivity(d);
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}