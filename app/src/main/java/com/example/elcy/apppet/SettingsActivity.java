package com.example.elcy.apppet;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class SettingsActivity extends AppCompatActivity {

    private EditText mName, mPhone, mNeighborhood, mCity;
    private Button mConfirm, mBack;
    private ImageView mImage;

    private FirebaseAuth mAuth;
    private DatabaseReference mCustomerDb;

    private String userId, name, phone, neighborhood, city, profileImageUrl;

    private Uri resultUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        mName = findViewById(R.id.profileName);
        mPhone = findViewById(R.id.profilePhone);
        mNeighborhood = findViewById(R.id.profileNeighborhood);
        mCity = findViewById(R.id.profileCity);

        mConfirm = findViewById(R.id.profileConfirm);
        mBack = findViewById(R.id.profileBack);

        mImage = findViewById(R.id.profileImage);

        mAuth = FirebaseAuth.getInstance();
        userId = mAuth.getCurrentUser().getUid();

        mCustomerDb = FirebaseDatabase.getInstance().getReference().child("Users").child(userId);

        getUserInformation();

        mImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, 1);
            }
        });

        mConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveUserInformation();
            }
        });
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void getUserInformation() {
        mCustomerDb.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists() && dataSnapshot.getChildrenCount()>0){
                    Map <String, Object> map = (Map<String, Object>) dataSnapshot.getValue();
                    if (map.get("name") != null){
                        name = map.get("name").toString();
                        mName.setText(name);
                    }
                    if (map.get("phone") != null){
                        phone = map.get("phone").toString();
                        mPhone.setText(phone);
                    }
                    if (map.get("neighborhood") != null){
                        neighborhood = map.get("neighborhood").toString();
                        mNeighborhood.setText(neighborhood);
                    }
                    if (map.get("city") != null){
                        city = map.get("city").toString();
                        mCity.setText(city);
                    }
                    if (map.get("profileImageUrl") != null){
                        profileImageUrl = map.get("profileImageUrl").toString();
                        Glide.with(getApplication()).load(profileImageUrl).into(mImage);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void saveUserInformation() {
        name = mName.getText().toString();
        phone = mPhone.getText().toString();
        neighborhood = mNeighborhood.getText().toString();
        city = mCity.getText().toString();

        Map userInfo = new HashMap();
        userInfo.put("name", name);
        userInfo.put("phone", phone);
        userInfo.put("neighborhood", neighborhood);
        userInfo.put("city", city);
        mCustomerDb.updateChildren(userInfo);
        if (resultUri != null){
            StorageReference filepath = FirebaseStorage.getInstance().getReference().child("profileImages").child(userId);
            Bitmap bitmap = null;

            try {
                bitmap = MediaStore.Images.Media.getBitmap(getApplication().getContentResolver(), resultUri);
            } catch (IOException e) {
                e.printStackTrace();
            }

            //diminuir a imagem
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 20, baos);
            byte[] data = baos.toByteArray();
            UploadTask uploadTask = filepath.putBytes(data);
            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    finish();
                }
            });
            uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Uri downloadUrl = taskSnapshot.getDownloadUrl();

                    Map userInfo = new HashMap();
                    userInfo.put("profileImageUrl", downloadUrl.toString());
                    mCustomerDb.updateChildren(userInfo);

                    //finish();
                }
            });
        }
        else {
            finish();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1 && resultCode == Activity.RESULT_OK){
            final Uri imageUri = data.getData();
            resultUri = imageUri;
            mImage.setImageURI(resultUri);
        }
    }
}
