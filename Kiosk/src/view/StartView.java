package view;

import javax.swing.*;

public class StartView extends AbstractView{
    private JButton staffLoginBtn;
    private JButton startBtn;
    private JLabel welcomeText;

    public StartView(){
        add(staffLoginBtn);
        add(startBtn);
        add(welcomeText);
    }
}
