package com.neizathedev.photovideoapplimegig.Tools;
/*
 * the PhotoEditorActivity class sets up the UI elements and defines the logic for applying a grayscale filter and cropping the image.
 * The applyGrayscaleFilter method applies a grayscale filter using a ColorMatrixColorFilter on the provided bitmap.
 * The cropImage method crops the image based on specified dimensions.
 * */

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.Manifest;
import android.widget.Toast;

import com.dsphotoeditor.sdk.activity.DsPhotoEditorActivity;
import com.dsphotoeditor.sdk.utils.DsPhotoEditorConstants;
import com.neizathedev.photovideoapplimegig.R;

/*
 * the PhotoEditorActivity class sets up the UI elements and defines the logic for applying a grayscale filter and cropping the image.
 * The applyGrayscaleFilter method applies a grayscale filter using a ColorMatrixColorFilter on the provided bitmap.
 * The cropImage method crops the image based on specified dimensions.
 * */
public class PhotoEditorActivity extends AppCompatActivity {

    ImageButton imageButtonCrop, btn_camera;
    ImageView imageView;
    int IMAGE_REQUEST_CODE = 101;

    public static final int RequestPermissionCode = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_editor);

        imageView = (ImageView) findViewById(R.id.imageView);

        btn_camera = (ImageButton) findViewById(R.id.btn_camera);
        EnableRuntimePermission();
        btn_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent v = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(v, 7);
            }
        });


        imageButtonCrop = (ImageButton) findViewById(R.id.btn_crop);
        imageButtonCrop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent x = new Intent();
                x.setAction(Intent.ACTION_GET_CONTENT);
                x.setType("image/*");
                startActivityForResult(x, IMAGE_REQUEST_CODE);

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMAGE_REQUEST_CODE) {
            if (data.getData() != null) {
                Uri filePath = data.getData();

                /*
                 * If the input image uri for DS Photo Editor is "inputImageUri", launch the editor UI
                 * using the following code
                 * */
                Intent dsPhotoEditorIntent = new Intent(this, DsPhotoEditorActivity.class);
                dsPhotoEditorIntent.setData(filePath);

                /*
                 * This is optional. By providing an output directory, the edited photo, will be saved in the specified folder on your device's external storage;
                 * If this is omitted, the edited photo will be saved to a folder named "DS_Photo_Editor" by default.
                 * */
                dsPhotoEditorIntent.putExtra(DsPhotoEditorConstants.DS_PHOTO_EDITOR_OUTPUT_DIRECTORY, "PhotoEditor Folder");

                // Optional customization: hide some tools you don't need as below
                int[] toolsToHide = {DsPhotoEditorActivity.TOOL_ORIENTATION};
                dsPhotoEditorIntent.putExtra(DsPhotoEditorConstants.DS_PHOTO_EDITOR_TOOLS_TO_HIDE, toolsToHide);
                startActivityForResult(dsPhotoEditorIntent, 200);
            }
        }
        if (requestCode == 200) {
            Intent x = new Intent(PhotoEditorActivity.this, DraftsActivity.class);
            x.setData(data.getData());
            startActivity(x);
        }
        // For capturing images
        if (requestCode == 7 && resultCode == RESULT_OK) {
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            imageView.setImageBitmap(bitmap);
        }
    }


    public void EnableRuntimePermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(PhotoEditorActivity.this, Manifest.permission.CAMERA)) {
            Toast.makeText(PhotoEditorActivity.this, "You may use this app", Toast.LENGTH_SHORT).show();
        } else {
            ActivityCompat.requestPermissions(PhotoEditorActivity.this, new String[]{
                    Manifest.permission.CAMERA}, RequestPermissionCode);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case RequestPermissionCode:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(PhotoEditorActivity.this, "Permission Granted", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(PhotoEditorActivity.this, "Permission Denied", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}