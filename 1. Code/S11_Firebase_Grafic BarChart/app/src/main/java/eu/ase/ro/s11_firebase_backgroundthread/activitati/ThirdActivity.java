package eu.ase.ro.s11_firebase_backgroundthread.activitati;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import eu.ase.ro.s11_firebase_backgroundthread.PlatformGrafic;
import eu.ase.ro.s11_firebase_backgroundthread.R;
import eu.ase.ro.s11_firebase_backgroundthread.clase.Movie;

public class ThirdActivity extends AppCompatActivity {

    //PAS 1: Lista de filme pe care o primim de la Firebase
    ArrayList<Movie> listMovies;

    //PAS 2: Map - sursa de date pentru Grafic  - din lista de filme extragem doar platforma
    Map<String, Integer> sursaDateGrafic;

    //PAS 3: Linear Layout
    LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        initComponents();

        //PAS 4: Preluare lista de filme trimise din MainActivity
        Intent intent = getIntent();
        listMovies = (ArrayList<Movie>) intent.getSerializableExtra("sendList");

        //PAS 6: Acum putem popula Mapa cu valorile de care avem nevoie pentru grafic
        sursaDateGrafic = getSursaDate(listMovies);

        //PAS 7: Includerea a clasei BarChartView in Linear Layout
        linearLayout.addView(new PlatformGrafic(getApplicationContext(),sursaDateGrafic));
    }

    //PAS 5: Creare set de date pentru grafic
    private Map<String, Integer> getSursaDate(List<Movie> movies) {
        if (movies == null || movies.isEmpty()) {
            return new HashMap<>();
        }
        else {
            Map<String, Integer> sursaDate = new HashMap<>();
            for (Movie movie : movies) {
                //Daca exista deja platforma salvata, incrementam doar nr de filme de la accea platforma
                if (sursaDate.containsKey(movie.getGenre())) {
                    sursaDate.put(movie.getGenre(), sursaDate.get(movie.getGenre()) + 1);
                }
                //Daca platforma nu exista, o salvam
                else {
                    sursaDate.put(movie.getGenre(), 1);
                }
            }
            return sursaDate;
        }
    }

    private void initComponents(){
        linearLayout = findViewById(R.id.thrid_linearLayout);
    }
}