package eu.ase.ro.s05_fragmente_listview.fragmente;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import eu.ase.ro.s05_fragmente_listview.R;
import eu.ase.ro.s05_fragmente_listview.clase.Movie;

public class HomeFragment extends Fragment {
    //PAS 1: Declarare Lista NESTATICA
    public  ArrayList<Movie> listMovie;

    //PAS 2: Ne declaram un ListView
    public ListView lvMovies;

    public HomeFragment() {
    }

    // PAS 3: Lista este trimisa ca si parametru
    public static HomeFragment newInstance(ArrayList<Movie> listMovie) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();

        //PAS 4: Impachetam lista in Bundle
        args.putParcelableArrayList("MOVIES_KEY", listMovie);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            //PAS 5: Citim parametrii din Bundle-ul setat in newInstance
            listMovie = getArguments().getParcelableArrayList("MOVIES_KEY");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        //PAS 6: Legare List View Java-XML
        lvMovies = view.findViewById(R.id.homeFragment_lv_movies);

        //PAS 7: Construire Adaptor
        if(getContext() !=null){
            ArrayAdapter<Movie> adaptorMoviesLV = new ArrayAdapter<>(getContext().getApplicationContext(),
                    android.R.layout.simple_list_item_1,listMovie);
            lvMovies.setAdapter(adaptorMoviesLV);
        }
        return view;
    }

    //PAS 8: Notificare adapter - este apelata in MainActivity.java - onActivtyResult
    public void notiyAdapter() {
        ArrayAdapter<Movie> adapter = (ArrayAdapter<Movie>) lvMovies.getAdapter();
        adapter.notifyDataSetChanged();
    }
}