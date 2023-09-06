package com.neizathedev.photovideoapplimegig.Tools;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

import com.neizathedev.photovideoapplimegig.R;

public class DraftsActivity extends AppCompatActivity {

    ImageView img_result;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drafts);

        img_result = (ImageView) findViewById(R.id.img_result);
        img_result.setImageURI(getIntent().getData());
    }
}