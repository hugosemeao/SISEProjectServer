package com.insure.server.security;

import java.security.MessageDigest;

public class VerifySignature {
    public static boolean checkSignature(String key, String originalMessage, String signature) throws Exception {
        String decryptedHash = DecryptPub.decryptMsg(key, signature);
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
        messageDigest.update(originalMessage.getBytes());

        return decryptedHash.contentEquals(new String(messageDigest.digest()));
    }
}
