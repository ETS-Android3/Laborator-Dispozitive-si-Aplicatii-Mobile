package eu.ase.ro.s7_networking_executor_json.asyncTask;

public interface Callback<R> {

    void runResultOnUiThread(R result);
}
