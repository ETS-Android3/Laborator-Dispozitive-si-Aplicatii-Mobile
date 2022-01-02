package eu.ase.ro.s3_d_transfer_obiectcustom_serializable;

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
import android.widget.Toast;

import java.text.ParseException;
import java.util.Date;

import eu.ase.ro.s3_d_transfer_obiectcustom_serializable.classes.DateConverter;
import eu.ase.ro.s3_d_transfer_obiectcustom_serializable.classes.Movie;
import eu.ase.ro.s3_d_transfer_obiectcustom_serializable.classes.Platform;

public class SecondActivity extends AppCompatActivity {
    public static final String KEY_MOVIE = "sendMovie";

    private final DateConverter dateConverter = new DateConverter();
    EditText etName, etDate, etProfit;
    Spinner spnGenre;
    RadioGroup rgPlatform;
    RadioButton rbChosenPlatform;
    Button btnSend;

    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        initComponents();
        spnPopulate();
        clickBtnSend();
    }

    private void initComponents() {
        etName = findViewById(R.id.second_et_name);
        etDate = findViewById(R.id.second_et_date);
        etProfit = findViewById(R.id.second_et_profit);
        spnGenre = findViewById(R.id.second_spn_genre);
        rgPlatform = findViewById(R.id.second_rg_platform);
        //rbChosenPlatform = findViewById(rgPlatform.getCheckedRadioButtonId());
        btnSend = findViewById(R.id.second_btn_send);

        intent = getIntent();
    }

    private void spnPopulate() {
        ArrayAdapter<CharSequence> adapterSPN = ArrayAdapter.createFromResource(
                getApplicationContext(),
                R.array.second_spn_genre,
                android.R.layout.simple_spinner_dropdown_item);
        spnGenre.setAdapter(adapterSPN);
    }

    private Movie createMovie() {
            String name = etName.getText().toString();
            Date date = dateConverter.fromString(etDate.getText().toString());
            int profit = Integer.parseInt(etProfit.getText().toString());
            String genre = spnGenre.getSelectedItem().toString();

            Platform platform = Platform.HBO;
            if (rgPlatform.getCheckedRadioButtonId() == R.id.second_rb_netflix) {
                platform = Platform.NETFLIX;
            }
            return new Movie(name, date, profit, genre, platform);
    }

    private boolean validate() {
        if (etName.getText() == null || etName.getText().toString().trim().length() < 3) {
            Toast.makeText(getApplicationContext(), R.string.second_validate_nameError, Toast.LENGTH_LONG)
                    .show();
            return false;
        }


        if (etProfit.getText() == null || etProfit.getText().toString().trim().length() == 0
                || Integer.parseInt(etProfit.getText().toString().trim()) < 1
                || Integer.parseInt(etProfit.getText().toString().trim()) > 1000) {
            Toast.makeText(getApplicationContext(), R.string.second_validate_profitError, Toast.LENGTH_LONG)
                    .show();
            return false;
        }


        if (etDate.getText() == null || dateConverter.fromString(etDate.getText().toString().trim()) == null) {
            Toast.makeText(getApplicationContext(), R.string.second_validate_dateError, Toast.LENGTH_LONG)
                    .show();
            return false;
        }
        return true;
    }


    private void clickBtnSend(){
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validate()){
                    Movie movie = createMovie();
                    intent.putExtra(KEY_MOVIE, movie);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        });
    }
}