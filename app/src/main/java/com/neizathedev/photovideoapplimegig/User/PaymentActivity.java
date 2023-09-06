package com.neizathedev.photovideoapplimegig.User;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.neizathedev.photovideoapplimegig.Authentication.LoginActivity;
import com.neizathedev.photovideoapplimegig.R;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class PaymentActivity extends AppCompatActivity {

    private EditText cardNumberEditText;
    private EditText expiryDateEditText;
    private EditText cvvEditText, chargeEditText;
    private Button payButton;

    /********************************************************/

    // Firebase
    FirebaseFirestore firestore;
    FirebaseAuth firebaseAuth;
    public static final String TAG = "TAG";

    /********************************************************/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        /********************************************************/
        // Instantiate Firestore and authentication services
        firebaseAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        /********************************************************/

        // Initialize views
        cardNumberEditText = findViewById(R.id.cardNumberEditText);
        expiryDateEditText = findViewById(R.id.expiryDateEditText);
        cvvEditText = findViewById(R.id.cvvEditText);
        chargeEditText = findViewById(R.id.chargeEditText);
        payButton = findViewById(R.id.payButton);

        // Handle payment button click
        payButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Retrieve card details
                String cardNumber = cardNumberEditText.getText().toString().trim();
                String expiryDate = expiryDateEditText.getText().toString().trim();
                String cvv = cvvEditText.getText().toString().trim();
                String amountp = chargeEditText.toString().trim();

                // Validate card details
                if (TextUtils.isEmpty(cardNumber) || TextUtils.isEmpty(expiryDate) || TextUtils.isEmpty(cvv) || TextUtils.isEmpty(amountp)) {
                    Toast.makeText(PaymentActivity.this, "Please enter all card details", Toast.LENGTH_SHORT).show();
                    return;
                }

                // ...
                FirebaseUser currentUser = firebaseAuth.getCurrentUser();
                if (currentUser != null) {
                    String userId = currentUser.getUid();

                    // Reference to "user_pay" collection
                    DocumentReference userPayRef = firestore.collection("user_pay").document(userId);

                    // Reference to "user" collection
                    DocumentReference userRef = firestore.collection("user").document(userId);

                    Map<String, Object> paymentData = new HashMap<>();
                    paymentData.put("email", currentUser.getEmail());
                    paymentData.put("cardNumber", cardNumber);
                    paymentData.put("expiryDate", expiryDate);
                    paymentData.put("cvv", cvv);
                    paymentData.put("amount", amountp);
                    paymentData.put("date", new Date()); // Default date is set to the current date and time

                    // Write to "user_pay" collection
                    userPayRef.set(paymentData, SetOptions.merge())
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    // Payment data saved successfully in "user_pay" collection
                                    Toast.makeText(PaymentActivity.this, "Payment made", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    // An error occurred while saving the payment data in "user_pay" collection
                                    Toast.makeText(PaymentActivity.this, "Failed to make payment", Toast.LENGTH_SHORT).show();
                                    Log.e(TAG, "Error saving payment data in user_pay collection", e);
                                }
                            });

                    // Write to "user" collection
                    userRef.set(paymentData, SetOptions.merge())
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    // Payment data saved successfully in "user" collection
                                    Intent x = new Intent(PaymentActivity.this, UserDashboardActivity.class);
                                    startActivity(x);
                                    finish();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    // An error occurred while saving the payment data in "user" collection
                                    Toast.makeText(PaymentActivity.this, "Failed to make payment", Toast.LENGTH_SHORT).show();
                                    Log.e(TAG, "Error saving payment data in user collection", e);
                                }
                            });
                }
// ...

            }
        });
    }

    private void makePayment(String cardNumber, String expiryDate, String cvv, String pay) {
        // TODO: Implement payment processing logic here

        // Show payment success message
        Toast.makeText(this, "Payment Successful!", Toast.LENGTH_SHORT).show();
        Intent x = new Intent(PaymentActivity.this, LoginActivity.class);
        startActivity(x);
    }
}
