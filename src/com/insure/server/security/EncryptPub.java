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
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;


public class EncryptPub {
    private Cipher cipher;
    private String message;
    private String key;

    public EncryptPub(String key, String message) throws NoSuchAlgorithmException, NoSuchPaddingException {
        this.cipher = Cipher.getInstance("RSA");
        this.key = key;
        this.message = message;
    }


    public PublicKey getPublic(String filename) throws Exception {
        byte[] keyBytes = Files.readAllBytes(new File(filename).toPath());
        X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        return kf.generatePublic(spec);
    }


    public String encryptText(String msg, Key key)
            throws NoSuchAlgorithmException, NoSuchPaddingException,
            UnsupportedEncodingException, IllegalBlockSizeException,
            BadPaddingException, InvalidKeyException {
        this.cipher.init(Cipher.ENCRYPT_MODE, key);
        return Base64.getEncoder().encodeToString(cipher.doFinal(msg.getBytes("UTF-8")));
    }

    public String getEncryptedMsg() throws Exception {
        PublicKey prvKey = this.getPublic(Paths.get("").toAbsolutePath() +
                System.getProperty("file.separator") + "keysS/Public" + System.getProperty("file.separator") + this.key + System.getProperty("file.separator") + this.key + "PublicKey");
        return  this.encryptText(this.message, prvKey);
    }

    public static String encryptMsg(String key, String msg) throws Exception{
        return (new EncryptPub(key, msg)).getEncryptedMsg();
    }

}
