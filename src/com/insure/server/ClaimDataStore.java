package com.insure.server;

import com.insure.server.security.DecryptPub;

import javax.crypto.NoSuchPaddingException;
import javax.jws.WebService;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@WebService
public class ClaimDataStore {
    AtomicInteger uuidTracker;
    ConcurrentHashMap<Integer, Claim> dataStore;

    //Constructor of connection handler
    public ClaimDataStore() {
        uuidTracker = new AtomicInteger(1);
        dataStore = new ConcurrentHashMap<Integer, Claim>();
    }

    //create claim
    public int createClaim(String client, String encryptedDescription) throws Exception {

        if(!client.substring(0,7).contentEquals("insured")){
            throw new Exception("Only a insured can create a claim.");
        }

        System.out.println("Encrypted: " + encryptedDescription);
        String description = (new DecryptPub(client, encryptedDescription)).getDecryptedMsg();
        System.out.println("Message: " + description);

        int uuid = uuidTracker.getAndIncrement();

        //temporary!!
        int clientID =  Integer.parseInt(client.substring(7));

        Claim claim = new Claim(uuid, description, clientID);
        dataStore.put(uuid, claim);

        return uuid;
    }

    //retrieve claim information
    public String claimToString(String client, int claimID){
        return retrieveClaim(claimID).toString();
    }

    // add document to claim
    public int addDocument(String client, int claimID, String docContent){
        return retrieveClaim(claimID).addDocument(docContent);
    }

    public String listDocuments(String client, int claimID){
        return retrieveClaim(claimID).documentKeys().toString();
    }

    public String viewDocument(String client, int claimID, int docID){
        return retrieveClaim(claimID).getDocumentContent(docID);
    }

    private Claim retrieveClaim(int id){
        return dataStore.get(id);
    }

    public void editDocument(String clientID, int ClaimID, int documentID, String newContent){
        dataStore.get(ClaimID).editDocument(documentID, newContent);
    }

    /*
    public void updateClaim(int id, String newDescription, int clientID){
        dataStore.replace(id, dataStore.get(id), new Claim(id, newDescription, clientID));
    }
    */

}
