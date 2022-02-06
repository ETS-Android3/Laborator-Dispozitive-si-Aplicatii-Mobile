package eu.ase.ro.s11_firebase_backgroundthread.activitati;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import java.util.List;

import eu.ase.ro.s11_firebase_backgroundthread.R;
import eu.ase.ro.s11_firebase_backgroundthread.adapter.MovieAdapter;
import eu.ase.ro.s11_firebase_backgroundthread.clase.Movie;
import eu.ase.ro.s11_firebase_backgroundthread.firebase.Callback;
import eu.ase.ro.s11_firebase_backgroundthread.firebase.FirebaseManager;


public class MainActivity extends AppCompatActivity {
    public static final int ADD_MOVIE_SECOND_ACTIVTY_REQUEST_CODE = 200;
    public static final int UPDATE_REQUEST_CODE = 201;

    Button btnOpenForm, btnDelete, btnOpenChart;
    ListView lvMovies;
    ArrayList<Movie> listMovies = new ArrayList<>();

    private FirebaseManager firebaseManager;
    private int selectedMovieIndexLV = -1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseManager = FirebaseManager.getInstance();


        initComponents();
        //lvPopulateAdapterDefault();
        lvPopulateAdapterCustom();

        clickBtnOpenForm();
        clickBtnOpenChart();
        clickBtnDelete();

        clickLVUpdate();
        clickLongLVDelete();

        firebaseManager.attachDataChangeEventListener(dataChangeCallback());


    }

    private void initComponents() {
        btnOpenForm = findViewById(R.id.main_btn_openForm);
        lvMovies = findViewById(R.id.main_lv_movies);
        btnDelete = findViewById(R.id.main_btn_delete);
        btnOpenChart = findViewById(R.id.main_btn_chart);
    }

    private void clickBtnOpenChart(){
        btnOpenChart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ThirdActivity.class);
                intent.putExtra("sendList", listMovies);
                startActivity(intent);
            }
        });
    }

    private void lvPopulateAdapterDefault() {
        ArrayAdapter<Movie> adapterLvDefault = new ArrayAdapter<>(
                getApplicationContext(),
                android.R.layout.simple_list_item_1,
                listMovies);
        lvMovies.setAdapter(adapterLvDefault);
    }

    private void lvPopulateAdapterCustom() {
        MovieAdapter adapterLvCustom = new MovieAdapter(
                getApplicationContext(),
                R.layout.adapter_lv_row,
                listMovies,
                getLayoutInflater());
        lvMovies.setAdapter(adapterLvCustom);
    }

    private void clickBtnOpenForm() {
        btnOpenForm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SecondActivity.class);
                startActivityForResult(intent, ADD_MOVIE_SECOND_ACTIVTY_REQUEST_CODE);
            }
        });
    }

    private void clickBtnDelete() {
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    firebaseManager.deleteAll(listMovies.get(selectedMovieIndexLV));
                    notifyAdapter();
                }
        });
    }

    private void clickLongLVDelete() {
        lvMovies.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                firebaseManager.deleteSimple(listMovies.get(position));
                notifyAdapter();
                return true;
            }
        });
    }

    private void clickLVUpdate() {
        lvMovies.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Preluare informatii de la nivelul Adapterului
                selectedMovieIndexLV = position;
                Movie movie = listMovies.get(selectedMovieIndexLV);
                view.setBackgroundColor(Color.GREEN);

                Intent intent = new Intent(getApplicationContext(), SecondActivity.class);
                intent.putExtra("updateMovie",movie);
                startActivityForResult(intent, UPDATE_REQUEST_CODE);

            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {
            Movie movie = (Movie) data.getSerializableExtra(SecondActivity.KEY_MOVIE);

            if (movie != null) {
                if (requestCode == ADD_MOVIE_SECOND_ACTIVTY_REQUEST_CODE) {
                    listMovies.add(movie);
                    notifyAdapter();
                    Toast.makeText(getApplicationContext(), getString(R.string.toast_addMovie, movie.getName()), Toast.LENGTH_SHORT).show();

                } else if (requestCode == UPDATE_REQUEST_CODE) {
                    notifyAdapter();
                }
            }
        }
    }

    private void notifyAdapter(){
        ArrayAdapter adapterNotify = (ArrayAdapter) lvMovies.getAdapter();
        adapterNotify.notifyDataSetChanged();
    }



    //------------------------------------- FIREBASE CALLBACKS -----------------------------------------------
    private Callback<List<Movie>> dataChangeCallback(){
        return new Callback<List<Movie>>() {
            @Override
            public void runResultOnUiThread(List<Movie> moviesChanged) {
                if(moviesChanged != null){
                    listMovies.clear();
                    listMovies.addAll(moviesChanged);
                    notifyAdapter();
                }
            }
        };
    }
}