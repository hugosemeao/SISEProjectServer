package com.insure.server;

import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

public class Document {
    private Claim belongsTo;
    private String content;
    private Date date;
    private int uuid;
    private String signature;

    public Document(String content, String signature, Claim claim, int uuid){
        Date date = new Date();
        this.uuid = uuid;
        this.signature = signature;
        this.content = content;
        this.belongsTo = claim;
    }

    public int getID(){
        return  this.uuid;
    }

    public String getContent(){
        return "Doc ID: " + this.uuid +
                "\nAdded date :" + this.date.toString() +
                "\nContent :" + this.content;
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