package com.insure.server;

import javax.jws.WebService;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@WebService
public class ClaimDataStore {
    AtomicInteger uuid=new AtomicInteger();
    ConcurrentHashMap<Integer,Claim> dataStore;

    public ClaimDataStore() {
        uuid=new AtomicInteger(1);
        dataStore=new ConcurrentHashMap<>();
    }

    public void createClaim(String description){
        Claim claim=new Claim(uuid.intValue(), description);
        dataStore.put(uuid.intValue(),claim);
    }

    public Claim retrieveClaim(int id){
        return dataStore.get(id);
    }

    public void updateClaim(int id, String newDescription){
        dataStore.replace(uuid.intValue(),dataStore.get(uuid),new Claim(uuid.intValue(),newDescription));
    }

    public String checkIfWorking(){
        return "Is working you dumbass!";
    }
}
