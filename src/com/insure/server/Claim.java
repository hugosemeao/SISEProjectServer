package com.insure.server;

import java.util.HashMap;
import java.util.HashSet;
import java.util.concurrent.atomic.AtomicInteger;

public class Claim {
    private final int uuid; //claim identifier
    private String description; //claim description
    private HashMap<Integer, String> docStore; //claim documents storage
    private AtomicInteger docId; //claim id tracker
    private final int idClient; //claim owner id


    public Claim(int id, String description, int idClient){
        this.description= description;
        this.uuid=id;
        docStore= new HashMap<>();
        docId= new AtomicInteger(1);
        this.idClient=idClient;
    }

    public String toString(){
        return "Claim ID :"+uuid+"\n Description:  "+description;
    }

    public void addDocument(String docContent){
        docStore.put(docId.getAndIncrement(), docContent);
    }
}
