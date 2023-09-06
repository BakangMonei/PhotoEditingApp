package com.neizathedev.photovideoapplimegig.Tools;
// Final Activity

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

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

public class VideoEditorActivity extends AppCompatActivity {

    Button goBackHomeVideoButton;
    private static final int REQUEST_PERMISSION = 1;
    private static final int REQUEST_PICK_VIDEO = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_editor);
        goBackHomeVideoButton = (Button) findViewById(R.id.goBackHomeVideoButton);
        goBackHomeVideoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent a = new Intent(VideoEditorActivity.this, UserDashboardActivity.class);
                startActivity(a);
            }
        });

        // Request storage permission if not granted
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_PERMISSION);
        } else {
            openVideoGallery();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openVideoGallery();
            } else {
                Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void openVideoGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
        intent.setType("video/*");
        startActivityForResult(intent, REQUEST_PICK_VIDEO);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_PICK_VIDEO && resultCode == Activity.RESULT_OK) {
            Uri selectedVideoUri = data.getData();

            // Perform your video editing operations here using the selectedVideoUri

            // For example, you can start the video editing activity:
            Intent editIntent = new Intent(Intent.ACTION_EDIT);
            editIntent.setDataAndType(selectedVideoUri, "video/*");
            editIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            startActivity(editIntent);
        }
    }
}
