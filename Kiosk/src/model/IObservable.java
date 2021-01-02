package model;

import controller.Controller;

public interface IObservable {
    void register(Controller observer);
    void remove(Controller observer);
    void notifyObserversOfUpdate();
    void notifyObserversOfDelete();
}
