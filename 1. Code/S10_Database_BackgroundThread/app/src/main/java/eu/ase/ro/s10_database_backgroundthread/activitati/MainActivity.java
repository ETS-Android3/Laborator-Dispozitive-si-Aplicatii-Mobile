package eu.ase.ro.s10_database_backgroundthread.activitati;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import eu.ase.ro.s10_database_backgroundthread.R;
import eu.ase.ro.s10_database_backgroundthread.adapter.MovieAdapter;
import eu.ase.ro.s10_database_backgroundthread.asyncTask.Callback;
import eu.ase.ro.s10_database_backgroundthread.clase.DateConverter;
import eu.ase.ro.s10_database_backgroundthread.database.entities.Movie;
import eu.ase.ro.s10_database_backgroundthread.database.service.MovieService;

public class MainActivity extends AppCompatActivity {
    public static final int ADD_MOVIE_SECOND_ACTIVTY_REQUEST_CODE = 200;
    public static final int UPDATE_MOVIE_SECOND_ACTIVITY_REQUEST_CODE = 201;
    Button btnOpenForm;
    ListView lvMovies;
    ArrayList<Movie> listMovies = new ArrayList<>();

    private MovieService movieService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        movieService = new MovieService(getApplicationContext());

        Date date = new DateConverter().fromString("05-05-1998");
        listMovies.add(new Movie("Giol Adriana", date,693,"Romance", "NETFLIX"));

        initComponents();
        //lvPopulateAdapterDefault();
        lvPopulateAdapterCustom();
        clickBtnOpenForm();

       // movieService.selectAll(selectAllFromDBCallback());
        selectAllFromDBThreadVersion2();
        clickSimpleLvUpdate();
        clickLongLvDelete();
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

    private void clickSimpleLvUpdate(){
        lvMovies.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //PAS 1: Deschidem activitatea cu formularul
                Intent intent = new Intent(getApplicationContext(),SecondActivity.class);
                intent.putExtra(SecondActivity.KEY_MOVIE,listMovies.get(position));
                startActivityForResult(intent, UPDATE_MOVIE_SECOND_ACTIVITY_REQUEST_CODE);
            }
        });
    }

    private void clickLongLvDelete(){
        lvMovies.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                movieService.delete(listMovies.get(position),deleteFromDBCallback(position));
                return true;
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK && data != null) {
            Movie movie = (Movie) data.getSerializableExtra(SecondActivity.KEY_MOVIE);

            if (requestCode == ADD_MOVIE_SECOND_ACTIVTY_REQUEST_CODE) {
                movieService.insert(movie, insertIntoDBCallback());

            } else if (requestCode == UPDATE_MOVIE_SECOND_ACTIVITY_REQUEST_CODE) {
                movieService.update(movie,updateIntoDBCallback());
            }
        }
    }

    private void notifyAdapter(){
        ArrayAdapter adapterNotify = (ArrayAdapter) lvMovies.getAdapter();
        adapterNotify.notifyDataSetChanged();
    }

    //-------------------------------------- SQLITE -------------------------------------------------------------

    private Callback<Movie> insertIntoDBCallback(){
        return new Callback<Movie>() {
            @Override
            public void runResultOnUiThread(Movie movieInserted) {
                if (movieInserted != null) {
                    listMovies.add(movieInserted);
                    notifyAdapter();
                    Toast.makeText(getApplicationContext(), getString(R.string.toast_addMovie, movieInserted.getName()), Toast.LENGTH_SHORT).show();
                }
            }
        };
    }

    private Callback<List<Movie>> selectAllFromDBCallback() {
        return new Callback<List<Movie>>() {
            @Override
            public void runResultOnUiThread(List<Movie> movieSelected) {
                if (movieSelected != null) {
                    listMovies.clear();
                    listMovies.addAll(movieSelected);
                    notifyAdapter();
                }
            }
        };
    }

    private Callback<Movie> updateIntoDBCallback(){
        return new Callback<Movie>() {
            @Override
            public void runResultOnUiThread(Movie movieUpdated) {
                if (movieUpdated != null) {
                    for(Movie movie:listMovies){
                        if(movie.getIdMovie() == movieUpdated.getIdMovie()){
                            movie.setName(movieUpdated.getName());
                            movie.setDate(movieUpdated.getDate());
                            movie.setProfit(movieUpdated.getProfit());
                            movie.setGenre(movieUpdated.getGenre());
                            movie.setPlatform(movieUpdated.getPlatform());
                            break;
                        }
                    }
                    notifyAdapter();
                }
            }
        };
    }

    private Callback<Boolean> deleteFromDBCallback(int position){
        return new Callback<Boolean>() {
            @Override
            public void runResultOnUiThread(Boolean movieDeleted) {
                if(movieDeleted){

                    listMovies.remove(position);
                    notifyAdapter();
                }
            }
        };
    }

    private void selectAllFromDBThreadVersion2(){
        Thread thread = new Thread() {
            @Override
            public void run() {
                final List<Movie> moviesSelected = movieService.selectAllVersion2();
                new Handler(getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        if (moviesSelected != null) {
                            listMovies.clear();
                            listMovies.addAll(moviesSelected);
                            notifyAdapter();
                        }
                    }
                });
            }
        };
        thread.start();
    }
}