package eu.ase.ro.s11_firebase_backgroundthread.activitati;
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

import java.util.Date;

import eu.ase.ro.s11_firebase_backgroundthread.R;
import eu.ase.ro.s11_firebase_backgroundthread.clase.Movie;
import eu.ase.ro.s11_firebase_backgroundthread.firebase.Callback;
import eu.ase.ro.s11_firebase_backgroundthread.firebase.FirebaseManager;


public class SecondActivity extends AppCompatActivity {


    public static final String KEY_MOVIE = "sendMovie";

    private EditText etName, etDate, etProfit;
    private Spinner spnGnere;
    private RadioGroup rgPlatform;
    private RadioButton rbChosenPlatform, rbNETFLIX, rbHBO;
    private Button btnSend;

    private Intent intent;
    private FirebaseManager firebaseManager;
    private Movie movie;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        firebaseManager = FirebaseManager.getInstance();

        intent = getIntent();
        if(intent.hasExtra(KEY_MOVIE)){
            movie = (Movie) intent.getSerializableExtra(KEY_MOVIE);
            firebaseManager.update(movie);
        }

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

    private void createMovie(){

        try {
            String name = etName.getText().toString();
            String date = etDate.getText().toString();
            int profit = Integer.parseInt(etProfit.getText().toString());
            String genre = spnGnere.getSelectedItem().toString();
            String platform = rbChosenPlatform.getText().toString();

            if(movie == null) {
                new Movie(name, date, profit, genre, platform);
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
            etDate.setText(movie.getDate());
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
                if (validateMovie() == true) {
                    if(movie == null) {
                        createMovie();
                     firebaseManager.insert(movie);
                    }

                    else if(movie != null){
                        firebaseManager.update(movie);
                        intent.putExtra(KEY_MOVIE, movie);
                    }
                    setResult(RESULT_OK,getIntent());
                    finish();
                }
            }
        });
    }
}