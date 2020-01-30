package com.insure.server;

import java.util.HashSet;

public class Claim {
    private final int uuid;
    private String description;
    private HashSet<String> docs;


    public Claim(int id, String description){
        this.description= description;
        this.uuid=id;
    };

    public String toString(){
        return "Claim ID :"+uuid+"\n Description:  "+description;
    };
}
