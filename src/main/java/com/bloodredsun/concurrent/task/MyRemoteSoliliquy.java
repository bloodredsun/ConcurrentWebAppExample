package com.bloodredsun.concurrent.task;

import com.bloodredsun.concurrent.RemoteClient;
import com.bloodredsun.concurrent.Tuple;

import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;

/**
 * Created by IntelliJ IDEA.
 *
 * @author Martin Anderson
 *         Date: 18/11/2011
 *         Time: 10:58
 */
public class MyRemoteSoliliquy {


    RemoteClient remoteClient;

    public MyRemoteSoliliquy(RemoteClient remoteClient) {
        this.remoteClient = remoteClient;
    }

    public Future<String> call(Future<String> response) throws IOException {
        Tuple tuple = new Tuple("http://localhost:8080/endpoint.jsp");
        remoteClient.execute(tuple);
        return tuple.getFutureResponse();
    }
}
