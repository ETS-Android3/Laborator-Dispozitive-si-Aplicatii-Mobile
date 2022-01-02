package eu.ase.ro.s7_networking_thread_json.activitati;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Date;

import eu.ase.ro.s7_networking_thread_json.R;
import eu.ase.ro.s7_networking_thread_json.clase.DateConverter;
import eu.ase.ro.s7_networking_thread_json.clase.Movie;
import eu.ase.ro.s7_networking_thread_json.network.HttpManager;
import eu.ase.ro.s7_networking_thread_json.parsare.MovieJsonParser;

public class MainActivity extends AppCompatActivity {
    ListView lvMovies;
    ArrayList<Movie> listMovies = new ArrayList<>();

    private static final String MOVIE_URL = "https://jsonkeeper.com/b/WXXU";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initComponents();
        lvPopulateAdapterDefault();
        loadMoviesFromURL();

        Date date = new DateConverter().fromString("05-05-1998");
        listMovies.add(new Movie("Deep Impact",date,693,"Romance", "Netflix"));


    }
    private void initComponents(){
        lvMovies = findViewById(R.id.main_lv_movies);
    }

    private void lvPopulateAdapterDefault(){
        ArrayAdapter<Movie> adapterLvDefault = new ArrayAdapter<>(
                getApplicationContext(),
                android.R.layout.simple_list_item_1,
                listMovies);
        lvMovies.setAdapter(adapterLvDefault);
        notifyAdapter();
    }


    private void notifyAdapter(){
        ArrayAdapter adapterNotify = (ArrayAdapter) lvMovies.getAdapter();
        adapterNotify.notifyDataSetChanged();
    }

    private void loadMoviesFromURL(){

        Thread thread = new Thread(){
            @Override
            public void run() {
                super.run();
                HttpManager httpManager = new HttpManager(MOVIE_URL);
                String result = httpManager.process();

                new Handler(getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        listMovies.addAll(MovieJsonParser.fromJson(result));
                        notifyAdapter();

                    }
                });
            }
        };
        thread.start();

    }
}