package com.example.elcy.apppet.Setup;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;


import com.example.elcy.apppet.Auth.RegistrationActivity;
import com.example.elcy.apppet.Models.Dog;
import com.example.elcy.apppet.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DonateActivity extends AppCompatActivity {

    private Button mAddPet;
    private EditText mAge;
    private RadioGroup mRadioAnimal, mRadioSexAnimal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donate);

        mAddPet = findViewById(R.id.add_pet);
        mAge = findViewById(R.id.animal_age);
        mRadioAnimal = findViewById(R.id.radio_animal);
        mRadioSexAnimal = findViewById(R.id.radio_sex_animal);

        mAddPet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Integer age = Integer.parseInt(mAge.getText().toString());
                int selectTypeId = mRadioAnimal.getCheckedRadioButtonId();
                int selectSexId = mRadioSexAnimal.getCheckedRadioButtonId();
                final RadioButton radioType = findViewById(selectTypeId);
                final RadioButton radioSex = findViewById(selectSexId);
                final String type = radioType.getText().toString();
                final String sex = radioSex.getText().toString();

                if (type.equals("Cachorro")) {
                    DatabaseReference currentAnimalDb = FirebaseDatabase.getInstance().getReference().child("Animals").child("Dog");
                    Dog dog = new Dog(sex, age);
                    currentAnimalDb.setValue(dog);

                }

            };




            });
    }
}