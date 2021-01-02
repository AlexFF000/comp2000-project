package model;

import controller.Controller;

import java.util.ArrayList;

public class User implements IObservable{
    public ArrayList<Controller> observers;
    private String username;
    private String password;

    public User(String username, String password){
        // Unlike setters, constructor should not update the data file, so it can be used to create User objects read from the file
        observers = new ArrayList<>();
        this.username = username;
        this.password = password;
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
        password = newPassword;
        updateAfterSet();
    }

    private void updateAfterSet(){
        // Save to file and notify observers
    }

    public void register(Controller observer){
        observers.add(observer);
    }

    public void remove(Controller observer){
        observers.remove(observer);
    }

    public void notifyObserversOfUpdate(){
        for (Controller observer : observers){
            observer.updateViewUser(this);
        }
    }
    public void notifyObserversOfDelete(){
        for (Controller observer : observers){
            observer.removeViewUser(this);
        }
    }
}
