package eu.ase.ro.s07_networking_asynctask_json_xml.AsyncTask;

public interface Callback<R> {

    void runResultOnUiThread(R result);
}