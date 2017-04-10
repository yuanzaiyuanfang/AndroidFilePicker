package com.brilliant.androidfilepicker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.brilliant.androidfilepicker.image.ImageActivity;
import com.brilliant.androidfilepicker.video.VideoActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.image_selector).setOnClickListener(this);
        findViewById(R.id.video_selector).setOnClickListener(this);
        findViewById(R.id.file_selector).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.image_selector: {
                startActivity(new Intent(this, ImageActivity.class));
                break;
            }
            case R.id.video_selector: {
                startActivity(new Intent(this, VideoActivity.class));
                break;
            }
            case R.id.file_selector: {
                break;
            }
            default:
                break;
        }
    }
}
