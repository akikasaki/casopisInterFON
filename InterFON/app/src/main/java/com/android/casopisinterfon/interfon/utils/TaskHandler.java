package com.android.casopisinterfon.interfon.utils;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Class for handling background jobs in non UI/Main threads
 * which are created and managed by this class in pool of threads.
 */
public class TaskHandler {

    private static final int MAX_NUM_OF_THREAD = 10;

    /**
     * Pool of threads, max num of active threads is {@value #MAX_NUM_OF_THREAD}.
     */
    private static ExecutorService mExecutor = Executors.newFixedThreadPool(MAX_NUM_OF_THREAD);

    /**
     * Method for submitting new task to be run on background thread.
     * @param r {@link Runnable} task to be completed.
     */
    public static void submitTask(Runnable r){
        mExecutor.submit(r);
    }
}
