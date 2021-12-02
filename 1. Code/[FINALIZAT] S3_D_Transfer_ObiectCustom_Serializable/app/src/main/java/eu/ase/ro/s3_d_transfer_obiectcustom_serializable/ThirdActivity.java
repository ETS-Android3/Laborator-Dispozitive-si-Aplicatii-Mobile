package eu.ase.ro.s3_d_transfer_obiectcustom_serializable;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import eu.ase.ro.s3_d_transfer_obiectcustom_serializable.classes.Movie;

public class ThirdActivity extends AppCompatActivity {
    ListView lv;
    ArrayList<Movie> movieList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        lv= findViewById(R.id.thrid_lv);
        Intent intent = getIntent();
        if(intent.hasExtra(MainActivity.KEY_MOVIE)){
             movieList = (ArrayList<Movie>) intent.getSerializableExtra(MainActivity.KEY_MOVIE);

        }
        ArrayAdapter adapter = new ArrayAdapter<Movie>(getApplicationContext(), android.R.layout.simple_list_item_1, movieList);
        lv.setAdapter(adapter);
    }
}