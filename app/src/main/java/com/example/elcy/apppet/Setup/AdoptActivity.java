package com.example.elcy.apppet.Setup;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.elcy.apppet.PetMainActivity;
import com.example.elcy.apppet.R;

public class AdoptActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adopt);

        Intent intent = new Intent(AdoptActivity.this, PetMainActivity.class);
        startActivity(intent);
        finish();
        return;
    }
}
