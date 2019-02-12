package com.example.elcy.apppet.Setup;

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
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.bumptech.glide.Glide;
import com.example.elcy.apppet.Models.Cat;
import com.example.elcy.apppet.Models.Dog;
import com.example.elcy.apppet.PetMainActivity;
import com.example.elcy.apppet.R;
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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class DonateActivity extends AppCompatActivity {

    private Button mAddPet;
    private EditText mAge;
    private RadioGroup mRadioAnimal, mRadioSexAnimal;
    private ImageView mImage;

    private Uri resultUri;

    private DatabaseReference currentAnimalDb;

    private FirebaseAuth mAuth;
    private String currentUid, petImageUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donate);

        mAuth = FirebaseAuth.getInstance();
        currentUid = mAuth.getCurrentUser().getUid();

        mAddPet = findViewById(R.id.add_pet);
        mAge = findViewById(R.id.animal_age);
        mRadioAnimal = findViewById(R.id.radio_animal);
        mRadioSexAnimal = findViewById(R.id.radio_sex_animal);
        mImage = findViewById(R.id.petImage);

        mImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, 1);
            }
        });

        mAddPet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Integer age = Integer.parseInt(mAge.getText().toString());
                int selectTypeId = mRadioAnimal.getCheckedRadioButtonId();
                int selectSexId = mRadioSexAnimal.getCheckedRadioButtonId();
                RadioButton radioType = findViewById(selectTypeId);
                RadioButton radioSex = findViewById(selectSexId);
                final String type = radioType.getText().toString();
                final String sex = radioSex.getText().toString();

                if (resultUri != null){
                    StorageReference filepath = FirebaseStorage.getInstance().getReference().child("petImages").child(currentUid);
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

                            if (type.equals("Cachorro")) {
                                currentAnimalDb = FirebaseDatabase.getInstance().getReference().child("Animals").child("Dog");
                                Dog dog = new Dog(currentUid,sex, age, downloadUrl.toString());
                                currentAnimalDb.push().setValue(dog);

                            }
                            if (type.equals("Gato")) {
                                currentAnimalDb = FirebaseDatabase.getInstance().getReference().child("Animals").child("Cat");
                                Cat cat = new Cat(currentUid, sex, age, downloadUrl.toString());
                                currentAnimalDb.push().setValue(cat);
                            }
                        }
                    });
                }
                else {
                    finish();
                }

                Intent intent = new Intent(DonateActivity.this, PetMainActivity.class);
                startActivity(intent);
                finish();
                return;

            }

            });
    }

    private void getImage() {
        currentAnimalDb.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists() && dataSnapshot.getChildrenCount()>0){
                    Map <String, Object> map = (Map<String, Object>) dataSnapshot.getValue();
                    if (map.get("petImageUrl") != null){
                        petImageUrl = map.get("petImageUrl").toString();
                        Glide.with(getApplication()).load(petImageUrl).into(mImage);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });
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