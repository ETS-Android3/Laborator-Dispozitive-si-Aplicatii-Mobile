package eu.ase.ro.s4_meniu_clasic;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    TextView tvMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvMessage = findViewById(R.id.main_tv_message);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_classic,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);
        if(item.getItemId() == R.id.menu_item_option1){
            tvMessage.setTextColor(Color.RED);
        } else if(item.getItemId() == R.id.menu_item_option2){
            tvMessage.setTextColor(Color.BLUE);
        }
        tvMessage.setText(item.getTitle());
        Toast.makeText(getApplicationContext(),
                getString(R.string.menu_options,item.getTitle()),
                Toast.LENGTH_SHORT)
                .show();
        return true;
    }
}