package com.insure.server;

import com.insure.server.security.DecryptPriv;
import com.insure.server.security.DecryptPub;
import com.insure.server.security.EncryptPub;
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
            throw new ClientException("Only a insured can create a claim.");
        }

        String originalMsg = DecryptPriv.decryptMsg(encryptedDescription);
        if(!VerifySignature.checkSignature(client, originalMsg, signature)){
            throw new ClientException("The claim signature is not valid.");
        }

        int uuid = uuidTracker.getAndIncrement();

        Claim claim = new Claim(uuid, originalMsg, client);
        dataStore.put(uuid, claim);

        return uuid;
    }

    //retrieve claim information
    public String claimToString(String client, String claimID) throws Exception {
        int id = Integer.parseInt(DecryptPub.decryptMsg(client, claimID));    //Decrypt claimId

        //check if claim exists
        if (!dataStore.containsKey(id)) {
            throw new ClientException("Claim does not exist.");
        }

        //check if client is owner of claimId or if client is office
        if (!retrieveClaim(id).getIdClient().contentEquals(client) && !client.substring(0, 7).contentEquals("officer")){
            throw new ClientException("Client does not own this claim.");
        }

        return retrieveClaim(id).toString();
    }

    // add document to claim
    public int addDocument(String client, int claimID, String encryptedMsg, String signature) throws Exception {
        //check if claim exists
        if (!dataStore.containsKey(claimID)) {
            throw new ClientException("Claim does not exist.");
        }

        String originalMsg = DecryptPub.decryptMsg(client, encryptedMsg);
        if(!VerifySignature.checkSignature(client, originalMsg, signature)) {
            throw new ClientException("The claim signature is not valid.");
        }

        return retrieveClaim(claimID).addDocument(originalMsg, signature);
    }

    public String listDocuments(String client, String encryptedClaimID) throws Exception {
        //Decrypt claimId
        int claimID = Integer.parseInt(DecryptPub.decryptMsg(client, encryptedClaimID));

        //check if claim exists
        if (!dataStore.containsKey(claimID)) {
            throw new ClientException("Claim does not exist.");
        }

        //check if client is owner of claimId or if client is office
        if (!retrieveClaim(claimID).getIdClient().contentEquals(client) && !client.substring(0, 7).contentEquals("officer")){
            throw new ClientException("Client does not own this claim.");
        }

        return retrieveClaim(claimID).documentKeys().toString();
    }

    public String[] viewDocument(String client, String encryptedClaimID, int docID) throws Exception {
        //Decrypt claimId
        int claimID = Integer.parseInt(DecryptPub.decryptMsg(client, encryptedClaimID));

        //check if claim exists
        if (!dataStore.containsKey(claimID)) {
            throw new ClientException("Claim does not exist.");
        }

        //check if client is owner of claimId or if client is office
        if (!retrieveClaim(claimID).getIdClient().contentEquals(client) && !client.substring(0, 7).contentEquals("officer")){
            throw new ClientException("Client does not own this claim.");
        }

        String encryptedDoc = EncryptPub.encryptMsg(client, retrieveClaim(claimID).getDocumentContent(docID));

        String[] docAndSignature = new String[]{encryptedDoc, retrieveClaim(claimID).getDocumentSignature(docID), retrieveClaim(claimID).getDocDate(docID)};

        return docAndSignature;
    }

    private Claim retrieveClaim(int id){
        return dataStore.get(id);
    }

    public void editDocument(String clientID, int claimID, int documentID, String encryptedContent, String signature) throws Exception {
        //check if claim exists
        if (!dataStore.containsKey(claimID)) {
            throw new ClientException("Claim does not exist.");
        }

        //check if signature is valid
        String originalMsg = DecryptPub.decryptMsg(clientID, encryptedContent);
        if(!VerifySignature.checkSignature(clientID, originalMsg, signature)) {
            throw new ClientException("The claim signature is not valid.");
        }

        //check if claim belongs to the client or if client is an officer
        if (!retrieveClaim(claimID).getIdClient().contentEquals(clientID) && !clientID.substring(0, 7).contentEquals("officer")){
            throw new ClientException("Client does not own this claim.");
        }

        dataStore.get(claimID).editDocument(documentID, originalMsg, signature);
    }


}
