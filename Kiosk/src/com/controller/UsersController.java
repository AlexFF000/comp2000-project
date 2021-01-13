package com.controller;

import com.model.JsonObject;
import com.model.User;
import com.model.UserManager;
import com.view.LoginView;
import com.view.UsersView;

import static com.model.JsonDirector.JsonToUser;

public class UsersController extends Controller{

    @Override
    public void updateModel(int updateType, JsonObject newValue){
        UserManager manager = UserManager.getInstance();
        User user;
        switch(updateType){
            case CREATE_USER:
                manager.addUser(JsonToUser(newValue));
                break;
            case DELETE_USER:
                user = manager.getUser(newValue.getKey());
                if (user != null) manager.deleteUser(user);
                break;
            case UPDATE_USERNAME:
                user = manager.getUser(newValue.getKey());
                if (user != null) user.setUsername(newValue.getNewKey());
                break;
            case UPDATE_PASSWORD:
                user = manager.getUser(newValue.getKey());
                if (user != null) user.setPassword(newValue.getPassword());
                break;
        }
    }

    @Override
    public void close() {
        // Deregister from all observables
        for (User user : UserManager.getInstance().users){
            user.remove(this);
        }
    }

    @Override
    public void updateViewUser(String username, User updatedUser){
        if (view.getClass() == UsersView.class){
            ((UsersView) view).editDisplayedItem(username, "username", updatedUser.getUsername());
        }
    }

    @Override
    public void removeViewUser(String username){
        if (view.getClass() == UsersView.class){
            ((UsersView) view).removeDisplayedItem(username);
        }
    }

    public void login(String username, String password){
        // Check if the credentials match, and update loginView accordingly
        if (view.getClass() == LoginView.class) {
            LoginView loginView = (LoginView) view;
            if (validateCredentials(username, password)) loginView.loginSuccess();
            else loginView.loginFail();
        }
    }

    public boolean validateCredentials(String username, String password){
        // Check if the credentials match a user
        for (User user : UserManager.getInstance().users){
            if (username.equals(user.getUsername())){
                if (user.passwordMatches(password)) return true;
            }
        }
        return false;
    }

    public void displayUsers(){
        // Give the view a list of users to display
        UserManager manager = UserManager.getInstance();
        for (User user : manager.users){
            // Register as observer
            user.register(this);
            if (view.getClass() == UsersView.class){
                ((UsersView) view).addToDisplay(user.getUsername());
            }
        }
    }
}
