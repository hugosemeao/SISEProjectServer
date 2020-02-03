package com.insure.server;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

public class Claim {
    private final int uuid; //claim identifier
    private String description; //claim description
    private HashMap<Integer, Document> docStore; //documents of this claim
    private AtomicInteger docId; //claim id tracker
    private final int idClient; //claim owner id


    public Claim(int id, String description, int idClient){
        this.description= description;
        this.uuid=id;
        docStore= new HashMap<>();
        this.idClient=idClient;
    }

    public String toString(){
        return "Claim ID :"+uuid+"\n Description:  "+ description;
    }

    public int addDocument(String docContent){
        Document newDoc = new Document(docContent, this);
        int docId =  newDoc.getID();
        docStore.put(docId, newDoc);

        return docId;
    }

    public String getDocumentContent(int docID){
        return docStore.get(docID).getContent();
    }

    public Set<Integer> documentKeys(){
        return docStore.keySet();
    }

    public void editDocument(int docID, String newContent){
        docStore.get(docID).editDocument(newContent);
    }


}
