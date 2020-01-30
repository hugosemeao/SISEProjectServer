package com.insure.server;

import com.sun.xml.ws.transport.tcp.connectioncache.spi.concurrent.ConcurrentQueue;

import javax.jws.WebService;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@WebService
public class ClaimDataStore {
    AtomicInteger uuidTracker;
    ConcurrentHashMap<Integer, Claim> dataStore;


    public ClaimDataStore() {
        uuidTracker = new AtomicInteger(1);
        dataStore = new ConcurrentHashMap<Integer, Claim>();
    }

    public int createClaim(String description,int clientID){

        int uuid = uuidTracker.getAndIncrement();

        Claim claim = new Claim(uuid, description, clientID);
        dataStore.put(uuid, claim);

        return uuid;
    }

    public Claim retrieveClaim(int id){
        return dataStore.get(id);
    }

    public void updateClaim(int id, String newDescription, int clientID){
        dataStore.replace(id, dataStore.get(id), new Claim(id, newDescription, clientID));
    }

    public void addDocument(int uuid, String docContent){
        retrieveClaim(uuid).addDocument(docContent);
    }
}
