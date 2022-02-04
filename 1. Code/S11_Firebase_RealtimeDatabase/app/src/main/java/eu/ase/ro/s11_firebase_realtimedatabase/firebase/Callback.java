package eu.ase.ro.s11_firebase_realtimedatabase.firebase;

public interface Callback<R> {
    void runResultOnUiThread(R result);
}
