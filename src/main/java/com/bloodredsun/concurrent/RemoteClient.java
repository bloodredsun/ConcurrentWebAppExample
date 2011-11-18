package com.bloodredsun.concurrent;

import org.mortbay.jetty.client.ContentExchange;
import org.mortbay.jetty.client.HttpClient;

import java.io.IOException;

public class RemoteClient {

    HttpClient httpClient = new HttpClient();

    public void setHttpClient(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    public void execute(final Tuple tuple) throws IOException {

        ContentExchange exchange = new ContentExchange() {
            protected void onResponseComplete() throws IOException {
                super.onResponseComplete();
                String responseContent = this.getResponseContent();
                tuple.setResponse(responseContent);

            }
        };
        //set the method and the url
        exchange.setMethod("GET");
        exchange.setURL(tuple.getUrl());
        // start the exchange
        httpClient.send(exchange);
    }

}
