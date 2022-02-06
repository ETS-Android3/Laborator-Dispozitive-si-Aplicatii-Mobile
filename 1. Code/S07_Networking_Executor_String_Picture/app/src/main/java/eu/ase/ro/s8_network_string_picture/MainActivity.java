package eu.ase.ro.s8_network_string_picture;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {
    TextView tv;
    ImageView iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initComponents();
        httpConnection();
    }

    private void initComponents() {
        tv = findViewById(R.id.main_tv);
        iv = findViewById(R.id.main_iv);
    }

    private void httpConnection() {
        ExecutorService service = Executors.newSingleThreadExecutor();
        service.execute(new Runnable() {
            @Override
            public void run() {
                try {


                    URL url = new URL("https://pastebin.com/raw/9MY0zc0W");

                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    InputStream inputStream = connection.getInputStream();
                    InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                    StringBuilder stringResult = new StringBuilder();
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        stringResult.append(line);
                    }

                    url = new URL("https://dice.ase.ro/img/dice_logo_web");

                    connection = (HttpURLConnection) url.openConnection();
                    inputStream = connection.getInputStream();

                    Bitmap picture = BitmapFactory.decodeStream(inputStream);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tv.setText(stringResult);
                            iv.setImageBitmap(picture);
                        }
                    });

                    try {
                        bufferedReader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        inputStreamReader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        inputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    connection.disconnect();

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }


        });
    }

}