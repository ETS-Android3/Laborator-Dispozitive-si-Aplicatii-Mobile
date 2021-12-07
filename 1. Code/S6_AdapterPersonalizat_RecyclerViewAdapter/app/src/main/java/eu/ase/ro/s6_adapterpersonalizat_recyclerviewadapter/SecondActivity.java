package eu.ase.ro.s6_adapterpersonalizat_recyclerviewadapter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class SecondActivity extends AppCompatActivity {
    public static final String DATE_PATTERN = "dd-mm-yyyy";
    DateFormat simpleDateFormat = new SimpleDateFormat(DATE_PATTERN, Locale.US);

    public static final String KEY_MOVIE = "sendMovie";

    EditText etName, etDate, etProfit, etRating;
    Spinner spnGnere;
    RadioGroup rgPlatform;
    RadioButton rbChosenPlatform;
    Button btnSend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
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
        rbChosenPlatform = findViewById(rgPlatform.getCheckedRadioButtonId());
        etRating = findViewById(R.id.second_et_rating);
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
            Date date = simpleDateFormat.parse(etDate.getText().toString());
            int profit = Integer.parseInt(etProfit.getText().toString());
            float rating = Float.parseFloat(etRating.getText().toString());
            String genre = spnGnere.getSelectedItem().toString();
            String platform = rbChosenPlatform.getText().toString();

            return new Movie(name,date,profit,rating,genre,platform);

        } catch (ParseException e) {
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
        if(etRating.getText().toString() == null){
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
                    if(movie != null)
                        getIntent().putExtra(KEY_MOVIE, movie);
                    setResult(RESULT_OK,getIntent());
                    finish();
                }
            }
        });
    }
}