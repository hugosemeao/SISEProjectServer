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

public class DecryptPub {
    private Cipher cipher;
    private String key;
    private String encryptedMsg;

    public DecryptPub(String key, String encryptedMsg) throws NoSuchAlgorithmException, NoSuchPaddingException {
        this.cipher = Cipher.getInstance("RSA");
        this.key = key;
        this.encryptedMsg = encryptedMsg;
    }


    public PublicKey getPublic(String filename) throws Exception {
        byte[] keyBytes = Files.readAllBytes(new File(filename).toPath());
        X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        return kf.generatePublic(spec);
    }


    public String decryptText(String msg, Key key)
            throws InvalidKeyException, UnsupportedEncodingException,
            IllegalBlockSizeException, BadPaddingException {
        this.cipher.init(Cipher.DECRYPT_MODE, key);
        return new String(cipher.doFinal(Base64.getDecoder().decode(msg)), "UTF-8");
    }

    public String getDecryptedMsg() throws Exception {
        PublicKey prvKey = this.getPublic(Paths.get("").toAbsolutePath() +
                System.getProperty("file.separator") + "keysS/Public" + System.getProperty("file.separator") + this.key + System.getProperty("file.separator") + this.key + "PublicKey");
        return  this.decryptText(this.encryptedMsg, prvKey);
    }

    public static String decryptMsg(String key, String msg) throws Exception{
        return (new DecryptPub(key, msg)).getDecryptedMsg();
    }

}
