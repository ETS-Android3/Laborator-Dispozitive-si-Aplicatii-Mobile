package eu.ase.ro.s11_firebase_backgroundthread.firebase;

public interface Callback<R> {
    void runResultOnUiThread(R result);
}
