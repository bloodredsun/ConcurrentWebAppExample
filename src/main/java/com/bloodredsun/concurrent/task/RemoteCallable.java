package com.bloodredsun.concurrent.task;


import com.bloodredsun.concurrent.RemoteClient;
import com.bloodredsun.concurrent.Tuple;

import java.io.IOException;
import java.util.concurrent.Callable;

public class RemoteCallable implements Callable<String> {


    RemoteClient remoteClient;

    public RemoteCallable(RemoteClient remoteClient) {
        this.remoteClient = remoteClient;
    }

    @Override
    public String call() throws IOException {
        Tuple tuple = new Tuple("http://localhost:8080/endpoint.jsp");
        remoteClient.execute(tuple);
        return tuple.getResponse();
    }
}