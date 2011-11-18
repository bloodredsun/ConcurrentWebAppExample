package com.bloodredsun.concurrent;

import org.mortbay.jetty.client.ContentExchange;
import org.mortbay.jetty.client.HttpClient;

import java.io.IOException;

public class RemoteClient {

    HttpClient client;

    public RemoteClient(HttpClient client) {
        this.client = client;
    }

    public void execute(final Tuple tuple) throws IOException {

        ContentExchange exchange = new ContentExchange() {
            // define the callback method to process the response when you get it back
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
        client.send(exchange);
    }

}
