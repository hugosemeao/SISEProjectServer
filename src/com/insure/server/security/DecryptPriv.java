package com.insure.server.security;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;
import java.util.Scanner;


public class DecryptPriv {
    private Cipher cipher;
    private String encryptedMsg;

    public DecryptPriv(String encryptedMsg) throws NoSuchAlgorithmException, NoSuchPaddingException {
        this.cipher = Cipher.getInstance("RSA");
        this.encryptedMsg = encryptedMsg;
    }


    // https://docs.oracle.com/javase/8/docs/api/java/security/spec/PKCS8EncodedKeySpec.html
    public PrivateKey getPrivate(String filename) throws Exception {
        byte[] keyBytes = Files.readAllBytes(new File(filename).toPath());
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        return kf.generatePrivate(spec);
    }


    public String decryptText(String msg, Key key)
            throws InvalidKeyException, UnsupportedEncodingException,
            IllegalBlockSizeException, BadPaddingException {
        this.cipher.init(Cipher.DECRYPT_MODE, key);
        return new String(cipher.doFinal(Base64.getDecoder().decode(msg)), "UTF-8");
    }

    public String getDecryptedMsg() throws Exception {
        PrivateKey prvKey = this.getPrivate(Paths.get("").toAbsolutePath() +
                System.getProperty("file.separator") + "keysS/Private/serverPrivateKey");
        return  this.decryptText(this.encryptedMsg, prvKey);
    }

    public static String decryptMsg(String msg) throws Exception{
        return (new DecryptPriv(msg)).getDecryptedMsg();
    }

}
