package com.model;

import com.controller.Controller;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;

public class User implements IObservable{
    private static final int HASH_ITERATIONS = 10000;
    private static final int HASH_LENGTH = 128;

    public ArrayList<Controller> observers;
    private String username;
    private String password;
    private String salt;

    public static String hashPassword(String salt, String password){
        // Hash password using PBKDF2
        try {
            // Store information for the hashing algorithm as a PBEKeySpec object
            PBEKeySpec keySpec = new PBEKeySpec(password.toCharArray(), salt.getBytes(), HASH_ITERATIONS, HASH_LENGTH);
            // Get object implementing the hashing algorithm
            SecretKeyFactory hashFunction = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            // Generate the hash
            return bytesToHex(hashFunction.generateSecret(keySpec).getEncoded());
        }
        catch (NoSuchAlgorithmException | InvalidKeySpecException e){
            e.printStackTrace();
            return null;
        }
    }

    private static String generateSalt(){
        // Generate a random 64 bit salt and return it as a hex string
        SecureRandom rng = new SecureRandom();
        byte[] generatedSalt = new byte[8];
        rng.nextBytes(generatedSalt);
        return bytesToHex(generatedSalt);
    }

    private static String bytesToHex(byte[] byteArray){
        // Convert byte array to a hexadecimal string
        StringBuilder builder = new StringBuilder();
        for (byte i : byteArray){
            // Convert each byte to a two digit hex string and pad with 0s if less than two digits
            builder.append(String.format("%02x", i));
        }
        return builder.toString();
    }

    public User(String username, String password, String salt){
        // Constructor for creating a new user object for existing account data
        // Unlike setters, constructor should not update the data file, so it can be used to create User objects read from the file
        observers = new ArrayList<>();
        this.username = username;
        this.password = password;
        this.salt = salt;
    }

    public User(String username, String password){
        // Constructor for creating a new user account
        observers = new ArrayList<>();
        this.username = username;
        // Generate a salt for the new user
        this.salt = generateSalt();
        // Hash the new user's password
        this.password = hashPassword(this.salt, password);
    }

    public String getUsername(){
        return username;
    }

    public void setUsername(String newUsername){
        username = newUsername;
        updateAfterSet();
    }

    public String getPassword(){
        return password;
    }

    public void setPassword(String newPassword){
        // Hash new password before setting it
        password = hashPassword(salt, newPassword);
        updateAfterSet();
    }

    public String getSalt(){
        return salt;
    }

    public void setSalt(String newSalt){
        salt = newSalt;
        updateAfterSet();
    }

    private void updateAfterSet(){
        // Save to file and notify observers
        UserManager.getInstance().saveToFile();
        notifyObserversOfUpdate();
    }

    public boolean passwordMatches(String passwordToCheck){
        // Hash the given password using this users salt, and return true if it matches this users password
        String hashedPassword = hashPassword(salt, passwordToCheck);
        return hashedPassword.equals(password);
    }

    @Override
    public void register(Controller observer){
        observers.add(observer);
    }

    @Override
    public void remove(Controller observer){
        observers.remove(observer);
    }

    @Override
    public void notifyObserversOfUpdate(){
        for (Controller observer : observers){
            observer.updateViewUser(this);
        }
    }

    @Override
    public void notifyObserversOfDelete(){
        for (Controller observer : observers){
            observer.removeViewUser(this);
        }
    }
}
