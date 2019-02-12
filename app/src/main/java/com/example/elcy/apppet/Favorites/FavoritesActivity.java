package com.example.elcy.apppet.Favorites;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.elcy.apppet.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FavoritesActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mFavoriteAdapter;
    private RecyclerView.LayoutManager mFavoriteLayoutManager;

    private String currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);

        currentUser = FirebaseAuth.getInstance().getCurrentUser().getUid();

        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setNestedScrollingEnabled(false);
        mRecyclerView.setHasFixedSize(true);
        mFavoriteLayoutManager = new LinearLayoutManager(FavoritesActivity.this);
        mRecyclerView.setLayoutManager(mFavoriteLayoutManager);
        mFavoriteAdapter = new FavoritesAdapter(FavoritesActivity.this, getDataSetFavorite());
        mRecyclerView.setAdapter(mFavoriteAdapter);

        getPetId();
    }

    private void getPetId() {
        DatabaseReference favoritePetsDb = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUser).child("connections").child("yeps");
        favoritePetsDb.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    for(final DataSnapshot petId : dataSnapshot.getChildren()){
                        final DatabaseReference petDb = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUser).child("connections").child("yeps").child(petId.getKey());
                        petDb.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                if (dataSnapshot.exists()){
                                    String petType = dataSnapshot.getValue().toString();
                                    if (petType.equals("{Dog=true}")){
                                        FetchPetInformation("Dog", petId.getKey());
                                    }
                                    if (petType.equals("{Cat=true}")){
                                        FetchPetInformation("Cat", petId.getKey());
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });

                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void FetchPetInformation(String type, String key) {
        DatabaseReference petDb = FirebaseDatabase.getInstance().getReference().child("Animals").child(type).child(key);
        petDb.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    String petId = dataSnapshot.getKey();
                    String petImageUrl = "";
                    String sex = "";
                    String age = "";
                    String owner = "";
                    if(dataSnapshot.child("imageUrl") != null){
                        petImageUrl = dataSnapshot.child("imageUrl").getValue().toString();
                    }
                    if(dataSnapshot.child("sex") != null){
                        sex = dataSnapshot.child("sex").getValue().toString();
                    }
                    if(dataSnapshot.child("age") != null){
                        age = dataSnapshot.child("age").getValue().toString();
                    }
                    if(dataSnapshot.child("owner") != null){
                        owner = dataSnapshot.child("owner").getValue().toString();

                    }

                    FavoritesObject obj = new FavoritesObject(petId, petImageUrl, sex, age, owner);
                    resultFavorites.add(obj);
                    mFavoriteAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    private ArrayList<FavoritesObject> resultFavorites = new ArrayList<FavoritesObject>();
    private List<FavoritesObject> getDataSetFavorite() {
        return resultFavorites;
    }

}
