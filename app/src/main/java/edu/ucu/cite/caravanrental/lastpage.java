package edu.ucu.cite.caravanrental;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class lastpage extends AppCompatActivity {

    Button BTNfinish;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lastpage);

        BTNfinish = findViewById(R.id.BTNfinish);

        BTNfinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(lastpage.this,Mainscreen.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fadein,R.anim.fadeout);
            }
        });

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(lastpage.this,Mainscreen.class);
        startActivity(intent);
        //super.onBackPressed();
    }
}