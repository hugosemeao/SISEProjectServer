package com.insure.server;

import com.insure.server.security.DecryptPriv;
import com.insure.server.security.DecryptPub;
import com.insure.server.security.VerifySignature;

import javax.jws.WebService;
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
    public int createClaim(String client, String encryptedDescription, String signature) throws Exception {
        if(!client.substring(0,7).contentEquals("insured")){
            throw new Exception("Only a insured can create a claim.");
        }

        String originalMsg = DecryptPriv.decryptMsg(encryptedDescription);
        if(!VerifySignature.checkSignature(client, originalMsg, signature)){
            throw new Exception("The claim signature is not valid.");
        }

        int uuid = uuidTracker.getAndIncrement();

        Claim claim = new Claim(uuid, originalMsg, client);
        dataStore.put(uuid, claim);

        return uuid;
    }

    //retrieve claim information
    public String claimToString(String client, int claimID){
        //Decrypt claimId
        //check if client is owner of claimId or if client is officer
        //ToDo

        //retrieveClaim(claimID).getIdClient().contentEquals(client) || client.substring(0, 8).contentEquals("officer");
       //otherwise send exception

        return retrieveClaim(claimID).toString();
    }

    // add document to claim
    public int addDocument(String client, int claimID, String docContent){
        //Check Sigature (see createClaim)
        //ToDo
        return retrieveClaim(claimID).addDocument(docContent);
    }

    public String listDocuments(String client, int claimID){
        //Decrypt claimId
        //check if client is owner of claimId or if client is officer
        //ToDo (see claimToString)
        return retrieveClaim(claimID).documentKeys().toString();
    }

    public String viewDocument(String client, int claimID, int docID){
        //igual a listDocuments e claimToString
        //ToDo
        return retrieveClaim(claimID).getDocumentContent(docID);
    }

    private Claim retrieveClaim(int id){
        //interno, só se usa cá dentro -- apagar comentário
        return dataStore.get(id);
    }

    public void editDocument(String clientID, int ClaimID, int documentID, String newContent){
        //igual a create claim e addDocumet, i think
        //ToDo
        dataStore.get(ClaimID).editDocument(documentID, newContent);
    }

    /*
    public void updateClaim(int id, String newDescription, int clientID){
        dataStore.replace(id, dataStore.get(id), new Claim(id, newDescription, clientID));
    }
    */

}
