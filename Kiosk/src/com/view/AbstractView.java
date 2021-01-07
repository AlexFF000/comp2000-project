package com.view;

import com.controller.Controller;
import javax.swing.*;

public abstract class AbstractView extends JPanel {
    public Controller controller;

    public void setController(Controller controller){
        this.controller = controller;
    }
}
