package model;

import controller.Controller;

public interface IModelManager {
    void register(Controller observer);
    void remove(Controller observer);
    void saveToFile();
}
