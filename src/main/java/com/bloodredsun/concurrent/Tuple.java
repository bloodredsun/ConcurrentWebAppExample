package com.bloodredsun.concurrent;

import com.google.common.util.concurrent.SettableFuture;

import java.util.concurrent.ExecutionException;

public class Tuple {

    String url;
    String request;
    SettableFuture<String> response = SettableFuture.create();


    public Tuple() {
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
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

    public void setResponse(String response) {
        this.response.set(response);
    }
}
