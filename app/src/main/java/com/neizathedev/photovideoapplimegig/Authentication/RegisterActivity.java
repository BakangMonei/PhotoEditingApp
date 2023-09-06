package com.neizathedev.photovideoapplimegig.Authentication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import androidx.annotation.NonNull;

import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.neizathedev.photovideoapplimegig.R;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: Monei Bakang Mothuti
 * @Date: Wednesday July 2023
 * @Time: 9:29 PM
 */
public class RegisterActivity extends AppCompatActivity {

    EditText inputUsername, inputEmail, inputPassword, inputConformPassword;
    Button btnRegister;
    TextView alreadyHaveAccount;
    ProgressBar progressBaar;
    String userId, username, email, password, repassword;
    public static final String TAG = "TAG";

    /*********************************************/
    FirebaseAuth fireAuth;
    FirebaseFirestore fireStore;

    /*********************************************/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        /*********************************************/
        //Instantiate Database and authentication Services
        fireAuth = FirebaseAuth.getInstance();
        fireStore = FirebaseFirestore.getInstance();
        /*********************************************/

        inputUsername = (EditText) findViewById(R.id.inputUsername);
        inputEmail = (EditText) findViewById(R.id.inputEmail);
        inputPassword = (EditText) findViewById(R.id.inputPassword);
        inputConformPassword = (EditText) findViewById(R.id.inputConformPassword);

        /*********************************************/
        alreadyHaveAccount = (TextView) findViewById(R.id.alreadyHaveAccount);
        alreadyHaveAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToLoginActivity();
            }
        });
        /*********************************************/
        progressBaar = (ProgressBar) findViewById(R.id.progressBaar);
        btnRegister = (Button) findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Fetch input data from Edit text views
                username = inputUsername.getText().toString().trim();
                email = inputEmail.getText().toString().trim();
                password = inputPassword.getText().toString().trim();
                repassword = inputConformPassword.getText().toString().trim();

                // Form Validation
                if (TextUtils.isEmpty(username)) {
                    Toast.makeText(getApplicationContext(), "Enter Username!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(password) || TextUtils.isEmpty(repassword)) {
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (password.length() < 6 || repassword.length() < 6) {
                    Toast.makeText(getApplicationContext(), "Password too short, enter minimum 6 characters!", Toast.LENGTH_SHORT).show();
                    return;
                }
                progressBaar.setVisibility(View.VISIBLE);
                //Create and Store user authentication credentials
                fireAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "Account successfully created.", Toast.LENGTH_SHORT).show();
                            // Store user details:
                            userId = fireAuth.getCurrentUser().getUid();

                            DocumentReference userInformation = fireStore.collection("user").document(userId);

                            Map<String, Object> user = new HashMap<>();
                            user.put("username", username);
                            user.put("email", email);

                            userInformation.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(getApplicationContext(), "User Account Created ", Toast.LENGTH_SHORT).show();
                                    Log.d(TAG, "User details successfully stored.");
                                    System.out.println("This was accessed");
                                }
                            });
                            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                        } else {
                            Toast.makeText(getApplicationContext(), "Error! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            progressBaar.setVisibility(View.GONE);
                        }
                    }
                });
            }
        });
        // Ends here
        /*********************************************/
    }

    public void goToLoginActivity() {
        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(intent);
    }
}