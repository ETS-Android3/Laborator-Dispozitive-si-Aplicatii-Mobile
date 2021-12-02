package eu.ase.ro.s3_d_transfer_obiectcustom_serializable;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;

import eu.ase.ro.s3_d_transfer_obiectcustom_serializable.adapter.MovieAdapter;
import eu.ase.ro.s3_d_transfer_obiectcustom_serializable.classes.DateConverter;
import eu.ase.ro.s3_d_transfer_obiectcustom_serializable.classes.Movie;
import eu.ase.ro.s3_d_transfer_obiectcustom_serializable.classes.Platform;

public class MainActivity extends AppCompatActivity {
    public static final int REQUEST_CODE_SECOND_ACTIVTY = 200;
    Button btnOpenForm;
    ListView lvMovies;
    ArrayList<Movie> listMovies = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initComponents();
        //lvPopulateAdapterDefault();
        lvPopulateAdapterCustom();
        clickBtnOpenForm();

        Date date = new DateConverter().fromString("05/05/1998");
        listMovies.add(new Movie("Giol Adriana",date,693,"Romance", Platform.NETFLIX));
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
       MovieAdapter adapterLvCustom = new MovieAdapter(getApplicationContext(),R.layout.adapter_lv_row,listMovies,getLayoutInflater());
        lvMovies.setAdapter(adapterLvCustom);
    }

    private void clickBtnOpenForm(){
        btnOpenForm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SecondActivity.class);
                startActivityForResult(intent, REQUEST_CODE_SECOND_ACTIVTY);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE_SECOND_ACTIVTY && resultCode == RESULT_OK && data != null){
            Movie movie = (Movie) data.getSerializableExtra(SecondActivity.KEY_MOVIE);

            if(movie !=null) {
                listMovies.add(movie);
                notifyAdapter();
                Toast.makeText(getApplicationContext(), R.string.popUp_addMovie,Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void notifyAdapter(){
        ArrayAdapter adapterNotify = (ArrayAdapter) lvMovies.getAdapter();
        adapterNotify.notifyDataSetChanged();
    }
}