package com.example.mini_projet;

import androidx.appcompat.app.AppCompatActivity;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

public class MoneyConverterActivity extends AppCompatActivity {

    private VideoView videoView;
    private EditText inputAmountArgent;
    private Spinner spinnerFrom, spinnerTo;
    private Button btnConvertArgent;
    private TextView resultArgent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_money_converter);

        videoView = findViewById(R.id.videoBackground);

        Uri uri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.gold2);
        videoView.setVideoURI(uri);

        videoView.setOnPreparedListener(mp -> {
            mp.setLooping(true);
            mp.setVideoScalingMode(MediaPlayer.VIDEO_SCALING_MODE_SCALE_TO_FIT_WITH_CROPPING);

            // Ajuster l'aspect ratio
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

        inputAmountArgent = findViewById(R.id.inputAmountArgent);
        spinnerFrom = findViewById(R.id.spinnerFrom);
        spinnerTo = findViewById(R.id.spinnerTo);
        btnConvertArgent = findViewById(R.id.btnConvertArgent);
        resultArgent = findViewById(R.id.resultArgent);

        String[] currencies = {"DT", "EUR", "USD"};
        String[] targets = {"USD", "EUR", "DT"};

        ArrayAdapter<String> adapterFrom = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, currencies);
        ArrayAdapter<String> adapterTo = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, targets);

        spinnerFrom.setAdapter(adapterFrom);
        spinnerTo.setAdapter(adapterTo);

        btnConvertArgent.setOnClickListener(v -> {
            String amountStr = inputAmountArgent.getText().toString().trim();

            if (TextUtils.isEmpty(amountStr)) {
                Toast.makeText(this, "Veuillez entrer un montant.", Toast.LENGTH_SHORT).show();
                return;
            }

            double amount;
            try {
                amount = Double.parseDouble(amountStr);
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Montant invalide", Toast.LENGTH_SHORT).show();
                return;
            }

            String from = spinnerFrom.getSelectedItem().toString();
            String to = spinnerTo.getSelectedItem().toString();

            double result = convert(from, to, amount);

            String formatted = String.format("%,.6f", result).replaceAll("\\.?0+$", "");
            resultArgent.setText(amount + " " + from + " = " + formatted + " " + to);
        });
    }


    private double convert(String from, String to, double amount) {
        double rate = 1.0;

        if (from.equals("DT")) {
            switch (to) {
                case "EUR": rate = 0.30; break;
                case "USD": rate = 0.32; break;
                case "OR": rate = 0.017; break;
                case "ARGENT": rate = 0.42; break;
            }
        } else if (from.equals("EUR")) {
            switch (to) {
                case "DT": rate = 3.3; break;
                case "USD": rate = 1.1; break;
                case "OR": rate = 0.056; break;
                case "ARGENT": rate = 1.4; break;
            }
        } else if (from.equals("USD")) {
            switch (to) {
                case "DT": rate = 3.1; break;
                case "EUR": rate = 0.91; break;
                case "OR": rate = 0.051; break;
                case "ARGENT": rate = 1.3; break;
            }
        }

        return amount * rate;
    }

}
