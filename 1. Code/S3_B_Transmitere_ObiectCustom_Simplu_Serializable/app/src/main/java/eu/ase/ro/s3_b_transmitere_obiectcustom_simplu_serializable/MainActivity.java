package eu.ase.ro.s3_b_transmitere_obiectcustom_simplu_serializable;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    public static final String KEY_SIMPLE_OBJECT = "sendSimpleObject";
    Button btnSendSimpleObject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initComponents();
        clickBtnSendSimpleObject();

    }

    private void initComponents(){
        btnSendSimpleObject = findViewById(R.id.main_btn_sendSimpleObject);
    }

    private void clickBtnSendSimpleObject(){
        btnSendSimpleObject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),SecondActivity.class);

                Movie movie = new Movie("A Little Chaos", 450);
                intent.putExtra(KEY_SIMPLE_OBJECT, movie);

                startActivity(intent);
            }
        });
    }
}