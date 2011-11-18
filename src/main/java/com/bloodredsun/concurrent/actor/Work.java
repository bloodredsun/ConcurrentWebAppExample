package com.bloodredsun.concurrent.actor;

import com.google.common.util.concurrent.SettableFuture;

import java.util.concurrent.Future;

/**
 * Created by IntelliJ IDEA.
 *
 * @author Martin Anderson
 *         Date: 18/11/2011
 *         Time: 13:19
 */
public class Work {


    SettableFuture<String> futureResponse = SettableFuture.create();

    public Future<String> getFutureResponse() {
        return futureResponse;
    }

    public void setFutureResponse(String futureResponse) {
        this.futureResponse.set(futureResponse);
    }
}
