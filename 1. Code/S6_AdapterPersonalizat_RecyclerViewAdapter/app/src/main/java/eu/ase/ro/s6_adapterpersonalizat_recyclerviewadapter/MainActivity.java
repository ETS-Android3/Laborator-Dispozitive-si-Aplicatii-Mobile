package eu.ase.ro.s6_adapterpersonalizat_recyclerviewadapter;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public static final int REQUEST_CODE = 200;
    Button btnOpenForm;
    RecyclerView rvMovies;
    ArrayList<Movie> listMovies = new ArrayList<Movie>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initComponents();
        clickBtnOpenForm();
        setRecyclerViewAdapter();
    }

    private void initComponents(){
        btnOpenForm = findViewById(R.id.main_btn_openForm);
        rvMovies = findViewById(R.id.main_rv_movies);
    }

    private void clickBtnOpenForm(){
        btnOpenForm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),SecondActivity.class);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE && resultCode == RESULT_OK && data !=null){
            Movie movie = data.getParcelableExtra(SecondActivity.KEY_MOVIE);

            if(movie !=null) {
                listMovies.add(movie);
                notifyAdapter();
            }
        }
    }
    private void setRecyclerViewAdapter(){
        MovieAdapter movieAdapter = new MovieAdapter(getApplicationContext(),listMovies);
        rvMovies.setAdapter(movieAdapter);
        rvMovies.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
    }

    private void notifyAdapter(){
        MovieAdapter movieAdapter = (MovieAdapter) rvMovies.getAdapter();
        movieAdapter.notifyDataSetChanged();
    }
}