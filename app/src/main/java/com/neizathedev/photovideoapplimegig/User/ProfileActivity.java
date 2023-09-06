package com.neizathedev.photovideoapplimegig.User;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.neizathedev.photovideoapplimegig.R;

public class ProfileActivity extends AppCompatActivity {

    private TextView titleTextView;
    private MaterialTextView emailUserTextView;
    private MaterialTextView userTextView;
    private MaterialTextView firstNameTextView;
    private MaterialTextView lastNameTextView;
    private MaterialTextView genderTextView;
    private MaterialTextView dobTextView;
    private MaterialTextView physicalAddressTextView;
    private MaterialTextView postalAddressTextView;
    private MaterialTextView phoneTextView;
    private MaterialTextView omangTextView;
    private MaterialTextView countryTextView;
    private MaterialTextView occupationTextView;
    private MaterialTextView cardNumberTextView;
    private MaterialTextView cvcTextView;
    private MaterialTextView expiryDateTextView;
    private MaterialTextView amountTextView;
    private Button updateBtn;
    private Button deleteAccountBtn;


    /***************************************************/
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore mFirestore;
    private String UserId;
    /***************************************************/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        /***************************************************/
        firebaseAuth = FirebaseAuth.getInstance();
        mFirestore = FirebaseFirestore.getInstance();
        /***************************************************/

        titleTextView = findViewById(R.id.titleTextView);

        emailUserTextView = findViewById(R.id.emailUserTextView); // Done
        userTextView = findViewById(R.id.userTextView); // Done
        firstNameTextView = findViewById(R.id.firstNameTextView); // Done
        lastNameTextView = findViewById(R.id.lastNameTextView); // Done
        genderTextView = findViewById(R.id.genderTextView); // Done
        dobTextView = findViewById(R.id.dobTextView); // Done
        physicalAddressTextView = findViewById(R.id.physicalAddressTextView); // Done
        postalAddressTextView = findViewById(R.id.postalAddressTextView); // Done
        phoneTextView = findViewById(R.id.phoneTextView); // Done
        omangTextView = findViewById(R.id.omangTextView); // Done
        countryTextView = findViewById(R.id.countryTextView); // Done
        occupationTextView = findViewById(R.id.occupationTextView); // Done
        cardNumberTextView = findViewById(R.id.cardNumberTextView); // Done
        cvcTextView = findViewById(R.id.cvcTextView); // Done
        expiryDateTextView = findViewById(R.id.expiryDateTextView); // Done
        amountTextView = findViewById(R.id.amountTextView); // Done


        updateBtn = findViewById(R.id.updateBtn);
        deleteAccountBtn = findViewById(R.id.deleteAccountBtn);


        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent x = new Intent(ProfileActivity.this, UpProfileActivity.class);
                Toast.makeText(ProfileActivity.this, "Please fill everything again, this is for verification!! Thank You!!!!!", Toast.LENGTH_SHORT).show();
                startActivity(x);
            }
        });


        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        if (currentUser != null) {
            String userId = currentUser.getUid();
            String email = currentUser.getEmail();
            emailUserTextView.setText(email);

            mFirestore.collection("user").document(userId).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    String user_name = documentSnapshot.getString("username");
                    userTextView.setText(user_name);

                    String first_name = documentSnapshot.getString("firstName");
                    firstNameTextView.setText(first_name);

                    String last_name = documentSnapshot.getString("lastName");
                    lastNameTextView.setText(last_name);

                    String omang = documentSnapshot.getString("omang");
                    omangTextView.setText(omang);

                    String phone = documentSnapshot.getString("phone");
                    phoneTextView.setText(phone);

                    String gender = documentSnapshot.getString("gender");
                    genderTextView.setText(gender);

                    String country = documentSnapshot.getString("country");
                    countryTextView.setText(country);

                    String dob = documentSnapshot.getString("dob");
                    dobTextView.setText(dob);

                    String physicalAddress = documentSnapshot.getString("physicalAddress");
                    physicalAddressTextView.setText(physicalAddress);

                    String postalAddress = documentSnapshot.getString("postalAddress");
                    postalAddressTextView.setText(postalAddress);

                    String occupation = documentSnapshot.getString("occupation");
                    occupationTextView.setText(occupation);

                    String card = documentSnapshot.getString("cardNumber");
                    cardNumberTextView.setText(card);

                    String cvc = documentSnapshot.getString("cvc");
                    cvcTextView.setText(physicalAddress);

                    String expiryDate= documentSnapshot.getString("expiryDate");
                    expiryDateTextView.setText(expiryDate);

                    String amount = documentSnapshot.getString("amount");
                    amountTextView.setText(amount);
                }
            });
        }
    }
}