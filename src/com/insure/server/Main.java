package com.insure.server;

import javax.swing.*;
import javax.xml.ws.Endpoint;

public class Main {
    public static void main(String args[]){

        ClaimDataStore claim = new ClaimDataStore();

        String ip = JOptionPane.showInputDialog(null,"Please inform the IP to run the server", "localhost");

        if (ip == null){ return; }
        Endpoint.publish("http://" + ip + ":8090/docstorage", claim);

        System.out.println("\nServer is running...\n");
    }
}
