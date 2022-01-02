package eu.ase.ro.s10_database_backgroundthread.activitati;

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
import android.widget.Switch;

import java.text.ParseException;
import java.util.Date;

import eu.ase.ro.s10_database_backgroundthread.R;
import eu.ase.ro.s10_database_backgroundthread.clase.DateConverter;
import eu.ase.ro.s10_database_backgroundthread.database.entities.Movie;


public class SecondActivity extends AppCompatActivity {

    public DateConverter dateConverter = new DateConverter();
    public static final String KEY_MOVIE = "sendMovie";

    EditText etName, etDate, etProfit;
    Spinner spnGnere;
    RadioGroup rgPlatform;
    RadioButton rbChosenPlatform, rbNETFLIX, rbHBO;
    Button btnSend;

    Intent intent;

    Movie movie = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        intent = getIntent();

        initComponents();
        spnPopulate();
        clickBtnSend();

        if(intent.hasExtra(KEY_MOVIE)){
        movie = (Movie) intent.getSerializableExtra(KEY_MOVIE);
        updateMovie(movie);
        }
    }

    private void initComponents(){
        etName = findViewById(R.id.second_et_name);
        etProfit = findViewById(R.id.second_et_profit);
        etDate = findViewById(R.id.second_et_date);
        spnGnere = findViewById(R.id.second_spn_genre);
        rgPlatform = findViewById(R.id.second_rg_platform);
        rbChosenPlatform = findViewById(rgPlatform.getCheckedRadioButtonId());
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

    private void createMovie(){

        try {
            String name = etName.getText().toString();
            Date date = dateConverter.fromString(etDate.getText().toString());
            int profit = Integer.parseInt(etProfit.getText().toString());
            String genre = spnGnere.getSelectedItem().toString();
            String platform = rbChosenPlatform.getText().toString();

            if(movie == null) {
               movie =  new Movie(name, date, profit, genre, platform);
            } else{
                movie.setName(name);
                movie.setDate(date);
                movie.setProfit(profit);
                movie.setGenre(genre);
                movie.setPlatform(platform);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return;
    }

    private void updateMovie(Movie movie){
        if(movie == null){
            return;
        }
        if(movie.getName() != null){
            etName.setText(movie.getName());
        }
        if(movie.getProfit() != 0){
            etProfit.setText(String.valueOf(movie.getProfit()));
        }
        if (movie.getDate() != null){
            etDate.setText(dateConverter.toString(movie.getDate()));
        }

        ArrayAdapter adapter = (ArrayAdapter) spnGnere.getAdapter();
        for (int i =0; i <adapter.getCount(); i++){
            String item = (String) adapter.getItem(i);
            if(item != null && item.equals(movie.getGenre())){
                spnGnere.setSelection(i);
                break;
            }
        }
        if(rgPlatform.equals("NETFLIX")){
            rbNETFLIX.setChecked(true);
            rbHBO.setChecked(false);

        }else{
            rbHBO.setChecked(true);
            rbNETFLIX.setChecked(false);
        }
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
                    createMovie();
                    if(movie != null)
                        intent.putExtra(KEY_MOVIE, movie);
                    setResult(RESULT_OK,getIntent());
                    finish();
                }
            }
        });
    }
}