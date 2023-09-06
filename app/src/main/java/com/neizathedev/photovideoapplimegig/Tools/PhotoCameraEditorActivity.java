package com.neizathedev.photovideoapplimegig.Tools;
// Final Activity

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.neizathedev.photovideoapplimegig.R;

import com.neizathedev.photovideoapplimegig.User.UserDashboardActivity;

public class PhotoCameraEditorActivity extends AppCompatActivity {

    Button goBackHomePhotoButton;
    private static final int REQUEST_PERMISSION = 1;
    private static final int REQUEST_PICK_IMAGE = 2;
    private static final int EDIT_IMAGE_REQUEST_CODE = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_camera_editor);

        goBackHomePhotoButton = (Button) findViewById(R.id.goBackHomePhotoButton);
        goBackHomePhotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent c = new Intent(PhotoCameraEditorActivity.this, UserDashboardActivity.class);
                startActivity(c);
            }
        });

        // Request storage permission if not granted
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_PERMISSION);
        } else {
            openImageGallery();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openImageGallery();
            } else {
                Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void openImageGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(intent, REQUEST_PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_PICK_IMAGE && resultCode == Activity.RESULT_OK) {
            Uri selectedImageUri = data.getData();

            // Perform your image editing operations here using the selectedImageUri

            // For example, you can start the image editing activity:
            Intent editIntent = new Intent(Intent.ACTION_EDIT);
            editIntent.setDataAndType(selectedImageUri, "image/*");
            editIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

            // Start the image editing activity
            startActivityForResult(editIntent, EDIT_IMAGE_REQUEST_CODE);
        } else if (requestCode == EDIT_IMAGE_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            // Editing completed, open the UserDashboardActivity
            Intent dashboardIntent = new Intent(PhotoCameraEditorActivity.this, UserDashboardActivity.class);
            startActivity(dashboardIntent);
            finish(); // Finish the current activity to prevent going back to it on back press
        }
    }
}

