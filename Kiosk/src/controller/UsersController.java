package controller;

import model.JsonObject;
import model.User;
import model.UserManager;
import static model.JsonDirector.JsonToUser;

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
}
