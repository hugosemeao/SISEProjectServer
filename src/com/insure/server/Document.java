package com.insure.server;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

public class Document {
    private static AtomicInteger uuidTracker = new AtomicInteger(1);

    private Claim belongsTo;
    private String content;
    private Date date;
    private int uuid;


    public Document(String content, Claim claim){
        //estas duas linhas tem que correr sem interrupcao
        //-------------------------------------------------
        Date date = new Date();
        int uuid = uuidTracker.getAndIncrement();
        //-------------------------------------------------

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

    public void editDocument(String content){
        this.content = content;
    }

}