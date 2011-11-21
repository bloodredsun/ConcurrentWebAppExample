package com.bloodredsun.concurrent.task;

import com.google.common.util.concurrent.SettableFuture;

import java.util.concurrent.Future;

/**
 * @author Martin Anderson
 *         Date: 18/11/2011
 *         Time: 13:19
 */
public class Work {

    String url;
    SettableFuture<String> futureResponse = SettableFuture.create();

    public Work(String url) {
        this.url = url;
    }

    public Future<String> getFutureResponse() {
        return futureResponse;
    }

    public void setFutureResponse(String futureResponse) {
        this.futureResponse.set(futureResponse);
    }

    public String getUrl() {
        return url;
    }
}
