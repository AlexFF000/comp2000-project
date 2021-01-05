package model;

import controller.Controller;
import static model.JsonDirector.JsonToUser;
import static model.JsonDirector.BuildJsonUser;

import java.util.ArrayList;

public class UserManager implements IModelManager{
    private static UserManager instance;
    private final UsersFileAccess file;
    public ArrayList<User> users;

    public static UserManager getInstance(){
        if (instance == null){
            instance = new UserManager();
        }
        return instance;
    }

    private UserManager(){
        users = new ArrayList<>();
        // Load users from file
        file = new UsersFileAccess();
        ArrayList<JsonObject> usersJson = file.loadItems();
        for (JsonObject user : usersJson){
            users.add(JsonToUser(user));
        }
    }

    public User getUser(String username){
        for (User user : users){
            if (user.getUsername().equals(username)) return user;
        }
        return null;
    }

    public void addUser(User user){
        users.add(user);
        saveToFile();
    }

    public void deleteUser(User user){
        user.notifyObserversOfDelete();
        users.remove(user);
        saveToFile();
    }

    @Override
    public void register(Controller observer){
        for (User user : users){
            user.register(observer);
        }
    }

    @Override
    public void remove(Controller observer){
        for (User user : users){
            user.remove(observer);
        }
    }

    @Override
    public void saveToFile(){
        ArrayList<JsonObject> usersJson = new ArrayList<>();
        for (User user : users){
            usersJson.add(BuildJsonUser(user));
        }
        file.saveItems(usersJson);
    }
}
