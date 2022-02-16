package eu.ase.ro.s07_networking_asynctask_json_xml.activitati;


import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.Callable;

import eu.ase.ro.s07_networking_asynctask_json_xml.AsyncTask.AsyncTaskRunner;
import eu.ase.ro.s07_networking_asynctask_json_xml.AsyncTask.Callback;
import eu.ase.ro.s07_networking_asynctask_json_xml.Networking.HttpManager;
import eu.ase.ro.s07_networking_asynctask_json_xml.Parsare.MovieJsonParser;
import eu.ase.ro.s07_networking_asynctask_json_xml.Parsare.MovieXmlParser;
import eu.ase.ro.s07_networking_asynctask_json_xml.R;
import eu.ase.ro.s07_networking_asynctask_json_xml.clase.Constants;
import eu.ase.ro.s07_networking_asynctask_json_xml.clase.DateConverter;
import eu.ase.ro.s07_networking_asynctask_json_xml.clase.Movie;


public class MainActivity extends AppCompatActivity {
    private ListView lvMovies;
    private ArrayList<Movie> listMovies = new ArrayList<>();

    private final AsyncTaskRunner asyncTaskRunner = new AsyncTaskRunner();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initComponents();
        lvPopulateAdapterDefault();

        Date date = new DateConverter().fromString("05-05-1998");
        listMovies.add(new Movie("Deep Impact", date, 693, "Romance", "Netflix"));

        loadMovieFromJsonHttp();
        loadMovieFromXmlHttp();
    }

    private void initComponents() {
        lvMovies = findViewById(R.id.main_lv_movies);
    }

    private void lvPopulateAdapterDefault() {
        ArrayAdapter<Movie> adapterLvDefault = new ArrayAdapter<>(
                getApplicationContext(),
                android.R.layout.simple_list_item_1,
                listMovies);
        lvMovies.setAdapter(adapterLvDefault);
        notifyAdapter();
    }

    private void notifyAdapter() {
        ArrayAdapter adapterNotify = (ArrayAdapter) lvMovies.getAdapter();
        adapterNotify.notifyDataSetChanged();
    }

    private void loadMovieFromJsonHttp(){
        Callable<String> ayncOperation = new HttpManager(Constants.MOVIE_URL_JSON);
        Callback<String> mainThreadZone = new Callback<String>() {
            @Override
            public void runResultOnUiThread(String result) {
                Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                listMovies.addAll(MovieJsonParser.fromJson(result));
                notifyAdapter();

            }
        };
        asyncTaskRunner.executeAsync(ayncOperation, mainThreadZone);
    }

    private void loadMovieFromXmlHttp(){
        Callable<String> asyncOperation = new HttpManager(Constants.MOVIE_URL_XML);
        Callback<String> mainThreadZone = new Callback<String>() {
            @Override
            public void runResultOnUiThread(String result) {
                listMovies.addAll(MovieXmlParser.fromXml(result));
                notifyAdapter();
            }
        };
        asyncTaskRunner.executeAsync(asyncOperation, mainThreadZone);
    }
}