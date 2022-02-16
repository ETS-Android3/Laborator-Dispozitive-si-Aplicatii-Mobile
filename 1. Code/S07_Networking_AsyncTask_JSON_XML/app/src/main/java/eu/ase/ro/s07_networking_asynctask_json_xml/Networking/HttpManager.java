package eu.ase.ro.s07_networking_asynctask_json_xml.Networking;

import androidx.annotation.NonNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.Callable;

public class HttpManager implements Callable<String> {
    private final String urlAdress;

    //PAS 1: Definim cate o instanta pentru fiecare element pentru conectare la URL
    private URL url;
    private HttpURLConnection connection;

    private InputStream inputStream;
    private InputStreamReader inputStreamReader;
    private BufferedReader bufferedReader;


    //PAS 2: Adaugam un constructor doar pentru urlAdress;
    public HttpManager(String urlAdress) {
        this.urlAdress = urlAdress;
    }

    //PAS 3: Ne cream o metoda care se ne aduca continutul de la URL-ul respectiv
    private String process(){
        try {
            httpConnection();
            getInformationFromURL();
            return putInformationTogheter();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            closeConnections();
        }
        return null;
    }

    //PAS 4: S-a realizat HTTP CONNECTION  din schema
    private void httpConnection() throws IOException {
        url = new URL(urlAdress);
        connection = (HttpURLConnection) url.openConnection();
    }

    //PAS 5: Preiau informatia bucata cu bucata (pizza)
    private void getInformationFromURL() throws IOException {
        inputStream = connection.getInputStream();
        inputStreamReader = new InputStreamReader(inputStream);
        bufferedReader = new BufferedReader(inputStreamReader);
    }

    //PAS 6:Ansamblam bucatile preluate
    @NonNull
    private String putInformationTogheter() throws IOException {
        StringBuilder result = new StringBuilder();
        String line;
        while ((line  = bufferedReader.readLine()) !=null){
            result.append(line);
        }
        return result.toString();
    }

    //PAS 7: Inchidem conexiunile indiferent daca a mers procesarea sau nu
    private void closeConnections() {
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
    }

    @Override
    public String call() throws Exception {
        return process();
    }
}