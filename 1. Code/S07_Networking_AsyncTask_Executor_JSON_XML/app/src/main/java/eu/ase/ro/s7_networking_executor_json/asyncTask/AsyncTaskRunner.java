package eu.ase.ro.s7_networking_executor_json.asyncTask;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class AsyncTaskRunner {

    //PAS 1: Definire intastante ale claselor Executor si Handler
    // Executor: metoda new CachedThreadPool calculeaza cate threaduri poate procesa in paralel.
    private final Executor executor = Executors.newCachedThreadPool();
    private final Handler handler = new Handler(Looper.getMainLooper());

    //PAS 2: Metoda in care cream un nou Thread / Runnable Task
    // - Callable: informatia pe care vreau sa o calculez pe alt Thread
    // - Callback: zona in care intoarcem rezultatul de la Callback
    public <R> void executeAsync(Callable<R> operatiaAsincrona, Callback<R> zonaUndePunRezultatCallable){
        try{
            executor.execute(runRunnableTask(operatiaAsincrona,zonaUndePunRezultatCallable));
        }
        catch(Exception e){
            Log.e("AsyncTaskRunner", "failed call executeAsync " + e.getMessage());
        }

    }

    //PAS 3: Metoda in care realizam operatia din Runnable Task / Thread
    private <R> Runnable runRunnableTask(Callable<R> operatiaAsincrona, Callback<R> zonaUndePunRezultatCallable) {
        return new Runnable() {
            @Override
            public void run() {
                try{
                    R result = operatiaAsincrona.call();
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            zonaUndePunRezultatCallable.runResultOnUiThread(result);
                        }
                    });


                }catch (Exception e){
                    Log.e("AsyncTaskRunner", "failed call asyncOperation " + e.getMessage());
                }
            }
        };
    }
}
