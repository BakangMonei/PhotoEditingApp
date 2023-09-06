package com.neizathedev.photovideoapplimegig.User;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Bundle;
import android.util.Log;
import android.webkit.MimeTypeMap;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.neizathedev.photovideoapplimegig.R;

import java.util.List;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;

import java.util.List;

import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class ViewResourcesActivity extends AppCompatActivity {

    private LinearLayout mediaLayout;
    private Handler handler;
    private Runnable longClickRunnable;

    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_resources);

        mediaLayout = findViewById(R.id.mediaLayout);
        handler = new Handler();
        db = FirebaseFirestore.getInstance();

        displayMediaFiles();
    }

    private void displayMediaFiles() {
        db.collection("user_media")
                .whereEqualTo("email", FirebaseAuth.getInstance().getCurrentUser().getEmail())
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        List<DocumentSnapshot> documents = task.getResult().getDocuments();
                        for (DocumentSnapshot document : documents) {
                            String fileType = document.getString("fileType");
                            String fileName = document.getString("fileName");
                            String fileUrl = document.getString("fileUrl");

                            if (fileType != null) {
                                if (fileType.equals("image")) {
                                    displayImage(fileUrl);
                                } else if (fileType.equals("video")) {
                                    displayVideo(fileUrl);
                                }
                            }
                        }
                    } else {
                        Log.d("TAG", "Error getting documents: ", task.getException());
                    }
                });
    }

    private void displayImage(String imageUrl) {
        ImageView imageView = new ImageView(this);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                getResources().getDimensionPixelSize(R.dimen.image_height),
                getResources().getDimensionPixelSize(R.dimen.image_width)
        );
        imageView.setLayoutParams(layoutParams);

        RequestOptions requestOptions = new RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.baseline_image_24)
                .error(R.drawable.baseline_security_24);

        Glide.with(this)
                .load(imageUrl)
                .apply(requestOptions)
                .into(imageView);

        LinearLayout linearLayout = new LinearLayout(this);
        LinearLayout.LayoutParams linearLayoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        linearLayoutParams.setMargins(0, 16, 0, 0);
        linearLayout.setLayoutParams(linearLayoutParams);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.addView(imageView);

        linearLayout.setOnLongClickListener(v -> {
            // Start the long-click handler to display options
            showOptionsDialog(imageUrl);
            return true;
        });

        mediaLayout.addView(linearLayout);
    }

    private void displayVideo(String videoUrl) {
        VideoView videoView = new VideoView(this);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        videoView.setLayoutParams(layoutParams);

        videoView.setVideoPath(videoUrl);
        videoView.start();

        LinearLayout linearLayout = new LinearLayout(this);
        LinearLayout.LayoutParams linearLayoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        linearLayoutParams.setMargins(0, 16, 0, 0);
        linearLayout.setLayoutParams(linearLayoutParams);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.addView(videoView);

        linearLayout.setOnLongClickListener(v -> {
            // Start the long-click handler to display options
            showOptionsDialog(videoUrl);
            return true;
        });

        mediaLayout.addView(linearLayout);
    }

    private void showOptionsDialog(String mediaUrl) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Options")
                .setItems(new CharSequence[]{"Delete", "Download", "View"}, (dialog, which) -> {
                    switch (which) {
                        case 0:
                            // Delete option
                            deleteMediaFile(mediaUrl);
                            break;
                        case 1:
                            // Download option
                            downloadMediaFile(mediaUrl);
                            break;
                        case 2:
                            // View option
                            viewMediaFile(mediaUrl);
                            break;
                    }
                })
                .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss())
                .show();
    }

    private void deleteMediaFile(String mediaUrl) {
        // Implement the deletion logic here
        // Replace the following toast with your implementation
        Toast.makeText(this, "Delete: " + mediaUrl, Toast.LENGTH_SHORT).show();
    }

    private void downloadMediaFile(String mediaUrl) {
        Glide.with(this)
                .asBitmap()
                .load(mediaUrl)
                .into(new CustomTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        // Save the bitmap image to external storage
                        File imageFile = saveBitmapToStorage(resource);
                        if (imageFile != null) {
                            // File saved successfully, display a toast message or implement further actions
                            Toast.makeText(ViewResourcesActivity.this, "Downloaded: " + imageFile.getAbsolutePath(), Toast.LENGTH_SHORT).show();
                        } else {
                            // Failed to save the file
                            Toast.makeText(ViewResourcesActivity.this, "Failed to save the file", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {
                        // Do nothing
                    }
                });
    }

    private File saveBitmapToStorage(Bitmap bitmap) {
        File imageFile = null;
        try {
            // Create a temporary file with a unique name
            String fileName = "image_" + System.currentTimeMillis() + ".jpg";
            File storageDir = getExternalCacheDir();
            imageFile = new File(storageDir, fileName);

            // Save the bitmap to the file
            FileOutputStream fos = new FileOutputStream(imageFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return imageFile;
    }

    private void viewMediaFile(String mediaUrl) {
        // Implement the view media file logic here
        // Replace the following toast with your implementation
        Toast.makeText(this, "View: " + mediaUrl, Toast.LENGTH_SHORT).show();
    }
}

