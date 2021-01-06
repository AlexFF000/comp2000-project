package view;

import controller.Controller;

import javax.swing.*;

public abstract class AbstractView extends JPanel {
    public Controller controller;

    public void setController(Controller controller){
        this.controller = controller;
    }
}
