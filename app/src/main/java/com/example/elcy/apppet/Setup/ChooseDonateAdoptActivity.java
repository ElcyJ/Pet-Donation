package com.example.elcy.apppet.Setup;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.elcy.apppet.R;

public class ChooseDonateAdoptActivity extends AppCompatActivity {

    private Button mDonate, mAdopt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_donate_adopt);

        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mDonate = findViewById(R.id.doar);
        mAdopt = findViewById(R.id.adotar);

        mDonate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChooseDonateAdoptActivity.this, DonateActivity.class);
                startActivity(intent);
                finish();
            }
        });

        mAdopt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChooseDonateAdoptActivity.this, AdoptActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

}
