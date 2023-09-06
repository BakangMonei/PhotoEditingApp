package com.neizathedev.photovideoapplimegig.Authentication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.neizathedev.photovideoapplimegig.Administrator.AdminDashBoardActivity;
import com.neizathedev.photovideoapplimegig.R;
import com.neizathedev.photovideoapplimegig.User.UserDashboardActivity;

/**
 * @Author: Monei Bakang Mothuti
 * @Date: Wednesday July 2023
 * @Time: 9:29 PM
 */
public class LoginActivity extends AppCompatActivity {
    /*********************************************/
    // For google sign-in
    GoogleSignInOptions gso;
    GoogleSignInClient gsc;
    /*********************************************/
    // For App sign-in
    private FirebaseAuth mAuth;
    private FirebaseDatabase database;

    /*********************************************/

    Button btnlogin, btnGoogle, btnFacebook;
    EditText inputEmail, inputPassword;
    TextView forgotPassword, textViewSignUp;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        /*********************************************/
        // Google SignIn
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        gsc = GoogleSignIn.getClient(this, gso);

        btnGoogle = (Button) findViewById(R.id.btnGoogle);
        btnGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SignIn();
            }
        });
        /*********************************************/
        // Facebook Login
        btnFacebook = (Button) findViewById(R.id.btnFacebook);

        /*********************************************/
        // Firebase Implementation
        mAuth = FirebaseAuth.getInstance();
        // Application Login

        btnlogin = (Button) findViewById(R.id.btnlogin);
        inputEmail = (EditText) findViewById(R.id.inputEmail);
        inputPassword = (EditText) findViewById(R.id.inputPassword);

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Fetch input data from Edit text views
                String email = inputEmail.getText().toString().trim();
                String pass = inputPassword.getText().toString().trim();

                // Validating the email & password
                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(email.equals("admin@app.com") && pass.equals("admin@1234")){
                    goToAdmins();
                }
                if (TextUtils.isEmpty(pass)) {
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (pass.length() < 6) {
                    Toast.makeText(getApplicationContext(), "Password too short, enter minimum 6 characters!", Toast.LENGTH_SHORT).show();
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);
                mAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            progressBar.setVisibility(View.INVISIBLE);
                            FirebaseUser user = mAuth.getCurrentUser();
                            finish();
                            goToDashboard();
                        }
                        else if (task.isSuccessful()) {
                            progressBar.setVisibility(View.INVISIBLE);
                            // FirebaseUser admin = mAuth.getUid();
                            // finish();
                            // goToAdmins();
                        } else {
                            progressBar.setVisibility(View.INVISIBLE);
                            Toast.makeText(LoginActivity.this, "Sign in failed." + task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        /*********************************************/
        // Forgot Password
        forgotPassword = (TextView) findViewById(R.id.forgotPassword); // Done
        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent a = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
                startActivity(a);
            }
        });

        /*********************************************/
        // Registration [SignUp]
        textViewSignUp = (TextView) findViewById(R.id.textViewSignUp); // Done
        textViewSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent register = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(register);
            }
        });
        /*********************************************/
    }

    /*********************************************/
    // This are for signing in with Google
    private void SignIn() {
        Intent intent = gsc.getSignInIntent();
        startActivityForResult(intent, 100);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 100) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                task.getResult(ApiException.class);
                HomeActivity();
            } catch (ApiException e) {
                Toast.makeText(this, "Error " + e, Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void HomeActivity() {
        finish();
        Intent intent = new Intent(getApplicationContext(), UserDashboardActivity.class);
        startActivity(intent);
    }

    // Signing in with google ends here

    /*********************************************/

    public void goToDashboard() {
        Intent intent = new Intent(LoginActivity.this, UserDashboardActivity.class);
        startActivity(intent);
    }

    public void goToAdmins(){
        Intent x = new Intent(LoginActivity.this, AdminDashBoardActivity.class);
        startActivity(x);
    }
}