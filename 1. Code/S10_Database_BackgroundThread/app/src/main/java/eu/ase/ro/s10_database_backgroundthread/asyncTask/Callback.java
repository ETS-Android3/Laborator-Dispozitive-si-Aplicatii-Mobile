package eu.ase.ro.s10_database_backgroundthread.asyncTask;

public interface Callback<R> {

    void runResultOnUiThread(R result);
}