package com.neizathedev.photovideoapplimegig.AdministratorTools;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.VideoView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.neizathedev.photovideoapplimegig.R;

import java.util.List;

public class ViewResourcesActivity extends AppCompatActivity {

    private LinearLayout mediaLayout;

    private FirebaseFirestore db;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private FirebaseStorage storage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_resources);

        mediaLayout = (LinearLayout) findViewById(R.id.mediaLayout);

        // Firebase
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        storage = FirebaseStorage.getInstance();

        if (currentUser != null) {
            displayMediaFiles();
        }
    }

    private void displayMediaFiles() {
        db.collection("user")
                .whereEqualTo("email", currentUser.getUid())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            List<DocumentSnapshot> documents = task.getResult().getDocuments();
                            for (DocumentSnapshot document : documents) {
                                String fileType = document.getString("file_type");
                                String fileName = document.getString("file_name");
                                String fileUrl = document.getString("file_url");

                                if (fileType != null && fileType.equals("image")) {
                                    displayImage(fileUrl);
                                } else if (fileType != null && fileType.equals("video")) {
                                    displayVideo(fileUrl);
                                }
                            }
                        } else {
                            Log.d("TAG", "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

    private void displayImage(String imageUrl) {
        ImageView imageView = new ImageView(this);
        // Load the image using your preferred image loading library (e.g., Picasso, Glide)
        // For simplicity, we are not showing the image loading part here.
        // You can use libraries like Picasso or Glide to load the image from the URL into the ImageView.
        // Example:
        // Picasso.get().load(imageUrl).into(imageView);

        mediaLayout.addView(imageView);
    }

    private void displayVideo(String videoUrl) {
        VideoView videoView = new VideoView(this);
        videoView.setVideoPath(videoUrl);
        mediaLayout.addView(videoView);
        videoView.start();
    }
}