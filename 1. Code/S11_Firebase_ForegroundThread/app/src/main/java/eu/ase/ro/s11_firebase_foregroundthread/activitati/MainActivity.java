package eu.ase.ro.s11_firebase_foregroundthread.activitati;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;

import eu.ase.ro.s11_firebase_foregroundthread.R;
import eu.ase.ro.s11_firebase_foregroundthread.adapter.MovieAdapter;
import eu.ase.ro.s11_firebase_foregroundthread.clase.DateConverter;
import eu.ase.ro.s11_firebase_foregroundthread.clase.Movie;


public class MainActivity extends AppCompatActivity {
    public static final int ADD_MOVIE_SECOND_ACTIVTY_REQUEST_CODE = 200;
    Button btnOpenForm;
    ListView lvMovies;
    ArrayList<Movie> listMovies = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initComponents();
       // lvPopulateAdapterDefault();
        lvPopulateAdapterCustom();
        clickBtnOpenForm();

        Date date = new DateConverter().fromString("05-05-1998");
        listMovies.add(new Movie("Giol Adriana", date,693,"Romance", "NETFLIX"));

    }
    private void initComponents(){
        btnOpenForm = findViewById(R.id.main_btn_openForm);
        lvMovies = findViewById(R.id.main_lv_movies);
    }

    private void lvPopulateAdapterDefault(){
        ArrayAdapter<Movie> adapterLvDefault = new ArrayAdapter<>(
                getApplicationContext(),
                android.R.layout.simple_list_item_1,
                listMovies);
        lvMovies.setAdapter(adapterLvDefault);
    }

    private void lvPopulateAdapterCustom(){
        MovieAdapter adapterLvCustom = new MovieAdapter(
                getApplicationContext(),
                R.layout.adapter_lv_row,
                listMovies,
                getLayoutInflater());
        lvMovies.setAdapter(adapterLvCustom);
    }


    private void clickBtnOpenForm(){
        btnOpenForm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SecondActivity.class);
                startActivityForResult(intent, ADD_MOVIE_SECOND_ACTIVTY_REQUEST_CODE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK && data != null){
            Movie movie = (Movie) data.getSerializableExtra(SecondActivity.KEY_MOVIE);

            if(requestCode == ADD_MOVIE_SECOND_ACTIVTY_REQUEST_CODE){
                if(movie !=null) {
                    listMovies.add(movie);
                    notifyAdapter();
                    Toast.makeText(getApplicationContext(),getString(R.string.toast_addMovie,movie.getName()),Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private void notifyAdapter(){
        ArrayAdapter adapterNotify = (ArrayAdapter) lvMovies.getAdapter();
        adapterNotify.notifyDataSetChanged();
    }
}