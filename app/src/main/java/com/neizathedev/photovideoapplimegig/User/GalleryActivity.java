package com.neizathedev.photovideoapplimegig.User;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.neizathedev.photovideoapplimegig.Model.MediaFile;
import com.neizathedev.photovideoapplimegig.R;


public class GalleryActivity extends AppCompatActivity {

    private static final int PICK_MEDIA_REQUEST = 1;

    private Button selectMediaButton;
    private Button uploadButton;
    private ImageView selectedImageView;

    private Uri mediaUri;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private FirebaseFirestore db;
    private StorageReference storageRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        selectMediaButton = findViewById(R.id.selectMediaButton);
        uploadButton = findViewById(R.id.uploadButton);
        selectedImageView = findViewById(R.id.selectedImageView);

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        db = FirebaseFirestore.getInstance();
        storageRef = FirebaseStorage.getInstance().getReference();

        selectMediaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMediaChooser();
            }
        });

        uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadFile();
            }
        });
    }

    private void openMediaChooser() {
        Intent intent = new Intent();
        intent.setType("*/*"); // Allow all file types
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Media"), PICK_MEDIA_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_MEDIA_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            mediaUri = data.getData();
            selectedImageView.setImageURI(mediaUri);
        }
    }

    private void uploadFile() {
        if (mediaUri != null) {
            StorageReference fileRef = storageRef.child("media/" + currentUser.getUid() + "/" + System.currentTimeMillis() + getExtension(mediaUri));
            fileRef.putFile(mediaUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri downloadUri) {
                                    saveFileToFirestore(downloadUri.toString());
                                    Toast.makeText(GalleryActivity.this, "File uploaded successfully", Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                            });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(GalleryActivity.this, "Upload failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        } else {
            Toast.makeText(this, "No file selected", Toast.LENGTH_SHORT).show();
        }
    }

    private void saveFileToFirestore(String fileUrl) {
        String fileName = "Media File"; // Customize this to set a meaningful file name for your app

        MediaFile mediaFile = new MediaFile(currentUser.getEmail(), fileName, fileUrl, getFileType(mediaUri)); // Use getEmail() instead of getUid()

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("user_media")
                .add(mediaFile)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        // File information saved to Firestore
                        // Add the email field to the document
                        documentReference.update("email", currentUser.getEmail())
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        // Email field updated successfully
                                        Toast.makeText(GalleryActivity.this, "File uploaded successfully", Toast.LENGTH_SHORT).show();
                                        finish();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        // Failed to update email field
                                        Toast.makeText(GalleryActivity.this, "Failed to update email field", Toast.LENGTH_SHORT).show();
                                    }
                                });
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Failed to save file information to Firestore
                        Toast.makeText(GalleryActivity.this, "Failed to save file information", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private String getExtension(Uri uri) {
        // Get the file extension from the URI
        String extension = "";
        String path = uri.getPath();
        if (path != null) {
            int dotIndex = path.lastIndexOf(".");
            if (dotIndex != -1 && dotIndex < path.length() - 1) {
                extension = path.substring(dotIndex);
            }
        }
        return extension;
    }

    private String getFileType(Uri uri) {
        // Get the media file type based on the MIME type
        String type = getContentResolver().getType(uri);
        if (type != null) {
            if (type.startsWith("image")) {
                return "image";
            } else if (type.startsWith("video")) {
                return "video";
            }
        }
        return "";
    }
}
