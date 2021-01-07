package com.view;

import com.Kiosk;
import com.controller.Controller;
import javax.swing.*;

public abstract class AbstractView extends JPanel {
    protected Kiosk window;
    public Controller controller;

    public void setWindow(Kiosk window){
        this.window = window;
    }

    public void setController(Controller controller){
        this.controller = controller;
    }
}
