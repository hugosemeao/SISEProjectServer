package com.insure.server;

import java.util.Date;

public class Document {
    private Claim belongsTo;
    private String content;
    private Date date;
    private int uuid;
    private String signature;

    public Document(String content, String signature, Claim claim, int uuid){
        this.date = new Date();
        this.uuid = uuid;
        this.signature = signature;
        this.content = content;
        this.belongsTo = claim;
    }

    public int getID(){
        return  this.uuid;
    }

    public String getDate(){
        return this.date.toString();
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