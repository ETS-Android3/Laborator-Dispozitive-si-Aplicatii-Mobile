package eu.ase.ro.s07_networking_asynctask_json_xml.AsyncTask;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class AsyncTaskRunner {
    //PAS 1: Pentru AsyncTaskRunner avem nevoie de:
    // - un Executor: metoda newCachedThreadPool() calculeaza cate threaduri se pot executa paralel.
    // - un Handler
    private final Executor executor = Executors.newCachedThreadPool();
    private final Handler handler = new Handler(Looper.getMainLooper());

    public <R> void executeAsync(Callable<R> asyncOperation, Callback<R> mainThreadZone) {
        try {
            executor.execute(new RunnableTask<>(handler, asyncOperation, mainThreadZone));
        } catch (Exception e) {
            Log.i("AsyncTaskRunner", "fail call executeAsync" + e.getMessage());
        }
    }
}
