package com.bloodredsun.concurrent;

import com.google.common.util.concurrent.SettableFuture;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class Tuple {

    String url;
    SettableFuture<String> response = SettableFuture.create();

    public Tuple(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public String getResponse() {
        String value = "";
        try {
            value = response.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return value;
    }

    public Future<String> getFutureResponse(){
        return response;
    }

    public void setResponse(String response) {
        this.response.set(response);
    }
}
