package eu.ase.ro.s12_graficabidimensionala_barchart.activitati;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import eu.ase.ro.s12_graficabidimensionala_barchart.R;
import eu.ase.ro.s12_graficabidimensionala_barchart.clase.Movie;
import eu.ase.ro.s12_graficabidimensionala_barchart.grafic.BarChartView;

public class ThirdActivity extends AppCompatActivity {

    public static String SEND_MAIN_THIRD_LIST_MOVIES = "sendListMovies";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //PAS 1: Preluare lista cand Main Activity
        List<Movie> listMovies = (List<Movie>) getIntent().getSerializableExtra(SEND_MAIN_THIRD_LIST_MOVIES);

        //PAS 2: Creare Map pe baza listei primite
        Map<String, Integer> sursaDateGrafic = getSource(listMovies);

        //PAS 3: Setare layout grafic in aplicatie
        setContentView(new BarChartView(getApplicationContext(), sursaDateGrafic));
    }

    //CONSTRUIRE MAPA
    private Map<String, Integer> getSource(List<Movie> listMovies) {
        //PAS 3: Validam parametrii de intrare
        if(listMovies == null || listMovies.isEmpty()){
            return null;
        }
        Map<String, Integer> sursaDateGrafic = new HashMap<>();
        for(Movie movie: listMovies){
            //Daca avem deja platforma setata, marim doar nr filmelor
            if(sursaDateGrafic.containsKey(movie.getGenre())){
                Integer valoareCurenta = sursaDateGrafic.get(movie.getGenre());
                Integer valoareNoua = (valoareCurenta != null ? valoareCurenta: 0) + 1;
                sursaDateGrafic.put(movie.getGenre(), valoareNoua);
            } else{
                sursaDateGrafic.put(movie.getGenre(), 1);
            }
        }

        return sursaDateGrafic;
    }
}