package com.example.mini_projet;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.VideoView;

public class CategoryActivity extends AppCompatActivity {
    private VideoView videoView;

    private TextView welcomeText;
    private CardView cardCrypto, cardArgent, cardAutres;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        welcomeText = findViewById(R.id.welcomeText);
        cardCrypto = findViewById(R.id.cardCrypto);
        cardArgent = findViewById(R.id.cardArgent);
        cardAutres = findViewById(R.id.cardAutres);
        videoView = findViewById(R.id.videoBackground);

        Uri uri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.gold2);
        videoView.setVideoURI(uri);

        videoView.setOnPreparedListener(mp -> {
            mp.setLooping(true);
            mp.setVideoScalingMode(MediaPlayer.VIDEO_SCALING_MODE_SCALE_TO_FIT_WITH_CROPPING);

            int videoWidth = mp.getVideoWidth();
            int videoHeight = mp.getVideoHeight();
            int screenWidth = getResources().getDisplayMetrics().widthPixels;
            int screenHeight = getResources().getDisplayMetrics().heightPixels;

            float screenRatio = (float) screenHeight / screenWidth;
            float videoRatio = (float) videoHeight / videoWidth;

            if (videoRatio > screenRatio) {
                videoView.getLayoutParams().width = screenWidth;
                videoView.getLayoutParams().height = (int) (screenWidth * videoRatio);
            } else {
                videoView.getLayoutParams().height = screenHeight;
                videoView.getLayoutParams().width = (int) (screenHeight / videoRatio);
            }
        });

        videoView.start();
        cardCrypto.setOnClickListener(v -> {
            Intent intent = new Intent(CategoryActivity.this, CryptoConverterActivity.class);
            startActivity(intent);
        });

        cardArgent.setOnClickListener(v -> {
            Intent intent = new Intent(CategoryActivity.this, MoneyConverterActivity.class);
            startActivity(intent);
        });

        cardAutres.setOnClickListener(v -> {
            Intent intent = new Intent(CategoryActivity.this, CryptoConverterActivity.class);
            startActivity(intent);
        });
    }@Override
    protected void onResume() {
        super.onResume();
        if (videoView != null) {
            videoView.start();
        }}
}
