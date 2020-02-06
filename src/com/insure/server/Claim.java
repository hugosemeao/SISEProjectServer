package com.insure.server;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class Claim {
    private final int uuid; //claim identifier
    private String description; //claim description
    private ConcurrentHashMap<Integer, Document> docStore; //documents of this claim
    private AtomicInteger docId; //claim id tracker
    private final String idClient; //claim owner id
    private AtomicInteger docTracker = new AtomicInteger(1);


    public Claim(int id, String description, String idClient){
        this.description= description;
        this.uuid=id;
        docStore= new ConcurrentHashMap<>();
        this.idClient = idClient;
    }

    @Override
    public String toString(){
        return "Claim ID :"+uuid+"\n Description:  "+ description;
    }

    public int addDocument(String docContent, String signature){
        synchronized (docTracker) {
            Document newDoc = new Document(docContent, signature, this, docTracker.getAndIncrement());
            int docId = newDoc.getID();
            docStore.put(docId, newDoc);
            return docId;
        }
    }

    public String getDocumentContent(int docID){
        return docStore.get(docID).getContent();
    }

    public String getDocumentSignature(int docID){
        return docStore.get(docID).getSignature();
    }

    public Set<Integer> documentKeys(){
        return docStore.keySet();
    }

    public void editDocument(int docID, String newContent, String signature){
        docStore.get(docID).editDocument(newContent, signature);
    }

    public String getIdClient(){
        return this.idClient;
    }

    public String getDocDate(int docID){
        return  docStore.get(docID).getDate();
    }
}
