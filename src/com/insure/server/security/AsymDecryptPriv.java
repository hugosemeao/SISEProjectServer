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


public class AsymDecryptPriv {
    private Cipher cipher;

    public AsymDecryptPriv() throws NoSuchAlgorithmException, NoSuchPaddingException {
        this.cipher = Cipher.getInstance("RSA");
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


    public static void main(String[] args) throws Exception {
        //start the encryption framework
        AsymDecryptPriv ad = new AsymDecryptPriv();

        //load private key file
        System.out.print("insert the path to the private keyfile (ex. 'keys\\user1PrivateKey'): ");
        Scanner path = new Scanner(System.in);
        String keyfile = path.nextLine();

        PrivateKey privateKey = ad.getPrivate(Paths.get("").toAbsolutePath() + System.getProperty("file.separator") + keyfile);

        //read encrypted message from the command line
        System.out.print("Encrypted Message: ");
        Scanner in = new Scanner(System.in);
        String encrypted_msg = in.nextLine();

        //decrypt message
        String decrypted_msg = ad.decryptText(encrypted_msg, privateKey);

        System.out.println("\nEncrypted Message: " + encrypted_msg +
                "\nDecrypted Message: " + decrypted_msg);


    }
}
