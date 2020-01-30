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
import java.util.Scanner;


public class AsymEncryptPub {
    private Cipher cipher;

    public AsymEncryptPub() throws NoSuchAlgorithmException, NoSuchPaddingException {
        this.cipher = Cipher.getInstance("RSA");
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


    public static void main(String[] args) throws Exception {
        //start the encryption framework
        AsymEncryptPub ac = new AsymEncryptPub();

        // load the public key
        System.out.print("insert the path to the public keyfile (ex. 'keys\\user1PublicKey'): ");
        Scanner path = new Scanner(System.in);
        String keyfile = path.nextLine();
        PublicKey publicKey = ac.getPublic(Paths.get("").toAbsolutePath() + System.getProperty("file.separator") + keyfile);

        //read message from the command line
        System.out.print("Message: ");
        Scanner in = new Scanner(System.in);
        String msg = in.nextLine();

        //encrypt the message
        String encrypted_msg = ac.encryptText(msg, publicKey);

        System.out.println("Original Message: " + msg +
                "\nEncrypted Message: " + encrypted_msg);


    }
}
