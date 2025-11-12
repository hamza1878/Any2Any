package com.example.mini_projet;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.VideoView;
import android.media.MediaPlayer;

public class LoginActivity extends AppCompatActivity {

    private EditText edtUser, edtPass;
    private Button btnLogin;
    private VideoView videoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtUser = findViewById(R.id.edtUser);
        edtPass = findViewById(R.id.edtPass);
        btnLogin = findViewById(R.id.btnLogin);
        videoView = findViewById(R.id.videoBackground);

        Uri uri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.gold);
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

        btnLogin.setOnClickListener(v -> {
            String user = edtUser.getText().toString().trim();
            String pass = edtPass.getText().toString().trim();

            if (TextUtils.isEmpty(user) || TextUtils.isEmpty(pass)) {
                Toast.makeText(this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
                return;
            }

            Intent i = new Intent(LoginActivity.this, CategoryActivity.class);
            i.putExtra("username", user);
            startActivity(i);
            finish();
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (videoView != null) {
            videoView.start();
        }
    }}