package com.bloodredsun.concurrent.task;


import com.bloodredsun.concurrent.RemoteClient;
import com.bloodredsun.concurrent.Tuple;

import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

public class MyRemoteCallable implements Callable<String> {


    RemoteClient remoteClient;

    public MyRemoteCallable(RemoteClient remoteClient) {
        this.remoteClient = remoteClient;
    }

    @Override
    public String call() throws IOException, ExecutionException, InterruptedException {
        Work work = new Work("http://localhost:8080/endpoint.jsp?pong=threaded");
        remoteClient.execute(work);
        return work.getFutureResponse().get();
    }
}