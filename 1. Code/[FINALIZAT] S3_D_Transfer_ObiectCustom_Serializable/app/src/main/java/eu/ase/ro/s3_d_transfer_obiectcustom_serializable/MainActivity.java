package eu.ase.ro.s3_d_transfer_obiectcustom_serializable;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import eu.ase.ro.s3_d_transfer_obiectcustom_serializable.classes.Movie;

public class MainActivity extends AppCompatActivity {
    public static final int REQUEST_CODE_SECOND_ACTIVTY = 200;
    public static final String KEY_MOVIE = "sendList";
    Button btnOpenForm, btnOpenThird;
    ListView lvMovies;
    ArrayList<Movie> listMovies = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initComponents();
        lvPopulate();
        clickBtnOpenForm();
        clickBtnOpenThird();


    }
    private void initComponents(){
        btnOpenForm = findViewById(R.id.main_btn_openForm);
        lvMovies = findViewById(R.id.main_lv_movies);
        btnOpenThird = findViewById(R.id.main_btn_openThird);
    }

    private void lvPopulate(){
        ArrayAdapter<Movie> adapterLV = new ArrayAdapter<>(
                getApplicationContext(),
                android.R.layout.simple_list_item_1,
                listMovies);
        lvMovies.setAdapter(adapterLV);
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
    private void clickBtnOpenThird(){
        Intent intent = new Intent(getApplicationContext(),ThirdActivity.class);
       // ArrayList<Movie> listMovie = new ArrayList<Movie>();
        intent.putExtra(KEY_MOVIE,listMovies);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE_SECOND_ACTIVTY && resultCode == RESULT_OK && data != null){
            Movie movie = (Movie) data.getSerializableExtra(SecondActivity.KEY_MOVIE);

            if(movie !=null) {
                listMovies.add(movie);

                Toast.makeText(getApplicationContext(), R.string.popUp_addMovie,Toast.LENGTH_SHORT).show();
                ArrayAdapter adapterNotify = (ArrayAdapter) lvMovies.getAdapter();
                adapterNotify.notifyDataSetChanged();

            }
        }
    }
}