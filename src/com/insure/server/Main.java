package com.insure.server;

import javax.xml.ws.Endpoint;

public class Main {
    public static void main(String args[]){

        ClaimDataStore claim = new ClaimDataStore();

        Endpoint.publish("http://localhost:8090/docstorage", claim);

        System.out.println("Server is running...");
    }
}
