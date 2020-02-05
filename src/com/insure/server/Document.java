package com.insure.server;

import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

public class Document {
    private static AtomicInteger uuidTracker = new AtomicInteger(1);
    private Claim belongsTo;
    private String content;
    private Date date;
    private int uuid;
    private String signature;

    public Document(String content, String signature, Claim claim){
        //synchronized to guarantee that document id and date are increasing together
        synchronized (uuidTracker) {
            Date date = new Date();
            this.uuid = uuidTracker.getAndIncrement();
        }

        this.signature = signature;
        this.content = content;
        this.belongsTo = claim;
        this.uuid = uuid;
    }

    public int getID(){
        return  this.uuid;
    }

    public String documentToString(){
        return "Doc ID: " + this.uuid +
                "\nDate :" + this.date.toString() +
                "\nContent :" + this.content;
    }

    public String getContent(){
        return this.content;
    }

    public String getSignature(){
        return this.signature;
    }

    public void editDocument(String content, String signature){
        //synchronized to prevent from reading the document with the wrong signature
        synchronized (this) {
            this.content = content;
            this.signature = signature;
        }
    }
}