package com.model;

import com.controller.Controller;

public interface IModelManager {
    // Register an observer with all models being managed by the manager
    void register(Controller observer);
    // De-register an observer from all the models being managed by the manager
    void remove(Controller observer);
    // Save the models being managed to the file
    void saveToFile();
}
