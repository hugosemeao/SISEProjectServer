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

public class Decrypt {
    private Cipher cipher;

    public Decrypt() throws NoSuchAlgorithmException, NoSuchPaddingException {
        this.cipher = Cipher.getInstance("RSA");
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


    public static void main(String[] args) throws Exception {
        //start the encryption framework
        Decrypt ad = new Decrypt();

        //load public key file
        System.out.print("insert the path to the public keyfile (ex. 'keys\\user1PublicKey'): ");
        Scanner path = new Scanner(System.in);
        String keyfile = path.nextLine();

        PublicKey publicKey = ad.getPublic(Paths.get("").toAbsolutePath() + System.getProperty("file.separator") + keyfile);

        //read encrypted message from the command line
        System.out.print("Encrypted Message: ");
        Scanner in = new Scanner(System.in);
        String encrypted_msg = in.nextLine();

        //decrypt message
        String decrypted_msg = ad.decryptText(encrypted_msg, publicKey);

        System.out.println("\nEncrypted Message: " + encrypted_msg +
                "\nDecrypted Message: " + decrypted_msg);
    }
}
