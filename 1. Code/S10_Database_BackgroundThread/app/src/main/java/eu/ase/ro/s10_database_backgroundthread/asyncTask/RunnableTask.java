package eu.ase.ro.s10_database_backgroundthread.asyncTask;

import android.os.Handler;
import android.util.Log;

import java.util.concurrent.Callable;

public class RunnableTask<R> implements Runnable {

    //PAS 1: Pentru RunnableTask avem nevoie:
    private final Handler handler;
    private final Callable<R> asyncOperation;
    private final Callback<R> mainThreadZone;


    //PAS 2: Implementam constructorul cu parametri sugerat
    public RunnableTask(Handler handler, Callable<R> asyncOperation, Callback<R> mainThreadZone) {
        this.handler = handler;
        this.asyncOperation = asyncOperation;
        this.mainThreadZone = mainThreadZone;
    }

    //PAS 3: Implementarea metodei run()
    @Override
    public void run() {
        try {
            final R result = asyncOperation.call();
            handler.post(new HandlerMessage<>(result,mainThreadZone));
        } catch (Exception e) {
            e.printStackTrace();
            Log.i("RunnableTask", "fail call Runnable Task");
        }
    }
}