package com.insure.server;

public class Claim {
        private final int uuid;
        private String description;

        public Claim(int id, String description){
            this.description= description;
            this.uuid=id;
        };

        public String toString(){
            return "Claim ID :"+uuid+"\n Description:  "+description;
        };
    }
