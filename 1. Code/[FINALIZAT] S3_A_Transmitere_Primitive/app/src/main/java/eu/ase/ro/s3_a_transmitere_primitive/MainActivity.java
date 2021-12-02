package eu.ase.ro.s3_a_transmitere_primitive;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    public static final String KEY_STRING = "sendString";
    Button btnSendString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initComponents();
        clickBtnSendString();

    }

    private void initComponents(){
        btnSendString = findViewById(R.id.main_btn_sendString);
    }

    private void clickBtnSendString(){
        btnSendString.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SecondActivity.class);

                String movieInfo = "The name of movie is A Little Chaos.";
                intent.putExtra(KEY_STRING,movieInfo);

                startActivity(intent);
            }
        });
    }
}