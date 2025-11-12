package com.example.mini_projet;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class GoldConverterActivity extends AppCompatActivity {

    private EditText inputGoldAmount;
    private Spinner spinnerMetal;
    private Button btnConvertGold;
    private TextView resultGold;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gold_converter);

        inputGoldAmount = findViewById(R.id.inputGoldAmount);
        spinnerMetal = findViewById(R.id.spinnerMetal);
        btnConvertGold = findViewById(R.id.btnConvertGold);
        resultGold = findViewById(R.id.resultGold);

        String[] metals = getResources().getStringArray(R.array.metal_array);
        spinnerMetal.setAdapter(new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item, metals));

        btnConvertGold.setOnClickListener(v -> {
            String str = inputGoldAmount.getText().toString().trim();
            if (TextUtils.isEmpty(str)) {
                Toast.makeText(this, "Entrez un montant en DT", Toast.LENGTH_SHORT).show();
                return;
            }
            double amount;
            try {
                amount = Double.parseDouble(str);
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Montant invalide", Toast.LENGTH_SHORT).show();
                return;
            }

            String metal = spinnerMetal.getSelectedItem().toString();
            double res = ConversionUtils.convertDTtoMetal(amount, metal);
            String s = String.format("%,.6f", res).replaceAll("\\.?0+$", "");
            resultGold.setText(amount + " DT = " + s + " g (" + metal + ")");
        });
    }
}
