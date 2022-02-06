package eu.ase.ro.s11_firebase_foregroundthread.activitati;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import java.text.ParseException;
import java.util.Date;

import eu.ase.ro.s11_firebase_foregroundthread.R;
import eu.ase.ro.s11_firebase_foregroundthread.clase.DateConverter;
import eu.ase.ro.s11_firebase_foregroundthread.clase.Movie;
import eu.ase.ro.s11_firebase_foregroundthread.firebase.FirebaseManager;

public class SecondActivity extends AppCompatActivity {

    public DateConverter dateConverter = new DateConverter();
    public static final String KEY_MOVIE = "sendMovie";

    public FirebaseManager firebaseManager;

    EditText etName, etDate, etProfit;
    Spinner spnGnere;
    RadioGroup rgPlatform;
    RadioButton rbChosenPlatform, rbNETFLIX, rbHBO;
    Button btnSend;

    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        intent = getIntent();
        firebaseManager = new FirebaseManager();

        initComponents();
        spnPopulate();
        clickBtnSend();
    }

    private void initComponents(){
        etName = findViewById(R.id.second_et_name);
        etProfit = findViewById(R.id.second_et_profit);
        etDate = findViewById(R.id.second_et_date);
        spnGnere = findViewById(R.id.second_spn_genre);
        rgPlatform = findViewById(R.id.second_rg_platform);
        rbNETFLIX = findViewById(R.id.second_rb_netflix);
        rbHBO = findViewById(R.id.second_rb_hbo);
        btnSend = findViewById(R.id.second_btn_send);
    }

    private void spnPopulate(){
        ArrayAdapter<CharSequence> adapterSPN = ArrayAdapter.createFromResource(
                getApplicationContext(),
                R.array.second_spn_genre,
                android.R.layout.simple_spinner_dropdown_item);
        spnGnere.setAdapter(adapterSPN);
    }

    private Movie createMovie(){

        try {
            String name = etName.getText().toString();
            Date date = dateConverter.fromString(etDate.getText().toString());
            int profit = Integer.parseInt(etProfit.getText().toString());
            String genre = spnGnere.getSelectedItem().toString();

            rbChosenPlatform = findViewById(rgPlatform.getCheckedRadioButtonId());
            String platform = rbChosenPlatform.getText().toString();


            return new Movie(name,date,profit,genre,platform);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private boolean validateMovie(){
        if(etName.getText().toString() == null){
            return false;
        }
        if(etDate.getText().toString() == null){
            return false;
        }
        if(etProfit.getText().toString() == null){
            return false;
        }
        return true;
    }

    private void clickBtnSend(){
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validateMovie() == true){
                    Movie movie = createMovie();
                    if(movie != null) {
                        firebaseManager.insertMovieInFirebase(movie);
                        intent.putExtra(KEY_MOVIE, movie);
                        setResult(RESULT_OK, getIntent());
                        finish();
                    }
                }
            }
        });
    }
}