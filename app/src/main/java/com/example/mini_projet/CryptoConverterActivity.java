package com.example.mini_projet;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
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

public class CryptoConverterActivity extends AppCompatActivity {
    private VideoView videoView;

    private EditText inputAmountCrypto;
    private Spinner spinnerFrom, spinnerTo;
    private Button btnConvertCrypto;
    private TextView resultCrypto;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crypto_converter);

        // Liaison des vues XML
        inputAmountCrypto = findViewById(R.id.inputAmountCrypto);
        spinnerFrom = findViewById(R.id.spinnerFrom);
        spinnerTo = findViewById(R.id.spinnerTo);
        btnConvertCrypto = findViewById(R.id.btnConvertCrypto);
        resultCrypto = findViewById(R.id.resultCrypto);
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
        // Listes des devises
        String[] fromCurrencies = {"DT", "EUR", "USD"};
        String[] toCurrencies = {"BTC", "ETH", "XRP", "OR", "ARGENT"};

        ArrayAdapter<String> fromAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item, fromCurrencies);
        ArrayAdapter<String> toAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item, toCurrencies);

        spinnerFrom.setAdapter(fromAdapter);
        spinnerTo.setAdapter(toAdapter);

        btnConvertCrypto.setOnClickListener(v -> {
            String str = inputAmountCrypto.getText().toString().trim();
            if (TextUtils.isEmpty(str)) {
                Toast.makeText(this, "⚠️ Entrez un montant", Toast.LENGTH_SHORT).show();
                return;
            }

            double amount;
            try {
                amount = Double.parseDouble(str);
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Montant invalide", Toast.LENGTH_SHORT).show();
                return;
            }

            String from = spinnerFrom.getSelectedItem().toString();
            String to = spinnerTo.getSelectedItem().toString();

            double result = convert(from, to, amount);
            if (result < 0) {
                Toast.makeText(this, "Conversion non disponible", Toast.LENGTH_SHORT).show();
                return;
            }

            String formatted = String.format("%,.8f", result).replaceAll("\\.?0+$", "");
            resultCrypto.setText(String.format("%s %s = %s %s", str, from, formatted, to));
        });
    }

    private double convert(String from, String to, double amount) {
        double rate = 0;

        switch (from) {
            case "DT":
                if (to.equals("BTC")) rate = 0.0000075;
                else if (to.equals("ETH")) rate = 0.00012;
                else if (to.equals("XRP")) rate = 1.8;
                else if (to.equals("OR")) rate = 0.00045;
                else if (to.equals("ARGENT")) rate = 0.025;
                break;
            case "EUR":
                if (to.equals("BTC")) rate = 0.000016;
                else if (to.equals("ETH")) rate = 0.00026;
                else if (to.equals("XRP")) rate = 3.5;
                else if (to.equals("OR")) rate = 0.0009;
                else if (to.equals("ARGENT")) rate = 0.05;
                break;
            case "USD":
                if (to.equals("BTC")) rate = 0.000014;
                else if (to.equals("ETH")) rate = 0.00023;
                else if (to.equals("XRP")) rate = 3.2;
                else if (to.equals("OR")) rate = 0.00085;
                else if (to.equals("ARGENT")) rate = 0.045;
                break;
        }

        return rate > 0 ? amount * rate : -1;
    }
}
