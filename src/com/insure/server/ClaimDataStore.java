package com.insure.server;

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

    public int createClaim(String description){

        int uuid = uuidTracker.getAndIncrement();

        Claim claim = new Claim(uuid, description);
        dataStore.put(uuid, claim);

        return uuid;
    }

    public Claim retrieveClaim(int id){
        return dataStore.get(id);
    }

    public void updateClaim(int id, String newDescription){
        dataStore.replace(id, dataStore.get(id), new Claim(id, newDescription));
    }
}
