package eu.ase.ro.s3_c_transfer_obiectcustom_parcelable;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public static final int REQUEST_CODE = 200;
    Button btnOpenForm;
    ListView lvMovies;
    ArrayList<String> listMovies = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initComponents();
        lvSetAdaptor();
        clickBtnOpenForm();

    }

    private void initComponents(){
        btnOpenForm = findViewById(R.id.main_btn_openForm);
        lvMovies = findViewById(R.id.main_lv_movies);
    }

    private void lvSetAdaptor(){
        ArrayAdapter<String> adaptorLV = new ArrayAdapter<String>(
                getApplicationContext(),
                android.R.layout.simple_list_item_1,
                listMovies);
        lvMovies.setAdapter(adaptorLV);
        adaptorLV.notifyDataSetChanged();
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
            listMovies.add(movie.toString());
        }
    }
}