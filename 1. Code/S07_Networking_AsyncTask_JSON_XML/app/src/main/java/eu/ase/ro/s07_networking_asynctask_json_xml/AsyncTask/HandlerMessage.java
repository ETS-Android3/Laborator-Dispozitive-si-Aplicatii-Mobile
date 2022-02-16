package eu.ase.ro.s07_networking_asynctask_json_xml.AsyncTask;

public class HandlerMessage<R> implements Runnable{

    //PAS 1: Pentru Handler avem nevoie de rezultat si zona din Main Thread in care dorim sa punem rezultatul
    // - sunt final deoarece ne dorim sa nu mai schimbam referinta catre acel mesaj, ne asiguram astfel ca acel mesaj
    // e unic, la fel si zona din MainThread - iar pentru ca punem final nu mai putem face set() pe ele
    private final R result;
    private final Callback<R> mainThreadZone;

    public HandlerMessage(R result, Callback<R> mainThreadZone) {
        this.result = result;
        this.mainThreadZone = mainThreadZone;
    }

    @Override
    public void run() {
        mainThreadZone.runResultOnUiThread(result);
    }
}