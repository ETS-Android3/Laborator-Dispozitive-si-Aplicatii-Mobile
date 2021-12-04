package eu.ase.ro.s3_b_transmitere_obiectcustom_simplu_serializable;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class SecondActivity extends AppCompatActivity {
    TextView tvName, tvProfit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        initComponents();

        Intent intent = getIntent();
        if(intent.hasExtra(MainActivity.KEY_SIMPLE_OBJECT)){
            Movie movie = (Movie) intent.getSerializableExtra(MainActivity.KEY_SIMPLE_OBJECT);
            tvName.setText(movie.getName());
            tvProfit.setText(""+ movie.getProfit());
        }
    }

    private void initComponents(){
        tvName = findViewById(R.id.second_tv_name);
        tvProfit = findViewById(R.id.second_tv_profit);
    }
}