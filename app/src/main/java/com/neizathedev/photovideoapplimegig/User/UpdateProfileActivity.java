package com.neizathedev.photovideoapplimegig.User;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;

import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

import android.util.Log;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.SetOptions;
import com.neizathedev.photovideoapplimegig.R;

import java.util.Map;

public class UpdateProfileActivity extends AppCompatActivity {

    /********************************************************/

    // Firebase
    FirebaseFirestore fireStore;
    FirebaseAuth fireAuth;
    public static final String TAG = "TAG";

    /********************************************************/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);

        /********************************************************/
        //Instantiate Firestore and authentication Services
        fireAuth = FirebaseAuth.getInstance();
        fireStore = FirebaseFirestore.getInstance();
        /********************************************************/

        // EditText
        EditText firstNameEditText = findViewById(R.id.firstNameEditText); // Done
        EditText lastNameEditText = findViewById(R.id.lastNameEditText); // Done
        EditText genderEditText = findViewById(R.id.genderEditText); // Done
        EditText DOBEditText = findViewById(R.id.DOBEditText); // Done
        EditText omangEditText = findViewById(R.id.omangEditText); // Done
        EditText countryEditText = findViewById(R.id.countryEditText); // Done
        EditText postalAddressEditText = findViewById(R.id.postalAddressEditText); // Done
        EditText physicalAddressEditText = findViewById(R.id.physicalAddressEditText); // Done
        EditText phoneEditText = findViewById(R.id.phoneEditText); // Done
        EditText occupationEditText = findViewById(R.id.occupationEditText); // Done
        EditText cardNumberEditText = findViewById(R.id.cardNumberEditText);
        EditText cvcEditText = findViewById(R.id.cvcEditText);
        EditText expiryDateEditText = findViewById(R.id.expiryDateEditText);
        EditText amountEditText = findViewById(R.id.amountEditText);

        Button updateBtn = findViewById(R.id.updateBtn);
        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String firstName = firstNameEditText.getText().toString().trim();
                String lastName = lastNameEditText.getText().toString().trim();
                String gender = genderEditText.getText().toString().trim();
                String dob = DOBEditText.getText().toString().trim();
                String postal_address = postalAddressEditText.getText().toString().trim();
                String physical_address = physicalAddressEditText.getText().toString().trim();
                String phone = phoneEditText.getText().toString().trim();
                String omang = omangEditText.getText().toString().trim();
                String country = countryEditText.getText().toString().trim();
                String occupation = occupationEditText.getText().toString().trim();
                String card = cardNumberEditText.getText().toString().trim();
                String cvc = cvcEditText.getText().toString().trim();
                String expiryDate = expiryDateEditText.getText().toString().trim();
                String pay = amountEditText.getText().toString().trim();

                FirebaseUser currentUser = fireAuth.getCurrentUser();
                if (currentUser != null) {
                    String userId = currentUser.getUid();
                    DocumentReference userRef = fireStore.collection("user").document(userId);

                    Map<String, Object> profileData = new HashMap<>();
                    profileData.put("firstName", firstName);
                    profileData.put("lastName", lastName);
                    profileData.put("gender", gender);
                    profileData.put("dob", dob);
                    profileData.put("physical_address", physical_address);
                    profileData.put("postal_address", postal_address);
                    profileData.put("occupation", occupation);
                    profileData.put("card_number", card);
                    profileData.put("phone", phone);
                    profileData.put("omang", omang);
                    profileData.put("country", country);
                    profileData.put("cvc", cvc);
                    profileData.put("expiry_date", expiryDate);
                    profileData.put("amount", pay);


                    userRef.set(profileData, SetOptions.merge())
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    // Profile updated successfully
                                    Toast.makeText(UpdateProfileActivity.this, "Profile updated", Toast.LENGTH_SHORT).show();
                                    Intent x = new Intent(UpdateProfileActivity.this, UserDashboardActivity.class);
                                    startActivity(x);
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    // An error occurred while updating the profile
                                    Toast.makeText(UpdateProfileActivity.this, "Failed to update profile", Toast.LENGTH_SHORT).show();
                                    Log.e(TAG, "Error updating profile", e);
                                }
                            });
                }
            }
        });

    }
}