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
import com.example.elcy.apppet.Favorites.FavoritesActivity;
import com.example.elcy.apppet.Models.Card;
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

        mAuth = FirebaseAuth.getInstance();
        currentUid = mAuth.getCurrentUser().getUid();
        rowItems = new ArrayList<>();

        arrayAdapter = new PetArrayAdapter(this, R.layout.item, rowItems);

        final DatabaseReference usersDb = FirebaseDatabase.getInstance().getReference().child("Users");


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
                String petId = obj.getPetId();
                if (obj.getPetType().equals("Dog")) {
                    showDogDb.child(petId).child("connections").child("nopes").child(currentUid).setValue(true);
                }
                if (obj.getPetType().equals("Cat")) {
                    showCatDb.child(petId).child("connections").child("nopes").child(currentUid).setValue(true);
                }
                usersDb.child(currentUid).child("connections").child("nopes").child(petId).setValue(true);
                Toast.makeText(PetMainActivity.this, "left", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onRightCardExit(Object dataObject) {
                Card obj = (Card) dataObject;
                String petId = obj.getPetId();
                String petType = obj.getPetType();
                String petOwnerId = obj.getOwnerId();
                if (obj.getPetType().equals("Dog")) {
                    showDogDb.child(petId).child("connections").child("yeps").child(currentUid).setValue(true);
                }
                if (obj.getPetType().equals("Cat")) {
                    showCatDb.child(petId).child("connections").child("yeps").child(currentUid).setValue(true);
                }
                usersDb.child(currentUid).child("connections").child("yeps").child(petId).setValue(true);
                usersDb.child(currentUid).child("connections").child("yeps").child(petId).child(petType).setValue(true);

                String key = FirebaseDatabase.getInstance().getReference().child("Chat").push().getKey();

                usersDb.child(currentUid).child("connections").child("owners").child(petOwnerId).child("ChatId").setValue(key);
                usersDb.child(petOwnerId).child("connections").child("owners").child(currentUid).child("ChatId").setValue(key);

                Toast.makeText(PetMainActivity.this, "right", Toast.LENGTH_SHORT).show();


            }

            @Override
            public void onAdapterAboutToEmpty(int itemsInAdapter) {
            }

            @Override
            public void onScroll(float scrollProgressPercent) {
            }
        });


        addToCards();

        flingContainer.setOnItemClickListener(new SwipeFlingAdapterView.OnItemClickListener() {
            @Override
            public void onItemClicked(int itemPosition, Object dataObject) {
                Toast.makeText(PetMainActivity.this, "click", Toast.LENGTH_SHORT).show();
            }

        });

    }

    final DatabaseReference showDogDb = FirebaseDatabase.getInstance().getReference().child("Animals").child("Dog");
    final DatabaseReference showCatDb = FirebaseDatabase.getInstance().getReference().child("Animals").child("Cat");
       public void addToCards(){
            showDogDb.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                    if (dataSnapshot.exists() && !dataSnapshot.child("owner").getValue().toString().equals(currentUid)){
                        if (!dataSnapshot.child("connections").child("nopes").hasChild(currentUid) && !dataSnapshot.child("connections").child("yeps").hasChild(currentUid)) {
                            Card card = new Card("Dog",dataSnapshot.getKey(), dataSnapshot.child("owner").getValue().toString(), dataSnapshot.child("sex").getValue().toString(), dataSnapshot.child("imageUrl").getValue().toString());
                            rowItems.add(card);
                            arrayAdapter.notifyDataSetChanged();
                        }
                    }
                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {}
                @Override
                public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {}
                @Override
                public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {}
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {}
            });

            showCatDb.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                    if (dataSnapshot.exists() && !dataSnapshot.child("owner").getValue().toString().equals(currentUid)){
                        if (!dataSnapshot.child("connections").child("nopes").hasChild(currentUid) && !dataSnapshot.child("connections").child("yeps").hasChild(currentUid)) {
                            Card card = new Card("Cat", dataSnapshot.getKey(), dataSnapshot.child("owner").getValue().toString(), dataSnapshot.child("sex").getValue().toString(), dataSnapshot.child("imageUrl").getValue().toString());
                            rowItems.add(card);
                            arrayAdapter.notifyDataSetChanged();
                        }
                    }
                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {}
                @Override
                public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {}
                @Override
                public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {}
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {}
            });
        }


    public void logoutUser(View view) {
        mAuth.signOut();
        Intent intent = new Intent(PetMainActivity.this, ChooseLoginRegistrationActivity.class);
        startActivity(intent);
        finish();
    }

    public void goToSettings(View view) {
        Intent intent = new Intent(PetMainActivity.this, SettingsActivity.class);
        startActivity(intent);
    }

    public void goToFavorites(View view) {
        Intent intent = new Intent(PetMainActivity.this, FavoritesActivity.class);
        startActivity(intent);
    }
}