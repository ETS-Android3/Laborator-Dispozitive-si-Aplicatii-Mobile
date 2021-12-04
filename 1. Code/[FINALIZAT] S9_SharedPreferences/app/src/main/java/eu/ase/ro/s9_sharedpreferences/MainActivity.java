package eu.ase.ro.s9_sharedpreferences;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import eu.ase.ro.s9_sharedpreferences.R;

public class MainActivity extends AppCompatActivity {

    public static final String STRING_SALVAT = "STRING_SALVAT";
    public static final String FLOAT_SALVAT = "FLOAT_SALVAT";
    public static final String RATING_SALVAT = "RATING_SALVAT";
    public static final String SHARED_PREFERENCES_NAME = "SharedPreferences_NAME";


    private EditText etString, etFloat;
    private RatingBar ratingBar;
    private Button btnAfiseaza;

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initComponents();
        clickBtnAfiseaza();
        restaurareDateDinSharedPreferences();
    }


    private void initComponents() {
        etString = findViewById(R.id.main_et_string);
        etFloat = findViewById(R.id.main_et_float);
        ratingBar = findViewById(R.id.main_ratingBar);
        btnAfiseaza = findViewById(R.id.main_btn_afiseaza);

        sharedPreferences = getSharedPreferences(SHARED_PREFERENCES_NAME, MODE_PRIVATE);
    }


    private void clickBtnAfiseaza() {
        btnAfiseaza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validareDate()) {
                    salvareDateInSharedPreferences();
                }
            }
        });
    }

    private boolean validareDate() {
        if (etString.getText().toString().isEmpty() || etString.getText().toString().trim().length() < 3) {

            Toast.makeText(getApplicationContext(),
                    R.string.string_errorString,
                    Toast.LENGTH_SHORT)
                    .show();
            return false;
        }

        if (etFloat.getText() == null || Float.parseFloat(etFloat.getText().toString().trim()) < 1
                || Float.parseFloat(etFloat.getText().toString().trim()) > 5000) {

            Toast.makeText(getApplicationContext(),
                    R.string.string_errorFloat,
                    Toast.LENGTH_SHORT)
                    .show();
            return false;
        }
        if (ratingBar.getRating() <= 0) {

            Toast.makeText(getApplicationContext(),
                    R.string.string_errorRating,
                    Toast.LENGTH_SHORT)
                    .show();
            return false;
        }
        return true;
    }

    private void salvareDateInSharedPreferences(){
        String stringPreluat = etString.getText().toString();
        float floatPreluat = Float.parseFloat(etFloat.getText().toString());
        float ratingPreluat = ratingBar.getRating();

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(STRING_SALVAT, stringPreluat);
        editor.putFloat(FLOAT_SALVAT, floatPreluat);
        editor.putFloat(RATING_SALVAT,ratingPreluat);

        editor.apply();

        Toast.makeText(getApplicationContext(),
                getString(R.string.string_toast,stringPreluat,floatPreluat,ratingPreluat),
                Toast.LENGTH_SHORT).
                show();
    }

    private void restaurareDateDinSharedPreferences(){
        String stringCitit =  sharedPreferences.getString(STRING_SALVAT, "None");
        float floatCitit = sharedPreferences.getFloat(FLOAT_SALVAT, 0);
        float ratingCitit = sharedPreferences.getFloat(RATING_SALVAT, -1);

        etString.setText(stringCitit);
        etFloat.setText(String.valueOf(floatCitit));
        ratingBar.setRating(ratingCitit);
    }
}