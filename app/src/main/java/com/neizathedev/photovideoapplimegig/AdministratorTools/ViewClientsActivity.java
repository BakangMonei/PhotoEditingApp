package com.neizathedev.photovideoapplimegig.AdministratorTools;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.neizathedev.photovideoapplimegig.Adapters.UsersAdapter;
import com.neizathedev.photovideoapplimegig.Model.UserAccount;
import com.neizathedev.photovideoapplimegig.R;
import com.neizathedev.photovideoapplimegig.SplashScreenActivity;

import android.Manifest;
import android.util.Log;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

public class ViewClientsActivity extends AppCompatActivity {
    private static final String TAG = SplashScreenActivity.class.getSimpleName();
    public static final int MULTIPLE_PERMISSIONS = 101;
    private RecyclerView recyclerView;
    UsersAdapter adapter; // Create Object of the Adapter class
    CollectionReference userCollection; // Create object of the user collection

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_clients);
        // Ask for runtime permission
        checkPermissions();

        // Create a instance of the Firestore database and get the user collection reference
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        userCollection = firestore.collection("user");

        recyclerView = findViewById(R.id.recycler1);

        // To display the Recycler view linearly
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Create FirestoreRecyclerOptions
        Query query = userCollection;
        FirestoreRecyclerOptions<UserAccount> options = new FirestoreRecyclerOptions.Builder<UserAccount>()
                .setQuery(query, UserAccount.class)
                .build();

        // Create an instance of the UsersAdapter
        adapter = new UsersAdapter(options);

        // Set the adapter to the RecyclerView
        recyclerView.setAdapter(adapter);
    }

    // Function to start listening to Firestore changes when the activity starts
    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    // Function to stop listening to Firestore changes when the activity stops
    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    // For making calls
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case MULTIPLE_PERMISSIONS:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    // For runtime Permission
    private boolean checkPermissions() {
        int result;
        String[] permissions = new String[]{Manifest.permission.CALL_PHONE};

        List<String> listPermissionsNeeded = new ArrayList<>();
        for (String p : permissions) {
            result = ContextCompat.checkSelfPermission(this, p);
            if (result != PackageManager.PERMISSION_GRANTED) {
                Log.d(TAG, "Ask Permissions : " + p);
                listPermissionsNeeded.add(p);
            }
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), MULTIPLE_PERMISSIONS);
            return false;
        }
        return true;
    }
}
