package eu.ase.ro.s12_graficabidimensionala_barchart.firebase;


public interface Callback<R> {
    void runResultOnUiThread(R result);
}
