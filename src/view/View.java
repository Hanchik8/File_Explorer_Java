package src.view;

import src.controller.Controller;

public class View {

    private TopPanel topMenu;

    public View() {
        new Controller(this);
    }

    public TopPanel getTopMenu() {
        return topMenu;
    }
}
