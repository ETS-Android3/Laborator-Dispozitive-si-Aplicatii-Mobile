package eu.ase.ro.s3_a_transmitere_primitive;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class SecondActivity extends AppCompatActivity {
    TextView tvMovieInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        initComponents();

        Intent intent = getIntent();
        if(intent.hasExtra(MainActivity.KEY_STRING)){
            String movieInfo = intent.getStringExtra(MainActivity.KEY_STRING);
            tvMovieInfo.setText(movieInfo);
        }
    }

    private void initComponents(){
        tvMovieInfo = findViewById(R.id.second_tv_string);
    }
}