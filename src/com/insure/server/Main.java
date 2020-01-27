package com.insure.server;

import javax.xml.ws.Endpoint;

public class Main {
    public static void main(String args[]){
        System.out.println("Project template - server");
        ClaimDataStore claim = new ClaimDataStore();
        Endpoint.publish("http://146.193.7.111:8090/docstorage", claim);
    }
}
