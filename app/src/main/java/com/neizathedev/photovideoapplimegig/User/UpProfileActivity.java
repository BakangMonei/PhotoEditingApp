package com.neizathedev.photovideoapplimegig.User;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.neizathedev.photovideoapplimegig.R;

import java.util.HashMap;
import java.util.Map;

public class UpProfileActivity extends AppCompatActivity {
    /********************************************************/
    // Firebase
    FirebaseFirestore fireStore;
    FirebaseAuth fireAuth;
    public static final String TAG = "TAG";
    /********************************************************/


    private TextView titleTextView;
    private TextInputLayout firstNameEditText;
    private TextInputLayout lastNameEditText;
    private TextInputLayout genderEditText;
    private TextInputLayout DOBEditText;
    private TextInputLayout physicalAddressEditText;
    private TextInputLayout postalAddressEditText;
    private TextInputLayout phoneEditText;
    private TextInputLayout omangEditText;
    private TextInputLayout countryEditText;
    private TextInputLayout occupationEditText;
    private TextInputLayout cardNumberEditText;
    private TextInputLayout cvcEditText;
    private TextInputLayout expiryDateEditText;
    private TextInputLayout amountEditText;
    private Button updateBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_up_profile);

        /********************************************************/
        //Instantiate Firestore and authentication Services
        fireAuth = FirebaseAuth.getInstance();
        fireStore = FirebaseFirestore.getInstance();
        /********************************************************/

        titleTextView = findViewById(R.id.titleTextView);
        firstNameEditText = findViewById(R.id.firstNameEditText);
        lastNameEditText = findViewById(R.id.lastNameEditText);
        phoneEditText = findViewById(R.id.phoneEditText);
        genderEditText = findViewById(R.id.genderEditText);
        DOBEditText = findViewById(R.id.DOBEditText);
        physicalAddressEditText = findViewById(R.id.physicalAddressEditText);
        postalAddressEditText = findViewById(R.id.postalAddressEditText);

        omangEditText = findViewById(R.id.omangEditText);
        countryEditText = findViewById(R.id.countryEditText);
        occupationEditText = findViewById(R.id.occupationEditText);
        cardNumberEditText = findViewById(R.id.cardNumberEditText);
        cvcEditText = findViewById(R.id.cvcEditText);
        expiryDateEditText = findViewById(R.id.expiryDateEditText);
        amountEditText = findViewById(R.id.amountEditText);
        updateBtn = findViewById(R.id.updateBtn);

        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String firstName = firstNameEditText.getEditText().getText().toString().trim();
                String lastName = lastNameEditText.getEditText().getText().toString().trim();
                String gender = genderEditText.getEditText().getText().toString().trim();
                String dob = DOBEditText.getEditText().getText().toString().trim();
                String postal_address = postalAddressEditText.getEditText().getText().toString().trim();
                String physical_address = physicalAddressEditText.getEditText().getText().toString().trim();
                String phone = phoneEditText.getEditText().getText().toString().trim();
                String omang = omangEditText.getEditText().getText().toString().trim();
                String country = countryEditText.getEditText().getText().toString().trim();
                String occupation = occupationEditText.getEditText().getText().toString().trim();
                String card = cardNumberEditText.getEditText().getText().toString().trim();
                String cvc = cvcEditText.getEditText().getText().toString().trim();
                String expiryDate = expiryDateEditText.getEditText().getText().toString().trim();
                String pay = amountEditText.getEditText().getText().toString().trim();

                FirebaseUser currentUser = fireAuth.getCurrentUser();
                if (currentUser != null) {
                    String userId = currentUser.getUid();
                    DocumentReference userRef = fireStore.collection("user").document(userId);

                    Map<String, Object> profileData = new HashMap<>();
                    profileData.put("firstName", firstName);
                    profileData.put("lastName", lastName);
                    profileData.put("gender", gender);
                    profileData.put("dob", dob);
                    profileData.put("physicalAddress", physical_address);
                    profileData.put("postalAddress", postal_address);
                    profileData.put("occupation", occupation);
                    profileData.put("phone", phone);
                    profileData.put("omang", omang);
                    profileData.put("country", country);



                    userRef.set(profileData, SetOptions.merge())
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    // Profile updated successfully
                                    Toast.makeText(UpProfileActivity.this, "Profile updated", Toast.LENGTH_SHORT).show();
                                    Intent x = new Intent(UpProfileActivity.this, UserDashboardActivity.class);
                                    startActivity(x);
                                    finish(); // Error might occur here
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    // An error occurred while updating the profile
                                    Toast.makeText(UpProfileActivity.this, "Failed to update profile", Toast.LENGTH_SHORT).show();
                                    Log.e(TAG, "Error updating profile", e);
                                }
                            });
                }
            }
        });
    }
}
