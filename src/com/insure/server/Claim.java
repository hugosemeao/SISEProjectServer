package com.insure.server;

import java.util.HashMap;
import java.util.HashSet;
import java.util.concurrent.atomic.AtomicInteger;

public class Claim {
    private final int uuid;
    private String description;
    private HashMap<Integer, String> docStore;
    private AtomicInteger docId;
    private final int idClient;


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
