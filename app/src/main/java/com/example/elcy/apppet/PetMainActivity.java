package com.example.elcy.apppet;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.elcy.apppet.Auth.ChooseLoginRegistrationActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.lorentzos.flingswipe.SwipeFlingAdapterView;

import java.util.ArrayList;
import java.util.List;

public class PetMainActivity extends AppCompatActivity {
    //private Card cards_data[];
    private ArrayAdapter arrayAdapter;
    private int i;

    private FirebaseAuth mAuth;
    private String currentUid;

    //ListView listView;
    List<Card> rowItems;

    String animalMain;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_main);

        //final DatabaseReference petsDb = FirebaseDatabase.getInstance().getReference().child("Animals");

        mAuth = FirebaseAuth.getInstance();
        currentUid = mAuth.getCurrentUser().getUid();
        rowItems = new ArrayList<>();

        arrayAdapter = new PetArrayAdapter(this, R.layout.item, rowItems);
        DatabaseReference showAllAnimalsDb = FirebaseDatabase.getInstance().getReference().child("Animals").child("Cat");
        showAllAnimalsDb.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                if (dataSnapshot.exists()){
                    animalMain = "Cat";
                    Card card = new Card(dataSnapshot.getKey(), dataSnapshot.child("sex").getValue().toString());
                    rowItems.add(card);
                    arrayAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        SwipeFlingAdapterView flingContainer = findViewById(R.id.frame);
        flingContainer.setAdapter(arrayAdapter);
        flingContainer.setFlingListener(new SwipeFlingAdapterView.onFlingListener() {

            @Override
            public void removeFirstObjectInAdapter() {

                Log.d("LIST", "removed object!");
                rowItems.remove(0);
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onLeftCardExit(Object dataObject) {
                Card obj = (Card) dataObject;
                String petId = obj.getUserId();
                //showAllAnimalsDb.child(petId).child("connections").child("nope").child(currentUid).setValue(true);
                Toast.makeText(PetMainActivity.this, "left", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onRightCardExit(Object dataObject) {
                Toast.makeText(PetMainActivity.this, "right", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdapterAboutToEmpty(int itemsInAdapter) {

            }

            @Override
            public void onScroll(float scrollProgressPercent) {
            }

        });

        flingContainer.setOnItemClickListener(new SwipeFlingAdapterView.OnItemClickListener() {
            @Override
            public void onItemClicked(int itemPosition, Object dataObject) {
                Toast.makeText(PetMainActivity.this, "click", Toast.LENGTH_SHORT).show();
            }

        });




    }

    public void logoutUser(View view) {
        mAuth.signOut();
        Intent intent = new Intent(PetMainActivity.this, ChooseLoginRegistrationActivity.class);
        startActivity(intent);
        finish();
        return;
    }
}