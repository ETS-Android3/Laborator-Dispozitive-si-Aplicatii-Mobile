package eu.ase.ro.s11_firebase_realtimedatabase.activitati;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import eu.ase.ro.s11_firebase_realtimedatabase.R;
import eu.ase.ro.s11_firebase_realtimedatabase.adapter.MovieAdapter;
import eu.ase.ro.s11_firebase_realtimedatabase.clase.Movie;
import eu.ase.ro.s11_firebase_realtimedatabase.firebase.Callback;
import eu.ase.ro.s11_firebase_realtimedatabase.firebase.FirebaseManager;


public class MainActivity extends AppCompatActivity {
    public static final int REQUEST_MAIN_SECOND_ADD_MOVIE = 200;

    private Button btnOpenForm;
    private ListView lvMovies;
    private ArrayList<Movie> listMovies = new ArrayList<>();

    private FirebaseManager firebaseManager;
    private int selectedMoviesIndex = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initComponents();
       // lvPopulateAdapterDefault();
        lvPopulateAdapterCustom();
        clickBtnOpenForm();
        clickLongLVDelete();


        firebaseManager = FirebaseManager.getInstance();
        firebaseManager.atachDataChangeEventListener(dataChangeCallback());
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
                startActivityForResult(intent, REQUEST_MAIN_SECOND_ADD_MOVIE);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK && data != null){
            Movie movie = (Movie) data.getSerializableExtra(SecondActivity.SEND_BACK_SECOND_MAIN_ADDED_MOVIE);

            if(requestCode == REQUEST_MAIN_SECOND_ADD_MOVIE){
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